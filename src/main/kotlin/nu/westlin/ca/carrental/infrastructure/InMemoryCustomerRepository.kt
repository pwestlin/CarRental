package nu.westlin.ca.carrental.infrastructure

import nu.westlin.ca.carrental.application.CustomerRepository
import nu.westlin.ca.carrental.domain.Customer
import nu.westlin.ca.carrental.domain.CustomerId
import nu.westlin.ca.carrental.domain.Id
import org.springframework.stereotype.Repository

@Repository
class InMemoryCustomerRepository : CustomerRepository {
    private val customers: MutableList<Customer> = mutableListOf()

    override fun add(customer: Customer) {
        customers.add(customer)
    }

    override fun delete(customerId: CustomerId): Boolean = customers.removeIf { customer -> customer.id == customerId }

    override fun find(customerId: Id<Customer, String>): Customer? {
        return customers.firstOrNull { customer -> customer.id == customerId }
    }
}
