package nu.westlin.ca.carrental.infrastructure

import nu.westlin.ca.carrental.application.BookingRepository
import nu.westlin.ca.carrental.domain.Booking
import nu.westlin.ca.carrental.domain.BookingId
import nu.westlin.ca.carrental.domain.Customer
import nu.westlin.ca.carrental.domain.CustomerId
import nu.westlin.ca.carrental.domain.Id
import org.springframework.stereotype.Repository

@Repository
class InMemoryBookingRepository : BookingRepository {
    private val bookings: MutableList<Booking> = mutableListOf()

    override fun add(booking: Booking) {
        bookings.add(booking)
    }

    override fun delete(bookingId: BookingId): Boolean = bookings.removeIf { customer -> customer.id == bookingId }

    override fun deleteAllCustomerBookings(customerId: CustomerId) {
        bookings.removeIf { booking -> booking.customerId == customerId }
    }

    override fun getBookingsForCustomer(customerId: Id<Customer, String>): List<Booking> {
        return bookings.filter { booking -> booking.customerId == customerId }
    }
}
