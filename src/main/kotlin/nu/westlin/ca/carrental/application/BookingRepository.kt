package nu.westlin.ca.carrental.application

import nu.westlin.ca.carrental.domain.Booking
import nu.westlin.ca.carrental.domain.BookingId
import nu.westlin.ca.carrental.domain.Customer
import nu.westlin.ca.carrental.domain.CustomerId
import nu.westlin.ca.carrental.domain.Id

interface BookingRepository {
    fun add(booking: Booking)

    fun delete(bookingId: BookingId): Boolean

    fun deleteAllCustomerBookings(customerId: CustomerId)
    fun getBookingsForCustomer(customerId: Id<Customer, String>): List<Booking>
}