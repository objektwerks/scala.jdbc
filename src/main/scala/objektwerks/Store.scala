package objektwerks

import com.typesafe.config.Config

import java.nio.file.Files
import java.nio.file.Path
import javax.sql.DataSource

import org.h2.jdbcx.JdbcConnectionPool

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

class Store(conf: Config):
  private val ds: DataSource = Store.createDataSource(config)

  def addTodo(todo: Todo): Int =
    "insert into todo(task) values(${todo.task})"
    1

  def updateTodo(todo: Todo): Boolean =
    DB localTx { implicit session =>
      sql"update todo set task = ${todo.task} where id = ${todo.id}".update()
    }
    true

  def listTodos(): Seq[Todo] =
    DB readOnly { implicit session =>
      sql"select * from todo"
        .map(rs => Todo( rs.int("id"), rs.string("task") ) )
        .list()
    }