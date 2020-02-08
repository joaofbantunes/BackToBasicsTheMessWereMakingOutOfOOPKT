package backtobasicsoop.domain.use_cases

import backtobasicsoop.domain.Error
import backtobasicsoop.domain.data.CartRepository
import backtobasicsoop.domain.data.ItemRepository
import backtobasicsoop.domain.data.ItemSaleRuleRepository
import backtobasicsoop.domain.services.PostAddItemToCartListener
import backtobasicsoop.shared.Either
import backtobasicsoop.shared.exec
import backtobasicsoop.shared.flatMap
import backtobasicsoop.shared.map

class AddItemToCartRequestHandler2(
    private val cartRepository: CartRepository,
    private val itemRepository: ItemRepository,
    private val itemSaleRuleRepository: ItemSaleRuleRepository,
    private val addItemToCartListener: PostAddItemToCartListener
) :
    RequestHandler<AddItemToCartRequest, Either<Error, Unit>> {
    override fun handle(input: AddItemToCartRequest): Either<Error, Unit> {

        val cart = cartRepository.get(input.cartId) ?: return Either.left(Error.NotFound("Couldn't find the cart"))
        val item = itemRepository.get(input.itemId) ?: return Either.left(Error.NotFound("Couldn't find the item"))

        return itemSaleRuleRepository
            .getForItem(item.id)
            .validate(cart, item, input.quantity)
            .flatMap { cart.addItemToCart(item, input.quantity) }
            .exec { cartRepository.save(cart) }
            .map { cartItem -> addItemToCartListener.onAdded(cart, item, cartItem) }
            .map { Unit }
    }
}