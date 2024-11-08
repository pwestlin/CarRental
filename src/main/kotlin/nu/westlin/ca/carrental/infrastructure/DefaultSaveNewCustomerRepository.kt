package nu.westlin.ca.carrental.infrastructure

import nu.westlin.ca.carrental.domain.Customer
import nu.westlin.ca.carrental.application.SaveNewCustomerRepository
import org.springframework.stereotype.Repository

@Repository
class DefaultSaveNewCustomerRepository(
    private val customerRepository: CustomerRepository
): SaveNewCustomerRepository {

    override fun save(customer: Customer) {
        customerRepository.add(customer)
    }
}