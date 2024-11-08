package nu.westlin.ca.carrental.application

import nu.westlin.ca.carrental.domain.Customer
import nu.westlin.ca.carrental.domain.CustomerId
import nu.westlin.ca.carrental.domain.Id

interface CustomerRepository {
    fun add(customer: Customer)

    fun delete(customerId: CustomerId): Boolean

    fun find(customerId: Id<Customer, String>): Customer?
}
