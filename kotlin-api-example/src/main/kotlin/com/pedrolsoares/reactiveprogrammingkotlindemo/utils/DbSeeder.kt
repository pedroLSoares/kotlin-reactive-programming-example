package com.pedrolsoares.reactiveprogrammingkotlindemo.utils

import com.pedrolsoares.reactiveprogrammingkotlindemo.model.Employee
import com.pedrolsoares.reactiveprogrammingkotlindemo.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Component
class DbSeeder(
        @Autowired val employeeRepository: EmployeeRepository,
        @Autowired val reactiveMongoOperations: ReactiveMongoOperations
) : CommandLineRunner{
    override fun run(vararg args: String?) {
        dbSetup()
    }

    private fun dbSetup() {
        val employeeList = Flux.just(
                Employee(null, "Pedro", "IT"),
                Employee(null, "Samantha", "IT"),
                Employee(null, "Robert", "Civil"),
                Employee(null, "Sarah", "HM")
        )

        val employees = employeeRepository.saveAll(employeeList);
        reactiveMongoOperations.collectionExists("employees").flatMap {
            when(it) {
                true -> reactiveMongoOperations.dropCollection("employees")
                false -> Mono.empty()
            }
        }
                .thenMany(reactiveMongoOperations.createCollection("employees"))
                .thenMany(employees)
                .thenMany(employeeRepository.findAll())
                .subscribe({println(it)}, {error -> println(error)}, { println("Initialized")})
    }
}