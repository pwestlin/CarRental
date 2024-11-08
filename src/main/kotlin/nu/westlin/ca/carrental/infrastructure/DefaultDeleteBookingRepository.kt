package nu.westlin.ca.carrental.infrastructure

import nu.westlin.ca.carrental.application.DeleteBookingRepository
import nu.westlin.ca.carrental.domain.CustomerId
import org.springframework.stereotype.Service

@Service
class DefaultDeleteBookingRepository(
    private val bookingRepository: BookingRepository
) : DeleteBookingRepository {
    override fun delete(customerId: CustomerId) {
        bookingRepository.deleteAllBookings(customerId)
    }
}