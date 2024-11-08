package nu.westlin.ca.carrental.domain

import java.time.LocalDateTime
import java.util.UUID

data class Customer(
    val id: Id<Customer, String>,
    // TODO pevest: Should be a value object with rules - simple regex that doesn't work for all cases but... :D - ^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$
    // TODO pevest: Parse, don't validate
    val email: String,
    // TODO pevest: Should be a value object with rules?
    val name: String,
    // TODO pevest: Should be a value object with rules - ^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$
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
    val model: String,
    val category: Category
) {
    enum class Type {
        Sport,
        Sedan,
        Hatchback
    }

    enum class Category {
        Basic,
        Exclusive
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
value class Id<out T, V>(val value: V) {
    companion object
}

// TODO pevest: Change UUID to Int for easier manual testing
typealias BookingId = Id<Customer, UUID>
typealias CarId = Id<Car, UUID>
typealias CustomerId = Id<Customer, String>
