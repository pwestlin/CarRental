package nu.westlin.ca.carrental

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class CarRentalApplication

fun main(args: Array<String>) {
    runApplication<CarRentalApplication>(*args)
}
