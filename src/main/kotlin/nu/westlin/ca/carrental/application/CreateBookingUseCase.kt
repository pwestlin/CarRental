package nu.westlin.ca.carrental.application

import nu.westlin.ca.carrental.domain.Booking
import nu.westlin.ca.carrental.domain.BookingId
import nu.westlin.ca.carrental.domain.CarId
import nu.westlin.ca.carrental.domain.CustomerId
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateBookingUseCase(
    private val bookingRepository: BookingRepository,
    private val customerRepository: CustomerRepository,
    private val carRepository: CarRepository
) {

    fun createBooking(newBooking: NewBooking): CreateBookingResult {
        val customer = customerRepository.find(CustomerId(newBooking.customerEmail))

        return if (customer != null) {
            TODO("Check that only GOLD can book Porsche")
            val car = carRepository.find(newBooking.carId)
            if (car != null) {
                val booking = newBooking.toBooking()
                bookingRepository.add(booking)
                CreateBookingResult.Created(booking.id)
            } else {
                CreateBookingResult.CarNotFound
            }
        } else {
            CreateBookingResult.CustomerNotFound
        }
    }
}

sealed interface CreateBookingResult {
    data class Created(val bookingId: BookingId) : CreateBookingResult
    data object CustomerNotFound : CreateBookingResult
    data object CarNotFound : CreateBookingResult
}

data class NewBooking(
    // TODO pevest: Should be CustomerId?
    val customerEmail: String,
    val carId: CarId,
    val period: Booking.Period
)

private fun NewBooking.toBooking(): Booking = Booking(
    id = BookingId(UUID.randomUUID()),
    customerId = CustomerId(this.customerEmail),
    carId = this.carId,
    period = this.period
)