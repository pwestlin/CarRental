package nu.westlin.ca.carrental.presentation

import nu.westlin.ca.carrental.application.ListCustomerBookingsUseCase
import nu.westlin.ca.carrental.domain.Booking
import nu.westlin.ca.carrental.domain.CustomerId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@CustomersRequestMapping
class ListCustomerBookingsController(
    private val listCustomerBookings: ListCustomerBookingsUseCase
) {
    @GetMapping("/customer/{customerId}")
    fun createNewCustomer(
        @PathVariable customerId: CustomerId
    ): ResponseEntity<List<Booking>> {
        return ResponseEntity.ok(listCustomerBookings.getBookings(customerId))
    }
}
