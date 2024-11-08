package nu.westlin.ca.carrental.application

import nu.westlin.ca.carrental.domain.CustomerId
import org.springframework.stereotype.Service

@Service
class DeleteCustomerUseCase(
    private val deleteCustomerRepository: DeleteCustomerRepository,
    private val deleteBookingRepository: DeleteBookingRepository
) {

    fun deleteCustomer(customerId: CustomerId): Boolean {
        return deleteCustomerRepository.delete(customerId).let { customerExists ->
            if(customerExists) {
                deleteBookingRepository.delete(customerId)
            }

            customerExists
        }
    }
}

interface DeleteCustomerRepository {
    fun delete(customerId: CustomerId): Boolean
}

interface DeleteBookingRepository {
    fun delete(customerId: CustomerId)
}