package backtobasicsoop

import backtobasicsoop.api.carts
import backtobasicsoop.domain.entities.ItemId
import backtobasicsoop.infrastructure.serialization.ItemIdDeserializer
import backtobasicsoop.infrastructure.serialization.ItemIdSerializer
import com.fasterxml.jackson.databind.module.SimpleModule
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    println("Starting server...")
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
            carts(::createAddItemToCartRequestHandler)
        }
    }
    server.start(wait = true)
}
