package backtobasicsoop.domain.use_cases

import backtobasicsoop.domain.data.CartRepository
import backtobasicsoop.domain.data.ItemRepository
import backtobasicsoop.domain.data.ItemSaleRuleRepository
import backtobasicsoop.domain.entities.CartId
import backtobasicsoop.domain.entities.ItemId
import backtobasicsoop.domain.services.PostAddItemToCartListener
import backtobasicsoop.shared.Result

data class AddItemToCartRequest(val cartId: CartId, val itemId: ItemId, val quantity: Int)

class AddItemToCartRequestHandler(
    private val cartRepository: CartRepository,
    private val itemRepository: ItemRepository,
    private val itemSaleRuleRepository: ItemSaleRuleRepository,
    private val addItemToCartListener: PostAddItemToCartListener
) :
    RequestHandler<AddItemToCartRequest, Result<Unit>> {
    override fun handle(input: AddItemToCartRequest): Result<Unit> {

        val cart = cartRepository.get(input.cartId) ?: return Result.Error.NotFound("Couldn't find the cart")
        val item = itemRepository.get(input.itemId) ?: return Result.Error.NotFound("Couldn't find the item")

        val rulesResult = itemSaleRuleRepository
            .getForItem(item.id)
            .validate(cart, item, input.quantity)

        if (rulesResult is Result.Error) {
            return rulesResult
        }

        val addItemToCartResult = cart.addItemToCart(item, input.quantity)

        if (addItemToCartResult is Result.Error) {
            return addItemToCartResult.asErrorOf<Unit>()
        }

        cartRepository.save(cart)

        addItemToCartListener.onAdded(cart, item, (addItemToCartResult as Result.Success).value)

        return Result.Success(Unit)
    }
}