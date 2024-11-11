package nu.westlin.ca.carrental.application

import nu.westlin.ca.carrental.domain.Customer
import nu.westlin.ca.carrental.domain.CustomerId
import org.springframework.stereotype.Service

@Service
class GetCustomerUseCase(
    private val customerRepository: CustomerRepository
) {
    fun getCustomer(customerId: CustomerId): Customer? = customerRepository.find(customerId)
}
