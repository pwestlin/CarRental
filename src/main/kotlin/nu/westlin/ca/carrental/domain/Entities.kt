package nu.westlin.ca.carrental.domain

import java.time.LocalDateTime
import java.util.*

data class Customer(
    val id: Id<Customer, String>,
    // TODO pevest: Should be a value object with rules
    // TODO pevest: Should this be called "id: Email"` or is that not "clean"? :
    val email: String,
    // TODO pevest: Should be a value object with rules?
    val name: String,
    // TODO pevest: Should be a value object with rules
    val phoneNumber: String,
    val type: Type
) {

    enum class Type {
        Basic,
        Gold
    }
}

data class Car(
    val id: Id<Car, UUID>,
    val type: Type,
    // TODO pevest: Should be a class
    val brand: String,
    // TODO pevest: Should be a class
    val model: String
) {

    enum class Type {
        Sport,
        Sedan,
        Hatchback
    }
}

data class Booking(
    val id: BookingId,
    val customerId: CustomerId,
    val carId: CarId,
    val period: Period
) {

    data class Period(
        val pickupTime: LocalDateTime,
        val returnTime: LocalDateTime
    )
}

@Suppress("unused")
@JvmInline
value class Id<out T, V>(val value: V)

typealias BookingId = Id<Customer, UUID>
typealias CarId = Id<Car, UUID>
typealias CustomerId = Id<Customer, String>