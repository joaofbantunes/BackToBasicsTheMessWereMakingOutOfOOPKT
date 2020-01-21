package backtobasicsoop.infrastructure.data

import backtobasicsoop.domain.data.ItemRepository
import backtobasicsoop.domain.entities.Item
import backtobasicsoop.domain.entities.ItemId
import java.util.*
import kotlin.collections.HashMap

class InMemoryItemRepository: ItemRepository {
    private val items: HashMap<ItemId, Item> = hashMapOf(
        ItemId(UUID.fromString("d9efb645-0c1f-4d2f-bf38-01a1906277b7")) to
                Item.from(
                    ItemId(UUID.fromString("d9efb645-0c1f-4d2f-bf38-01a1906277b7")),
                    "Sample Item")
    )
    override fun get(id: ItemId): Item? = items[id]

    override fun save(item: Item): Item {
        items[item.id] = item
        return item
    }

    override fun delete(id: ItemId) {
        items.remove(id)
    }
}