package com.pedrolsoares.reactiveprogrammingkotlindemo.repository

import com.pedrolsoares.reactiveprogrammingkotlindemo.model.Employee
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : ReactiveMongoRepository<Employee, String>