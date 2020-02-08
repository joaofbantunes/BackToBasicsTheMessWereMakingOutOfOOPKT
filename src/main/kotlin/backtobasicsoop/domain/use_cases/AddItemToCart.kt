package backtobasicsoop.domain.use_cases

import backtobasicsoop.domain.Error
import backtobasicsoop.domain.data.CartRepository
import backtobasicsoop.domain.data.ItemRepository
import backtobasicsoop.domain.data.ItemSaleRuleRepository
import backtobasicsoop.domain.entities.CartId
import backtobasicsoop.domain.entities.ItemId
import backtobasicsoop.domain.services.PostAddItemToCartListener
import backtobasicsoop.shared.Either

data class AddItemToCartRequest(val cartId: CartId, val itemId: ItemId, val quantity: Int)

class AddItemToCartRequestHandler(
    private val cartRepository: CartRepository,
    private val itemRepository: ItemRepository,
    private val itemSaleRuleRepository: ItemSaleRuleRepository,
    private val addItemToCartListener: PostAddItemToCartListener
) :
    RequestHandler<AddItemToCartRequest, Either<Error, Unit>> {
    override fun handle(input: AddItemToCartRequest): Either<Error, Unit> {

        val cart = cartRepository.get(input.cartId) ?: return Either.left(Error.NotFound("Couldn't find the cart"))
        val item = itemRepository.get(input.itemId) ?: return Either.left(Error.NotFound("Couldn't find the item"))

        val rulesResult = itemSaleRuleRepository
            .getForItem(item.id)
            .validate(cart, item, input.quantity)

        if (rulesResult is Either.Left) {
            return rulesResult
        }

        val addItemToCartResult = cart.addItemToCart(item, input.quantity)

        if (addItemToCartResult is Either.Left) {
            return addItemToCartResult
        }

        cartRepository.save(cart)

        addItemToCartListener.onAdded(cart, item, (addItemToCartResult as Either.Right).value)

        return Either.right(Unit)
    }
}