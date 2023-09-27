package objektwerks

import com.typesafe.config.Config

import java.nio.file.Files
import java.nio.file.Path
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

class Store(config: Config):
  private val ds: DataSource = Store.createDataSource(config)

  def addTodo(todo: Todo): Todo =
    //"insert into todo(task) values(${todo.task})"
    Todo(task = "")

  def updateTodo(todo: Todo): Boolean =
    //"update todo set task = ${todo.task} where id = ${todo.id}"
    true

  def listTodos(): Seq[Todo] =
    val todos = mutable.ListBuffer[Todo]()
    Using.Manager( use =>
      val connection = use( ds.getConnection )
      val statement = use( connection.createStatement )
      val resultset = statement.executeQuery("select * from todo")
      while (resultset.next()) {
        val id = resultset.getInt(1)
        val task = resultset.getString(2)
        val todo = Todo(id, task)
        todos += todo
      }
    )
    todos.toList