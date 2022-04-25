package com.pedrolsoares.reactiveprogrammingwebclientdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.logging.LogManager

@SpringBootApplication
class ReactiveProgrammingWebclientDemoApplication

fun main(args: Array<String>) {
    runApplication<ReactiveProgrammingWebclientDemoApplication>(*args)
    var logger = org.apache.logging.log4j.LogManager.getLogger()

    val api = WebClientAPI()

//    api
//        .getAllEmployees()
//        .log()
//        .subscribe()

    api
        .postMultiple()
        .doOnComplete { api.getAllEmployees().subscribe() }
        .subscribe()


}
