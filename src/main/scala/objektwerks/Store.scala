package objektwerks

import com.typesafe.config.Config

import java.nio.file.{Files, Path}
import java.sql.Statement
import javax.sql.DataSource

import org.h2.jdbcx.JdbcConnectionPool

import scala.collection.mutable
import scala.util.Using

private object Store:
  def createDataSource(config: Config): DataSource =
    val ds = JdbcConnectionPool.create(
      config.getString("ds.url"),
      config.getString("ds.user"),
      config.getString("ds.password")
    )

    Using.Manager( use =>
      val connection = use( ds.getConnection )
      val statement = use( connection.createStatement )
      val sql = Files.readString( Path.of( config.getString("ds.ddl") ) )
      statement.execute(sql)
    )
    ds

final class Store(config: Config):
  private val ds: DataSource = Store.createDataSource(config)

  def close(): Unit =
    ds.asInstanceOf[JdbcConnectionPool].dispose()

  def addTodo(todo: Todo): Todo =
    val addTodoQuery = ds
      .getConnection
      .prepareStatement("insert into todo(task) values(?)", Statement.RETURN_GENERATED_KEYS) 
    addTodoQuery.setString(1, todo.task)
    addTodoQuery.executeUpdate()
    val resultset = addTodoQuery.getGeneratedKeys()
    resultset.next()
    val id = resultset.getInt(1)
    todo.copy(id = id)

  def updateTodo(todo: Todo): Int =
    val updateTodoQuery = ds
      .getConnection
      .prepareStatement("update todo set task = ? where id = ?")
    updateTodoQuery.setString(1, todo.task)
    updateTodoQuery.setInt(2, todo.id)
    updateTodoQuery.executeUpdate()

  def listTodos(): Seq[Todo] =
    val todos = mutable.ListBuffer[Todo]()
    val listTodosQuery = ds
      .getConnection
      .prepareStatement("select * from todo")
    val resultset = listTodosQuery.executeQuery()
    while (resultset.next()) {
      val id = resultset.getInt(1)
      val task = resultset.getString(2)
      val todo = Todo(id, task)
      todos += todo
    }
    todos.toList