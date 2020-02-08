package backtobasicsoop.api

import backtobasicsoop.domain.Error
import backtobasicsoop.domain.entities.CartId
import backtobasicsoop.domain.entities.ItemId
import backtobasicsoop.domain.use_cases.AddItemToCartRequest
import backtobasicsoop.shared.Either
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import java.util.*

data class AddItemToCartRequestDto(val itemId: ItemId, val quantity: Int)

fun Route.carts(addItemToCartRequestHandlerFactory: () -> (AddItemToCartRequest) -> Either<Error, Unit>) {
    route("/carts") {
        post("/{cartId}/items") {
            val cartId = CartId(UUID.fromString(call.parameters["cartId"]));
            val payload = call.receive<AddItemToCartRequestDto>()
            val request = AddItemToCartRequest(cartId, payload.itemId, payload.quantity)
            val handler = addItemToCartRequestHandlerFactory()
            call.respondWithResult(handler(request))
        }
    }
}