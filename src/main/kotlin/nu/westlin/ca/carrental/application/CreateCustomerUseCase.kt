package nu.westlin.ca.carrental.application

import nu.westlin.ca.carrental.domain.Customer
import nu.westlin.ca.carrental.domain.CustomerId
import nu.westlin.ca.carrental.domain.Id
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateCustomerUseCase(
    private val saveNewCustomerRepository: SaveNewCustomerRepository,
    private val findCustomerRepository: FindCustomerRepository
) {

    fun createCustomer(newCustomer: NewCustomer): CreateCustomerResult {
        return findCustomerRepository.find(CustomerId(newCustomer.email))?.let {
            CreateCustomerResult.AlreadyExist
        } ?: run {
            saveNewCustomerRepository.save(
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

interface SaveNewCustomerRepository {
    fun save(customer: Customer)
}

interface FindCustomerRepository {
    fun find(customerId: CustomerId): Customer?
}