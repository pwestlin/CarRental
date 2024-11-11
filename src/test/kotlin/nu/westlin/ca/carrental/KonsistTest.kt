package nu.westlin.ca.carrental

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.ext.list.withAllAnnotationsOf
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RestController

class KonsistTest {

    @DisplayName("Spring")
    @Nested
    inner class KonsistSpringTest {

        @Test
        fun `classes with annotation @RestController should reside in 'presentation' package`() {
            Konsist
                .scopeFromProduction()
                .classes()
                .withAllAnnotationsOf(RestController::class)
                .assertTrue {
                    it.resideInPackage("..presentation")
                }
        }

        @Test
        fun `classes with @RestController annotation should have 'Controller' suffix`() {
            Konsist
                .scopeFromProject()
                .classes()
                .withAllAnnotationsOf(RestController::class)
                .assertTrue { it.hasNameEndingWith("Controller") }
        }

        @Test
        fun `classes with @Repository annotation should have 'Repository' suffix`() {
            Konsist
                .scopeFromProject()
                .classes()
                .withAllAnnotationsOf(Repository::class)
                .assertTrue { it.hasNameEndingWith("Repository") }
        }

        @Test
        fun `classes with @Repository annotation should reside in 'infrastructure' package`() {
            Konsist
                .scopeFromProject()
                .classes()
                .withAllAnnotationsOf(Repository::class)
                .assertTrue { it.resideInPackage("..infrastructure") }
        }

        @Test
        fun `classes with @Service annotation should have 'Service' or 'UseCase' suffix`() {
            Konsist
                .scopeFromProject()
                .classes()
                .withAllAnnotationsOf(Service::class)
                .assertTrue { it.hasNameEndingWith("Service") || it.hasNameEndingWith("UseCase") }
        }
    }

    @Test
    fun `classes with 'UseCase' suffix should reside in 'usecase' package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue { it.resideInPackage("..application") }
    }

    @Test
    fun `clean architecture layers have correct dependencies`() {
        Konsist
            .scopeFromProduction()
            .assertArchitecture {
                val domain = Layer("Domain", "nu.westlin.ca.carrental.domain..")
                val presentation = Layer("Presentation", "nu.westlin.ca.carrental.presentation..")
                val application = Layer("application", "nu.westlin.ca.carrental.application..")
                val infrastructure = Layer("Infrastructure", "nu.westlin.ca.carrental.infrastructure..")

                presentation.dependsOn(application, domain)
                application.dependsOn(domain)
                infrastructure.dependsOn(domain, application)
                domain.dependsOnNothing()
            }
    }
}