package com.pedrolsoares.reactiveprogrammingkotlindemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

@SpringBootApplication
class ReactiveProgrammingKotlinDemoApplication

fun main(args: Array<String>) {
    runApplication<ReactiveProgrammingKotlinDemoApplication>(*args)

    Flux.range(1, 10)
        .parallel()
        .runOn(Schedulers.boundedElastic())
        .log()
        .map { it.toInt() }
        .map { calculate(it) }
        .subscribe{ println(it) }
}

fun calculate(number: Number): Number{
    Thread.sleep(2000)
    return number
}
