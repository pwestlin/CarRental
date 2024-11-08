package nu.westlin.ca.carrental.application

import io.github.oshai.kotlinlogging.KotlinLogging
import nu.westlin.ca.carrental.domain.Booking
import nu.westlin.ca.carrental.domain.BookingId
import nu.westlin.ca.carrental.domain.Car
import nu.westlin.ca.carrental.domain.CarId
import nu.westlin.ca.carrental.domain.Customer
import nu.westlin.ca.carrental.domain.CustomerId
import nu.westlin.ca.carrental.domain.Id
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.UUID

@Component
@Profile("initwithdata")
class InitializeDataAtStartup(
    private val customerRepository: CustomerRepository,
    private val carRepository: CarRepository,
    private val bookingRepository: BookingRepository
) {

    private val logger = KotlinLogging.logger {}

    @EventListener
    fun onApplicationEvent(event: ContextRefreshedEvent) {
        val porsche = Car(
            id = CarId.createCarId(),
            type = Car.Type.Sport,
            brand = "Posrche",
            model = "911 Turbo",
            category = Car.Category.Exclusive
        ).also(carRepository::add)
        Car(
            id = CarId.createCarId(),
            type = Car.Type.Sedan,
            brand = "Subaru",
            model = "Impreza",
            category = Car.Category.Basic
        ).also(carRepository::add)
        val volvo = Car(
            id = CarId.createCarId(),
            type = Car.Type.Hatchback,
            brand = "Volvo",
            model = "V60",
            category = Car.Category.Basic
        ).also(carRepository::add)

        Customer(
            id = CustomerId("poor@man.com"),
            email = "poor@man.com",
            name = "Poor Man",
            phoneNumber = "123",
            type = Customer.Type.Basic
        ).also { customer ->
            customerRepository.add(customer)

            bookingRepository.add(
                Booking(
                    id = BookingId.createBookingId(),
                    customerId = customer.id,
                    carId = volvo.id,
                    period = Booking.Period(
                        pickupTime = LocalDateTime.now().plusDays(1L),
                        returnTime = LocalDateTime.now().plusDays(2L)
                    )
                )
            )
        }

        Customer(
            id = CustomerId("rich@woman.com"),
            email = "rich@woman.com",
            name = "Rich Woman",
            phoneNumber = "456",
            type = Customer.Type.Gold
        ).also { customer ->
            customerRepository.add(customer)

            bookingRepository.add(
                Booking(
                    id = BookingId.createBookingId(),
                    customerId = customer.id,
                    carId = porsche.id,
                    period = Booking.Period(
                        pickupTime = LocalDateTime.now().plusDays(1L),
                        returnTime = LocalDateTime.now().plusDays(2L)
                    )
                )
            )
        }

        logger.info { "Data initialized" }
    }
}

private fun Id.Companion.createCarId(): CarId = CarId(UUID.randomUUID())
private fun Id.Companion.createBookingId(): BookingId = BookingId(UUID.randomUUID())