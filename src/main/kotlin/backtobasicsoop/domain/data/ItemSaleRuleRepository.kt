package backtobasicsoop.domain.data

import backtobasicsoop.domain.entities.ItemId
import backtobasicsoop.domain.entities.ItemSaleRule

interface ItemSaleRuleRepository {
    fun getForItem(itemId: ItemId): ItemSaleRule
}