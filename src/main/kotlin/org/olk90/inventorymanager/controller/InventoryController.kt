package org.olk90.inventorymanager.controller

import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.model.InventoryItemModel

class InventoryController : WorkspaceController() {

    val model = InventoryItemModel(InventoryItem())

    fun save() {
        model.commit()

        val inventoryItem = model.item
        // TODO persist data
    }
}