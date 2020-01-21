package backtobasicsoop.domain.entities

class Item private constructor(val id: ItemId, val name: String) {
    companion object {
        fun new(name: String): Item = Item(ItemId.new(), name)

        fun from(id: ItemId, name: String): Item = Item(id, name)
    }
}