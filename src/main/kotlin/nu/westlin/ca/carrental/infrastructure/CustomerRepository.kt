package nu.westlin.ca.carrental.infrastructure

import nu.westlin.ca.carrental.domain.Customer
import nu.westlin.ca.carrental.domain.CustomerId
import nu.westlin.ca.carrental.domain.Id
import org.springframework.stereotype.Repository

interface CustomerRepository {
    // TODO pevest: Should this be Customer from entities or antoher abstraction?
    fun add(customer: Customer)

    // TODO pevest: email should be an value object
    fun delete(customerId: CustomerId): Boolean
    fun find(customerId: Id<Customer, String>): Customer?
}

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