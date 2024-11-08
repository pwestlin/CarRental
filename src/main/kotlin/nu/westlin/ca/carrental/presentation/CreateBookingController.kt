package nu.westlin.ca.carrental.presentation

import nu.westlin.ca.carrental.application.CreateBookingResult
import nu.westlin.ca.carrental.application.CreateBookingUseCase
import nu.westlin.ca.carrental.application.NewBooking
import nu.westlin.ca.carrental.domain.BookingId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@BookingsRequestMapping
class CreateBookingController(
    private val createBooking: CreateBookingUseCase
) {

    @PostMapping("")
    fun createNewBooking(
        @RequestBody newBooking: NewBooking
    ): ResponseEntity<CreateNewCustomerResponse> {
        return when (val foo = createBooking.createBooking(newBooking)) {
            is CreateBookingResult.Created -> ResponseEntity.ok(CreateNewCustomerResponse.Created(foo.bookingId))
            CreateBookingResult.CustomerNotFound -> ResponseEntity.badRequest().body(CreateNewCustomerResponse.Error.CustomerNotFound)
            CreateBookingResult.CarNotFound -> ResponseEntity.badRequest().body(CreateNewCustomerResponse.Error.CarNotFound)
            CreateBookingResult.TooPoor -> ResponseEntity.badRequest().body(CreateNewCustomerResponse.Error.TooPoor)
        }
    }
}

sealed interface CreateNewCustomerResponse {
    data class Created(val bookingId: BookingId) : CreateNewCustomerResponse

    sealed interface Error : CreateNewCustomerResponse {
        data object CustomerNotFound : CreateNewCustomerResponse
        data object CarNotFound : CreateNewCustomerResponse
        data object TooPoor : CreateNewCustomerResponse
    }
}