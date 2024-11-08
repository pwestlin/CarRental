package nu.westlin.ca.carrental.application

import nu.westlin.ca.carrental.domain.Car
import nu.westlin.ca.carrental.domain.CarId

interface CarRepository {
    fun find(carId: CarId): Car?
    fun add(car: Car)
}