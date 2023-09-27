package objektwerks

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite

final class StoreTest extends AnyFunSuite:
  val conf = ConfigFactory.load("test.conf")
  val store = Store(conf)

  test("store"):
    val todo = Todo(task = "wash car")
    val id = store.addTodo(todo)
    println(s"*** Add Todo - $id")
    assert( id > 0 )

    val todoWithId = todo.copy(id = id)
    println(s"*** Update Todo - $todoWithId")
    store.updateTodo(todoWithId.copy(task = "wash and dry car"))
    
    val todos = store.listTodos()
    println(s"*** List Todos = ${todos.toString}")
    assert( todos.nonEmpty )