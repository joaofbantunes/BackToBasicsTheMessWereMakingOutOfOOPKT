package backtobasicsoop.domain.data

import backtobasicsoop.domain.entities.Item
import backtobasicsoop.domain.entities.ItemId


interface ItemRepository {
    fun get(id: ItemId): Item?

    fun save(item: Item): Item

    fun delete(id: ItemId)
}