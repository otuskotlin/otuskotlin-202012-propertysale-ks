package ru.otus.otuskotlin.propertysale.be.app.ktor.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*

fun Application.featureAuth(authConfig: AuthConfig) {

    install(Authentication) {
        jwt("auth-jwt") {
            skipWhen { ! authConfig.authOff }
            realm = authConfig.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(authConfig.secret))
                    .withAudience(authConfig.audience)
                    .withIssuer(authConfig.domain)
                    .build()
            )
            validate { credential ->
                println("AUDIENCE: ${credential.payload.audience} ${authConfig.audience} ${credential.payload.audience.contains(authConfig.audience)}")
                println("ISSUER: ${credential.payload.issuer}")
                println("SUBJECT: ${credential.payload.subject}")
                if (credential.payload.audience.contains(authConfig.audience)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
