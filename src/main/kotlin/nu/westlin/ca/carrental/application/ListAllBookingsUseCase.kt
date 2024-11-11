package nu.westlin.ca.carrental.application

import nu.westlin.ca.carrental.domain.Booking
import org.springframework.stereotype.Service

@Service
class ListAllBookingsUseCase(
    private val bookingRepository: BookingRepository
) {
    fun getBookings(): List<Booking> {
        return bookingRepository.getBookings()
    }
}