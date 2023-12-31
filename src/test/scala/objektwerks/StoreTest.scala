package objektwerks

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.BeforeAndAfterAll

final class StoreTest extends AnyFunSuite with BeforeAndAfterAll:
  val conf = ConfigFactory.load("test.conf")
  val store = Store(conf)

  println("*** Store open.")

  override protected def afterAll(): Unit =
    store.close()
    println("*** Store closed.")

  test("store"):
    var todo = Todo(task = "wash car")
    todo = store.addTodo(todo)
    println(s"*** Add Todo - $todo")
    assert( todo.id > 0 )

    val updatedTodo = todo.copy(task = "wash and dry car")
    val updated = store.updateTodo(updatedTodo)
    println(s"*** Update Todo - $updatedTodo")
    assert( updated == 1 )
    
    val todos = store.listTodos()
    println(s"*** List Todos = ${todos.toString}")
    assert( todos.nonEmpty )