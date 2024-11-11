package nu.westlin.ca.carrental.presentation

import io.github.oshai.kotlinlogging.KotlinLogging
import nu.westlin.ca.carrental.application.DeleteCustomerUseCase
import nu.westlin.ca.carrental.domain.CustomerId
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CustomersRequestMapping
class DeleteCustomerController(
    private val deleteCustomer: DeleteCustomerUseCase
) {

    @DeleteMapping("/{customerId}")
    fun deleteCustomer(
        @PathVariable customerId: CustomerId
    ): ResponseEntity<Unit> {
        return if (deleteCustomer.deleteCustomer(customerId)) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.badRequest().build()
        }
    }
}

@RestController
@RequestMapping(value = ["/anvandare"], produces = [MediaType.APPLICATION_JSON_VALUE])
class NyAnvandareController {

    val logger = KotlinLogging.logger {}

    @PostMapping("/Anvandare")
    fun registreraNyAnvandare(@RequestBody anvandare: Anvandare) {
        logger.info { "Ny användare registrerad: $anvandare" }
    }

    @PostMapping("/NyAnvandare1")
    fun registreraNyAnvandare1(@RequestBody nyAnvandare: NyAnvandare) {
        require(EPostadress.arGiltig(nyAnvandare.ePostadress))
        require(Losenord.arGiltigt(nyAnvandare.losenord))

        val anvandare = Anvandare(ePostadress = EPostadress(varde = nyAnvandare.ePostadress), losenord = Losenord(varde = nyAnvandare.ePostadress))
        logger.info { "Ny användare registrerad: $anvandare" }
    }

    @PostMapping("/NyAnvandare2")
    fun registreraNyAnvandare2(@RequestBody nyAnvandare: NyAnvandare): ResponseEntity<Unit> {
        return if (EPostadress.arGiltig(nyAnvandare.ePostadress) && Losenord.arGiltigt(nyAnvandare.losenord)) {
            val anvandare = Anvandare(ePostadress = EPostadress(varde = nyAnvandare.ePostadress), losenord = Losenord(varde = nyAnvandare.ePostadress))
            logger.info { "Ny användare registrerad: $anvandare" }
            ResponseEntity.ok().build()
        } else {
            logger.info { "Indatat uppfyllde inte regelverket: $nyAnvandare" }
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/NyAnvandare3")
    fun registreraNyAnvandare3(@RequestBody nyAnvandare: NyAnvandare): ResponseEntity<String> {
        return if (EPostadress.arGiltig(nyAnvandare.ePostadress)) {
            val anvandare = Anvandare(ePostadress = EPostadress(varde = nyAnvandare.ePostadress), losenord = Losenord(varde = nyAnvandare.ePostadress))

            if (Losenord.arGiltigt(nyAnvandare.losenord)) {
                logger.info { "Ny användare registrerad: $anvandare" }
                ResponseEntity.ok().build()
            } else {
                logger.info { "Felaktigt lösenord: ${nyAnvandare.losenord}" }
                ResponseEntity.badRequest().body("Felaktigt lösenord: ${nyAnvandare.losenord}")
            }
        } else {
            logger.info { "Felaktig e-postadress: ${nyAnvandare.ePostadress}" }
            ResponseEntity.badRequest().body("Felaktig e-postadress: ${nyAnvandare.ePostadress}")
        }
    }

    @PostMapping("/NyAnvandare4")
    fun registreraNyAnvandare4(@RequestBody nyAnvandare: NyAnvandare): ResponseEntity<Parsningsfel> {
        return if (EPostadress.arGiltig(nyAnvandare.ePostadress)) {
            val anvandare = Anvandare(ePostadress = EPostadress(varde = nyAnvandare.ePostadress), losenord = Losenord(varde = nyAnvandare.ePostadress))

            if (Losenord.arGiltigt(nyAnvandare.losenord)) {
                logger.info { "Ny användare registrerad: $anvandare" }
                ResponseEntity.ok().build()
            } else {
                logger.info { "Felaktigt lösenord: ${nyAnvandare.losenord}" }
                ResponseEntity.badRequest().body(Parsningsfel("Felaktigt lösenord: ${nyAnvandare.losenord}"))
            }
        } else {
            logger.info { "Felaktig e-postadress: ${nyAnvandare.ePostadress}" }
            ResponseEntity.badRequest().body(Parsningsfel("Felaktig e-postadress: ${nyAnvandare.ePostadress}"))
        }
    }

    @PostMapping("/NyAnvandare5")
    fun registreraNyAnvandare5(@RequestBody nyAnvandare: NyAnvandare): ResponseEntity<List<Parsningsfel>> {
        val giltigEPostadress = EPostadress.arGiltig(nyAnvandare.ePostadress)
        val giltigtLosenord = Losenord.arGiltigt(nyAnvandare.losenord)

        return if (giltigEPostadress && giltigtLosenord) {
            val anvandare = Anvandare(ePostadress = EPostadress(varde = nyAnvandare.ePostadress), losenord = Losenord(varde = nyAnvandare.ePostadress))

            logger.info { "Ny användare registrerad: $anvandare" }
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.badRequest().body(
                buildList {
                    if (!giltigEPostadress) {
                        logger.info { "Felaktig e-postadress: ${nyAnvandare.ePostadress}" }
                        add(Parsningsfel("Felaktig e-postadress: ${nyAnvandare.ePostadress}"))
                    }
                    if (!giltigtLosenord) {
                        logger.info { "Felaktigt lösenord: ${nyAnvandare.losenord}" }
                        add(Parsningsfel("Felaktigt lösenord: ${nyAnvandare.losenord}"))
                    }
                }
            )
        }
    }

    @PostMapping("/NyAnvandare6")
    fun registreraNyAnvandare6(@RequestBody nyAnvandare: NyAnvandare): ResponseEntity<List<Parsningsfel>> {
        return when (val resultat = parsa(nyAnvandare)) {
            is Parsningsresultat.Fel -> ResponseEntity.badRequest().body(resultat.fel.map { Parsningsfel(it) })
            Parsningsresultat.Ok -> ResponseEntity.ok().build()
        }
    }

    private fun parsa(nyAnvandare: NyAnvandare): Parsningsresultat {
        val giltigEPostadress = EPostadress.arGiltig(nyAnvandare.ePostadress)
        val giltigtLosenord = Losenord.arGiltigt(nyAnvandare.losenord)

        return if (giltigEPostadress && giltigtLosenord) {
            Parsningsresultat.Ok
        } else {
            Parsningsresultat.Fel(
                buildList {
                    if (!giltigEPostadress) {
                        add("Felaktig e-postadress: ${nyAnvandare.ePostadress}")
                    }
                    if (!giltigtLosenord) {
                        add("Felaktigt lösenord: ${nyAnvandare.losenord}")
                    }
                }
            )
        }
    }

    //data class Parsningsfel(val attribut: String, val felmeddelande: String)
    data class Parsningsfel(val felmeddelande: String)

    sealed interface Parsningsresultat {
        data object Ok : Parsningsresultat
        data class Fel(val fel: List<String>) : Parsningsresultat
    }
}

data class NyAnvandare(
    val ePostadress: String,
    val losenord: String
)

data class Anvandare(
    val ePostadress: EPostadress,
    val losenord: Losenord
)

@JvmInline
value class EPostadress(val varde: String) {
    init {
        require(arGiltig(varde)) { "$varde är inte en giltig E-postadress" }
    }

    companion object {
        private const val REGEX = """^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$"""

        fun arGiltig(ePostAdress: String): Boolean = ePostAdress.matches(REGEX.toRegex())
    }
}

@JvmInline
value class Losenord(val varde: String) {
    init {
        require(arGiltigt(varde)) { "$varde är inte ett giltigt lösenord" }
    }

    companion object {
        fun arGiltigt(losenord: String): Boolean = losenord.length in 8..32
    }
}

interface AnvandareService {
    fun spara(ePostadress: String, losenord: String)
    fun spara(anvandare: Anvandare)
}

@Service
class DefaultAnvandareService : AnvandareService {

    val logger = KotlinLogging.logger {}

    override fun spara(ePostadress: String, losenord: String) {
        val anvandare = Anvandare(
            ePostadress = EPostadress(ePostadress),
            losenord = Losenord(losenord)
        )
        logger.info { "Ny användare sparad: $anvandare" }
    }

    override fun spara(anvandare: Anvandare) {
        logger.info { "Ny användare sparad: $anvandare" }
    }
}