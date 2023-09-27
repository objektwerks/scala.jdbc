package objektwerks

import com.typesafe.config.ConfigFactory

import java.util.UUID
import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

object Performance:
  val conf = ConfigFactory.load("app.conf")
  val store = Store(conf)

@State(Scope.Thread)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(1)
class Performance():
  import Performance.store

  var todo = Todo(task = UUID.randomUUID.toString)
  val id = store.addTodo(todo)
  todo = todo.copy(id = id)

  @Benchmark
  def addTodo(): Int = store.addTodo(Todo(task = UUID.randomUUID.toString))

  @Benchmark
  def updateTodo(): Unit =
    todo = todo.copy(task = UUID.randomUUID.toString)
    store.updateTodo(todo)

  @Benchmark
  def listTodos(): Seq[Todo] = store.listTodos()