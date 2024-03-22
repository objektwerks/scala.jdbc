Scala Jdbc
----------
Jdbc feature test and performance benchmark against H2 using Scala 3.

Test
----
1. sbt clean test

Benchmark
---------
>See Performance class for details.
1. sbt jmh:run

Results
-------
>The following exception occurs when running the benchmark: **java.sql.SQLException: Login timeout**

>OpenJDK Runtime Environment Zulu22.28+91-CA (build 22+36), **Scala 3.4.1-RC2**, Apple M1
1. addTodo - 0.0
2. updateTodo - 0.0
3. listTodos - 0.0
>Total time: 0 s (0:0), 10 warmups, 10 iterations, average time in microseconds, completed **2024.3.22**