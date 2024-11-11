package nu.westlin.ca.carrental.application

import nu.westlin.ca.carrental.domain.Booking
import nu.westlin.ca.carrental.domain.BookingId
import nu.westlin.ca.carrental.domain.Car
import nu.westlin.ca.carrental.domain.CarId
import nu.westlin.ca.carrental.domain.Customer
import nu.westlin.ca.carrental.domain.CustomerId
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateBookingUseCase(
    private val bookingRepository: BookingRepository,
    private val customerRepository: CustomerRepository,
    private val carRepository: CarRepository
) {

    // TODO pevest: Refactor
    fun createBooking(newBooking: NewBooking): CreateBookingResult {
        val customer = customerRepository.find(newBooking.customerId)

        return if (customer != null) {
            val car = carRepository.find(newBooking.carId)
            if (car != null) {
                // TODO pevest: I think this rule is a business rules and therefore should reside in the domain package
                if (car.category == Car.Category.Exclusive && customer.type == Customer.Type.Gold) {
                    val booking = newBooking.toBooking()
                    bookingRepository.add(booking)
                    CreateBookingResult.Created(booking.id)
                } else {
                    CreateBookingResult.TooPoor
                }
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
    data object TooPoor : CreateBookingResult
}

data class NewBooking(
    val customerId: CustomerId,
    val carId: CarId,
    val period: Booking.Period
)

private fun NewBooking.toBooking(): Booking = Booking(
    id = BookingId(UUID.randomUUID()),
    customerId = this.customerId,
    carId = this.carId,
    period = this.period
)