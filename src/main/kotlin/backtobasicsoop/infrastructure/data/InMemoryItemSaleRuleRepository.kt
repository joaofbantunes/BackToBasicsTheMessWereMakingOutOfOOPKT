package backtobasicsoop.infrastructure.data

import backtobasicsoop.domain.data.ItemSaleRuleRepository
import backtobasicsoop.domain.entities.ItemId
import backtobasicsoop.domain.entities.ItemSaleRule
import backtobasicsoop.domain.entities.item_sale_rules.NoopItemSaleRule

class InMemoryItemSaleRuleRepository : ItemSaleRuleRepository {
    override fun getForItem(itemId: ItemId): ItemSaleRule = NoopItemSaleRule()
}