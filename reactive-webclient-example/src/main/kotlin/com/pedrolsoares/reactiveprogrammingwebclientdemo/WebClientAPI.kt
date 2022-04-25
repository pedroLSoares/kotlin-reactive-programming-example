package com.pedrolsoares.reactiveprogrammingwebclientdemo

import com.pedrolsoares.reactiveprogrammingwebclientdemo.model.Employee
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.ParallelFlux
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toFlux

class WebClientAPI {

   private val webClient = WebClient.create("http://localhost:8080/v1")

   fun getAllEmployees(): Flux<Employee> {
      return webClient
         .get()
         .uri("/employees")
         .retrieve()
         .bodyToFlux(Employee::class.java)
         .doOnNext { println("Employee retrivied $it") }
   }

   fun saveEmployees(employee: Employee?): Mono<Employee> {
      var employeeToSave = employee ?: Employee("123123", "Jhonny", "ME")

      return webClient
         .post()
         .uri("/employees")
         .body(BodyInserters.fromValue(employeeToSave))
         .retrieve()
         .bodyToMono(Employee::class.java)
   }

   fun postMultiple(): Flux<Employee> {
      return Flux.range(1, 1000)
         .parallel()
         .runOn(Schedulers.boundedElastic())
         .flatMap({ data -> saveEmployees(Employee(data.toString(), "Employee number $data", "IT")) }, false, 2000)
         .toFlux()

   }

   fun manualPost(): Flux<String> {
      repeat(1000) { index ->
         saveEmployees(
            Employee(
               index.toString(),
               "Employee manual $index",
               "Manual"
            )

         )
      }

      return Flux.empty<String>()
   }
}