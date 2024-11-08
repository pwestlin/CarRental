package nu.westlin.ca.carrental.presentation

import nu.westlin.ca.carrental.application.DeleteCustomerUseCase
import nu.westlin.ca.carrental.domain.CustomerId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@CustomersRequestMapping
class DeleteCustomerController(
    private val deleteCustomer: DeleteCustomerUseCase
) {

    @DeleteMapping("/{customerId}")
    fun deleteCustomer(
        @PathVariable customerId: CustomerId
    ): ResponseEntity<Unit> {
        return if (deleteCustomer.deleteCustomer(customerId)) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.badRequest().build()
        }
    }
}
