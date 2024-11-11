package seg3x02.employeeGql.resolvers

import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping

@Controller
class EmployeesResolver(private val employeeRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee> = employeeRepository.findAll()

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? = employeeRepository.findById(id).orElse(null)

    @MutationMapping
    fun newEmployee(@Argument input: CreateEmployeeInput): Employee {
        val employee = Employee(
            name = input.name ?: "",
            dateOfBirth = input.dateOfBirth ?: "",
            city = input.city ?: "",
            salary = input.salary ?: 0.0f,
            gender = input.gender,
            email = input.email
        )
        return employeeRepository.save(employee)
    }

    @MutationMapping
    fun deleteEmployee(@Argument id: String): Boolean {
        employeeRepository.deleteById(id)
        return true
    }

    @MutationMapping
    fun updateEmployee(@Argument id: String, @Argument input: CreateEmployeeInput): Employee {
        val employee = employeeRepository.findById(id).orElseThrow { Exception("Employee not found") }
        employee.name = input.name ?: employee.name
        employee.dateOfBirth = input.dateOfBirth ?: employee.dateOfBirth
        employee.city = input.city ?: employee.city
        employee.salary = input.salary ?: employee.salary
        employee.gender = input.gender ?: employee.gender
        employee.email = input.email ?: employee.email
        return employeeRepository.save(employee)
    }
}
