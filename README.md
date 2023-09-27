Scala Jdbc
----------
Jdbc feature test and performance benchmark against H2 and Scala 3.

Test
----
1. sbt clean test

Benchmark
---------
>See Performance class for details.
1. sbt jmh:run
>**Warning:** Using JDK 9-21 and sbt-jmh 46, throws: java.lang.ClassNotFoundException: javax/sql/DataSource

>**See:** [Benchmark compilation fails if java.sql.ResultSet is used #192](https://github.com/sbt/sbt-jmh/issues/192)

Results
-------
>OpenJDK Runtime Environment Zulu21.28+85-CA (build 21+35), **Scala 3.3.1**, Apple M1
1. addTodo - 0.0
2. updateTodo - 0.0
3. listTodos - 0.0
>Total time: 0 s (0:0), 10 warmups, 10 iterations, in microseconds, completed **2023.9.27**
