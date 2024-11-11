package nu.westlin.ca.carrental

import io.github.oshai.kotlinlogging.KotlinLogging
import nu.westlin.ca.carrental.application.NewCustomer
import nu.westlin.ca.carrental.domain.Booking
import nu.westlin.ca.carrental.domain.Car
import nu.westlin.ca.carrental.domain.Customer
import nu.westlin.ca.carrental.domain.CustomerId
import nu.westlin.ca.carrental.infrastructure.InMemoryBookingRepository
import nu.westlin.ca.carrental.infrastructure.InMemoryCarRepository
import nu.westlin.ca.carrental.infrastructure.InMemoryCustomerRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.web.client.RestClient
import org.springframework.web.client.toEntity
import kotlin.random.Random

private const val INTEGRATION_TEST_PROFILE = "IntegrationTest"

// TODO pevest: Make this test run only if chosen?
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(INTEGRATION_TEST_PROFILE)
class CarRentalApplicationIntegrationTest(
    @Autowired private val bookingRepository: InMemoryBookingRepository,
    @Autowired private val carRepository: InMemoryCarRepository,
    @Autowired private val customerRepository: InMemoryCustomerRepository
) {
    @LocalServerPort
    private var port: Int = 0

    private lateinit var restClient: RestClient

    private val logger = KotlinLogging.logger {}

    @BeforeAll
    fun setup() {
        logger.info { "port: $port" }
        restClient = RestClient.builder()
            .baseUrl("http://localhost:$port")
            // Short cicuit Springs default error handling that throws exceptions
            .defaultStatusHandler({ true }, { _, _ ->
                // Do nothing
            })
            .build()
    }

    @Suppress("UNCHECKED_CAST")
    @BeforeEach
    fun clearData() {
        // TODO pevest: My God it's ugly using reflection but I havw no way to "clean" the system...
        (ReflectionTestUtils.getField(bookingRepository, "bookings") as MutableList<Booking>).clear()
        (ReflectionTestUtils.getField(carRepository, "cars") as MutableList<Car>).clear()
        (ReflectionTestUtils.getField(customerRepository, "customers") as MutableList<Customer>).clear()
    }

    @Test
    fun `create customer`() {
        // TODO pevest: Create extension function example()
        val newCustomer = NewCustomer.example()

        val response = restClient.post()
            .uri("/customers")
            .body(newCustomer)
            .retrieve()
            .toEntity<Customer>()
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(
            Customer(
                id = CustomerId(newCustomer.email),
                email = newCustomer.email,
                name = newCustomer.name,
                phoneNumber = newCustomer.phoneNumber,
                type = Customer.Type.Basic
            )
        )
    }

    @Test
    fun `delete customer that have no bookings`() {
        // TODO pevest: Create extension function example()
        val newCustomer = NewCustomer.example()

        // TODO pevest: Refacto to function call
        val customer = restClient.post()
            .uri("/customers")
            .body(newCustomer)
            .retrieve()
            .toEntity<Customer>()
            .body!!

        restClient.delete()
            .uri("/customers/${customer.id.value}")
            .retrieve()
            .toBodilessEntity()
            .let { response -> assertThat(response.statusCode).isEqualTo(HttpStatus.OK) }

        // TODO pevest: Refactor to function
        restClient.get()
            .uri("/customers/${customer.id}")
            .retrieve()
            .toBodilessEntity()
            .let { response ->
                assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
                assertThat(response.body).isNull()
            }
    }

    @Test
    fun `delete customer that have one or more bookings`() {
        // TODO pevest: Implement
    }
}

// TODO pevest: Move into TestExtensions.kt
fun NewCustomer.Companion.example(): NewCustomer {
    val firstNames = listOf(
        "Sune",
        "Arne",
        "Kent",
        "Al",
        "Keith",
        "Susan",
        "Camilla",
        "Peter",
        "Jenny",
        "Jennifer",
        "Morgan",
        "Ben",
        "David",
        "Dejan"
    )
    val lastNames = listOf(
        "Davies",
        "Kulusevski",
        "Carlsson",
        "Karlsson",
        "Friggebo",
        "Smith",
        "Carter",
        "Bush",
        "Fish"
    )
    val domainNames = listOf(
        "com",
        "se",
        "nu",
        "au",
        "gb",
        "it"
    )

    val firstName = firstNames.random()
    val lastName = lastNames.random()
    val domainName = domainNames.random()
    return NewCustomer(
        email = "$firstName@$lastName.$domainName",
        name = "$firstName $lastName",
        phoneNumber = Random.nextLong(10_000_000_000).toString()
    )
}