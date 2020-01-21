package backtobasicsoop

import backtobasicsoop.domain.entities.CartId
import backtobasicsoop.domain.entities.ItemId
import backtobasicsoop.domain.use_cases.AddItemToCartRequest
import backtobasicsoop.domain.use_cases.RequestHandler
import backtobasicsoop.infrastructure.serialization.ItemIdDeserializer
import backtobasicsoop.infrastructure.serialization.ItemIdSerializer
import backtobasicsoop.shared.Result
import com.fasterxml.jackson.databind.module.SimpleModule
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.*
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import java.util.*

fun main(args: Array<String>) {
    println("Starting server...")
    val kodein = wireUpDependencies();
    val server = embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson {
                registerModule(
                    SimpleModule()
                    .addSerializer(ItemId::class.java, ItemIdSerializer())
                    .addDeserializer(ItemId::class.java, ItemIdDeserializer())
                )
            }
        }
        routing {
            carts(kodein)
        }
    }
    server.start(wait = true)
}

fun Route.carts(kodein: Kodein) {
    route("/carts") {
        post("/{cartId}/items") {
            val cartId = CartId(UUID.fromString(call.parameters["cartId"]));
            val payload = call.receive<AddItemToCartRequestDto>()
            val request = AddItemToCartRequest(cartId, payload.itemId, payload.quantity)
            //a bit of a service locator, but if it's just for each handler, not terrible
            val handler by kodein.instance<RequestHandler<AddItemToCartRequest, Result<Unit>>>()
            call.respond(handler.handle(request))
        }
    }
}

suspend fun <TResult : Any> ApplicationCall.respond(result: Result<TResult>) {
    when (result) {
        is Result.Success -> if (result.value is Unit) this.respond(HttpStatusCode.NoContent) else this.respond(
            HttpStatusCode.OK,
            result.value
        )
        is Result.Error.Invalid -> this.respond(HttpStatusCode.BadRequest, ErrorResponse(result.message))
        is Result.Error.NotFound -> this.respond(HttpStatusCode.NotFound, ErrorResponse(result.message))
    }.exhaust
}

data class ErrorResponse(val reason: String)

// trickery to force the above when to be exhaustive
val Any.exhaust: Unit
    get() = Unit