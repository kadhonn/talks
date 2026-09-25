println("Hello World from KTS!")

val m = mapOf("key" to "value", "hello" to "world")
println(m)
println(m.map { it.value.uppercase() })

for (i in 1..10) {
    println("$i")
}
