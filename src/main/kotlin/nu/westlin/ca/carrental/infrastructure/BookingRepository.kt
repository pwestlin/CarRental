package nu.westlin.ca.carrental.infrastructure

import nu.westlin.ca.carrental.domain.Booking
import nu.westlin.ca.carrental.domain.BookingId
import nu.westlin.ca.carrental.domain.CustomerId
import org.springframework.stereotype.Repository

interface BookingRepository {
    fun add(booking: Booking)

    fun delete(bookingId: BookingId): Boolean

    fun deleteAllCustomerBookings(customerId: CustomerId)
}

@Repository
class InMemoryBookingRepository : BookingRepository {
    private val bookings: MutableList<Booking> = mutableListOf()

    override fun add(booking: Booking) {
        bookings.add(booking)
    }

    override fun delete(bookingId: BookingId): Boolean = bookings.removeIf { customer -> customer.id == bookingId }

    override fun deleteAllCustomerBookings(customerId: CustomerId) {
        bookings.removeIf { customer -> customer.id == customerId }
    }
}
