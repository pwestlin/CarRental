package nu.westlin.ca.carrental.application

import nu.westlin.ca.carrental.domain.Customer
import nu.westlin.ca.carrental.domain.CustomerId
import nu.westlin.ca.carrental.domain.Id
import org.springframework.stereotype.Service

@Service
class CreateCustomerUseCase(
    private val customerRepository: CustomerRepository
) {
    fun createCustomer(newCustomer: NewCustomer): CreateCustomerResult {
        return customerRepository.find(CustomerId(newCustomer.email))?.let {
            CreateCustomerResult.AlreadyExist
        } ?: run {
            customerRepository.add(
                Customer(
                    id = Id(newCustomer.email),
                    email = newCustomer.email,
                    name = newCustomer.name,
                    phoneNumber = newCustomer.phoneNumber,
                    type = Customer.Type.Basic
                )
            )

            CreateCustomerResult.Success
        }
    }
}

sealed class CreateCustomerResult {
    data object Success : CreateCustomerResult()

    data object AlreadyExist : CreateCustomerResult()
}

data class NewCustomer(
    val email: String,
    val name: String,
    val phoneNumber: String
)
