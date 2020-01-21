package backtobasicsoop

import backtobasicsoop.domain.data.CartRepository
import backtobasicsoop.domain.data.ItemRepository
import backtobasicsoop.domain.data.ItemSaleRuleRepository
import backtobasicsoop.domain.entities.ItemId
import backtobasicsoop.domain.services.PostAddItemToCartListener
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
import backtobasicsoop.shared.Result
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import java.util.*

fun wireUpDependencies() = Kodein {
    bind<CartRepository>() with singleton { InMemoryCartRepository() }
    bind<ItemRepository>() with singleton { InMemoryItemRepository() }
    bind<ItemSaleRuleRepository>() with singleton { InMemoryItemSaleRuleRepository() }
    bind<RequestHandler<AddItemToCartRequest, Result<Unit>>>() with provider {
        AddItemToCartRequestHandler(
            instance(),
            instance(),
            instance(),
            instance()
        )
    }
    bind<RequestHandler<AddItemToCartRequest, Result<Unit>>>(overrides = true) with provider {
        RequestHandlerLoggingDecorator(overriddenInstance() as RequestHandler<AddItemToCartRequest, Result<Unit>>)
    }
    bind<PostAddItemToCartListener>() with singleton {
        CompositePostAddItemToCartListener(
            sequenceOf(
                WatchlistNotifierListener(
                    LoggingItemNotifier(),
                    sequenceOf(ItemId(UUID.fromString("d9efb645-0c1f-4d2f-bf38-01a1906277b7"))))
            ))
    }
}