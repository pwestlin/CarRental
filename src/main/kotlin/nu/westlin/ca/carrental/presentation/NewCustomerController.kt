package nu.westlin.ca.carrental.presentation

import nu.westlin.ca.carrental.application.CreateCustomerResult
import nu.westlin.ca.carrental.application.CreateCustomerUseCase
import nu.westlin.ca.carrental.application.NewCustomer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CustomersRequestMapping
class NewCustomerController(
    private val createCustomer: CreateCustomerUseCase
) {
    @PostMapping("")
    fun createNewCustomer(
        @RequestBody newCustomer: NewCustomer
    ): ResponseEntity<Unit> {
        return when (createCustomer.createCustomer(newCustomer)) {
            CreateCustomerResult.AlreadyExist -> ResponseEntity.status(HttpStatus.CONFLICT).build()
            CreateCustomerResult.Success -> ResponseEntity.ok().build()
        }
    }
}
