package backtobasicsoop.infrastructure.services

import backtobasicsoop.domain.entities.ItemId
import backtobasicsoop.domain.services.ItemNotifier
import mu.KotlinLogging

class LoggingItemNotifier : ItemNotifier {
    private val logger = KotlinLogging.logger {}

    override fun notify(itemId: ItemId) {
        logger.debug { "Sold item $itemId" }
    }
}