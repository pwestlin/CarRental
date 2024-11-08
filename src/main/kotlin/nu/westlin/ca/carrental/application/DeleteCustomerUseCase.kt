package nu.westlin.ca.carrental.application

import nu.westlin.ca.carrental.domain.CustomerId
import nu.westlin.ca.carrental.infrastructure.BookingRepository
import org.springframework.stereotype.Service

@Service
class DeleteCustomerUseCase(
    private val customerRepository: CustomerRepository,
    private val bookingRepository: BookingRepository
) {
    fun deleteCustomer(customerId: CustomerId): Boolean {
        return customerRepository.delete(customerId).let { customerExists ->
            if (customerExists) {
                bookingRepository.deleteAllCustomerBookings(customerId)
            }

            customerExists
        }
    }
}
