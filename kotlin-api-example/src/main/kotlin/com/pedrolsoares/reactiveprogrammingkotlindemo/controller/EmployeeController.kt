package com.pedrolsoares.reactiveprogrammingkotlindemo.controller

import com.pedrolsoares.reactiveprogrammingkotlindemo.model.Employee
import com.pedrolsoares.reactiveprogrammingkotlindemo.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
@RequestMapping("v1/employees")
class EmployeeController (
        @Autowired val employeeRepository: EmployeeRepository
        ){

    @GetMapping
    fun getAllEmployees(): Flux<Employee> {
        return employeeRepository.findAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): Mono<Employee> {
        return employeeRepository.findById(id)
    }

    @PostMapping
    fun save(@RequestBody employee: Employee): Mono<Employee> {
        return employeeRepository.save(employee)
    }

    @PutMapping
    fun update(@RequestBody employee: Employee): Mono<Employee> {
        return employeeRepository.save(employee)
    }

    @DeleteMapping
    fun delete(): Mono<Void> {
        return employeeRepository.deleteAll()
    }

    @DeleteMapping("{id}")
    fun deleteById(@PathVariable id: String): Mono<Void>{
        return employeeRepository.deleteById(id)
    }
}