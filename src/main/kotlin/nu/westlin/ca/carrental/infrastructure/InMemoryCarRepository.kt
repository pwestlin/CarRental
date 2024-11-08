package nu.westlin.ca.carrental.infrastructure

import nu.westlin.ca.carrental.application.CarRepository
import nu.westlin.ca.carrental.domain.Car
import nu.westlin.ca.carrental.domain.CarId
import org.springframework.stereotype.Repository

@Repository
class InMemoryCarRepository : CarRepository {
    private val cars: MutableList<Car> = mutableListOf()

    override fun add(car: Car) {
        cars.add(car)
    }

    override fun find(carId: CarId): Car? = cars.find { it.id == carId }
}
