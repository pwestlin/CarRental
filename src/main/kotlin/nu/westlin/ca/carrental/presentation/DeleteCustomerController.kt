package nu.westlin.ca.carrental.presentation

import nu.westlin.ca.carrental.application.DeleteCustomerUseCase
import nu.westlin.ca.carrental.domain.Id
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@CustomersRequestMapping
class DeleteCustomerController(
    private val deleteCustomer: DeleteCustomerUseCase
) {
    // TODO pevest: Maybe email should be CustomerId(email)
    @DeleteMapping("/{email}")
    fun createNewCustomer(
        @PathVariable email: String
    ): ResponseEntity<Unit> {
        return if (deleteCustomer.deleteCustomer(Id(email))) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.badRequest().build()
        }
    }
}
