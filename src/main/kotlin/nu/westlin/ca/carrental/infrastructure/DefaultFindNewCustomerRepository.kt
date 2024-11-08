package nu.westlin.ca.carrental.infrastructure

import nu.westlin.ca.carrental.application.FindCustomerRepository
import nu.westlin.ca.carrental.domain.Customer
import nu.westlin.ca.carrental.application.SaveNewCustomerRepository
import nu.westlin.ca.carrental.domain.CustomerId
import org.springframework.stereotype.Repository

@Repository
class DefaultFindNewCustomerRepository(
    private val customerRepository: CustomerRepository
): FindCustomerRepository {
    override fun find(customerId: CustomerId): Customer? = customerRepository.find(customerId)

}