package nu.westlin.ca.carrental.presentation

import nu.westlin.ca.carrental.application.ListAllBookingsUseCase
import nu.westlin.ca.carrental.domain.Booking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@BookingsRequestMapping
class ListBookingsController(
    private val listCustomerBookings: ListAllBookingsUseCase
) {
    @GetMapping("")
    fun createNewCustomer(): ResponseEntity<List<Booking>> {
        return ResponseEntity.ok(listCustomerBookings.getBookings())
    }
}
