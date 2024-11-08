package nu.westlin.ca.carrental.application

import nu.westlin.ca.carrental.domain.Booking
import nu.westlin.ca.carrental.domain.CustomerId
import nu.westlin.ca.carrental.infrastructure.InMemoryBookingRepository
import org.springframework.stereotype.Service

@Service
class ListCustomerBookingsUseCase(
    private val bookingRepository: InMemoryBookingRepository
) {
    fun getBookings(customerId: CustomerId): List<Booking> {
        return bookingRepository.getBookingsForCustomer(customerId)
    }

}