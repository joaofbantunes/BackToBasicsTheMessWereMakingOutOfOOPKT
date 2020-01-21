package backtobasicsoop.domain.services

import backtobasicsoop.domain.entities.ItemId

interface ItemNotifier {
    fun notify(itemId: ItemId)
}