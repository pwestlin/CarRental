package nu.westlin.ca.carrental.application

import nu.westlin.ca.carrental.domain.Customer
import nu.westlin.ca.carrental.domain.CustomerId
import org.springframework.stereotype.Service

@Service
class CreateCustomerUseCase(
    private val customerRepository: CustomerRepository
) {
    fun createCustomer(newCustomer: NewCustomer): CreateCustomerResult {
        val customerId = CustomerId(newCustomer.email)
        return customerRepository.find(customerId)?.let {
            CreateCustomerResult.AlreadyExist
        } ?: run {
            customerRepository.add(
                Customer(
                    id = customerId,
                    email = newCustomer.email,
                    name = newCustomer.name,
                    phoneNumber = newCustomer.phoneNumber,
                    type = Customer.Type.Basic
                )
            )

            CreateCustomerResult.Success(customerId)
        }
    }
}

sealed interface CreateCustomerResult {
    data class Success(val customerId: CustomerId) : CreateCustomerResult

    data object AlreadyExist : CreateCustomerResult
}

data class NewCustomer(
    val email: String,
    val name: String,
    val phoneNumber: String
)
