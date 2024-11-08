package nu.westlin.ca.carrental.infrastructure

import nu.westlin.ca.carrental.application.DeleteCustomerRepository
import nu.westlin.ca.carrental.domain.CustomerId
import org.springframework.stereotype.Repository

@Repository
class DefaultDeleteCustomerRepository(
    private val customerRepository: CustomerRepository
) : DeleteCustomerRepository {

    override fun delete(customerId: CustomerId): Boolean = customerRepository.delete(customerId)
}