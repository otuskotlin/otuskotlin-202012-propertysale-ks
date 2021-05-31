package ru.otus.otuskotlin.propertysale.be.app.ktor

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.jsonConfig
import ru.otus.otuskotlin.propertysale.mp.common.RestEndpoints
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatRead
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class AuthJwtTest {
    @Test
    fun jwtTest() {
        println(TOKEN)
        withTestApplication({
            (environment.config as MapApplicationConfig).apply {
                put("propertysale.auth.jwt.secret", SECRET)
                put("propertysale.auth.jwt.audience", AUDIENCE)
                put("propertysale.auth.jwt.domain", DOMAIN)
                put("propertysale.auth.jwt.realm", REALM)
            }
            module()
        }) {
            handleRequest(HttpMethod.Post, RestEndpoints.flatRead) {
                val body = PsRequestFlatRead(
                    requestId = "321",
                    flatId = "12345",
                    debug = PsRequestFlatRead.Debug(stubCase = PsRequestFlatRead.StubCase.SUCCESS)
                )

                val format = jsonConfig

                val bodyString = format.encodeToString(PsMessage.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
                addHeader("Authorization", "Bearer $TOKEN")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseFlatRead)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("321", res.onRequest)
                assertEquals("flat-test-name", res.flat?.name)
            }
        }
    }

    companion object {
        const val SECRET = "propertysale-secret"
        const val AUDIENCE = "test-ps-audience"
        const val REALM = "test-ps-realm"
        const val DOMAIN = "http://localhost/"
        const val USER_ID = "test-user-id"
        const val USER_FNAME = "Ivan"
        const val USER_MNAME = "Ivanovich"
        const val USER_LNAME = "Ivanov"
        val TOKEN = JWT.create()
            .withSubject("Authentication")
            .withIssuer(DOMAIN)
            .withAudience(AUDIENCE)
            .withClaim("id", USER_ID)
            .withClaim("fname", USER_FNAME)
            .withClaim("mname", USER_MNAME)
            .withClaim("lname", USER_LNAME)
            .withArrayClaim("groups", arrayOf("USER", "ADMIN_MP", "MODERATOR_MP"))
//            .withExpiresAt(getExpiration())
            .sign(Algorithm.HMAC256(SECRET))
            .toString()
    }
}
