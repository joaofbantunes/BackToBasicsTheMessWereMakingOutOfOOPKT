package backtobasicsoop

import backtobasicsoop.domain.entities.ItemId
import backtobasicsoop.shared.Result
import backtobasicsoop.domain.services.post_add_item_to_cart_listeners.CompositePostAddItemToCartListener
import backtobasicsoop.domain.services.post_add_item_to_cart_listeners.WatchlistNotifierListener
import backtobasicsoop.domain.use_cases.AddItemToCartRequest
import backtobasicsoop.domain.use_cases.AddItemToCartRequestHandler
import backtobasicsoop.domain.use_cases.RequestHandler
import backtobasicsoop.domain.use_cases.RequestHandlerLoggingDecorator
import backtobasicsoop.infrastructure.data.InMemoryCartRepository
import backtobasicsoop.infrastructure.data.InMemoryItemRepository
import backtobasicsoop.infrastructure.data.InMemoryItemSaleRuleRepository
import backtobasicsoop.infrastructure.services.LoggingItemNotifier
import java.util.*

private val cartRepository = InMemoryCartRepository()
private val itemRepository = InMemoryItemRepository()
private val itemSaleRuleRepository = InMemoryItemSaleRuleRepository()
private val addItemToCartListener = CompositePostAddItemToCartListener(
    sequenceOf(
        WatchlistNotifierListener(
            LoggingItemNotifier(),
            sequenceOf(ItemId(UUID.fromString("d9efb645-0c1f-4d2f-bf38-01a1906277b7")))
        )
    )
)

private fun <TIn : Any, TOut : Any> RequestHandler<TIn, TOut>.withLogging() = RequestHandlerLoggingDecorator(this)
private fun <TIn : Any, TOut : Any> RequestHandler<TIn, TOut>.asFunction() = this::handle

fun x(): RequestHandler<AddItemToCartRequest, Result<Unit>> =
    AddItemToCartRequestHandler(cartRepository, itemRepository, itemSaleRuleRepository, addItemToCartListener)
        .withLogging()

fun createAddItemToCartRequestHandler(): (AddItemToCartRequest) -> Result<Unit> =
    AddItemToCartRequestHandler(cartRepository, itemRepository, itemSaleRuleRepository, addItemToCartListener)
        .withLogging()
        .asFunction()
