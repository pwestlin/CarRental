package nu.westlin.ca.carrental.presentation

import nu.westlin.ca.carrental.application.GetCustomerUseCase
import nu.westlin.ca.carrental.domain.Customer
import nu.westlin.ca.carrental.domain.CustomerId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@CustomersRequestMapping
class GetCustomerController(
    private val getCustomer: GetCustomerUseCase
) {

    @GetMapping("/{customerId}")
    fun getCustomer(
        @PathVariable customerId: CustomerId
    ): ResponseEntity<Customer> {
        val customer = getCustomer.getCustomer(customerId)
        return if (customer != null) {
            ResponseEntity.ok(customer)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
