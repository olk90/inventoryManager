package org.olk90.inventorymanager.logic.controller

import com.beust.klaxon.Klaxon
import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.model.InventoryItemModel
import org.olk90.inventorymanager.model.InventorySet
import java.io.File

class InventoryController : WorkspaceController() {

    val model = InventoryItemModel(InventoryItem())

    fun save() {
        model.commit()

        val inventoryItem = model.item
        // TODO persist data
    }

    fun parsePersonsFromFile(jsonDocument: File) {
        val set = Klaxon().parse<InventorySet>(jsonDocument)
        if (set != null) {
            inventoryItems.clear()
            inventoryItems.addAll(set.inventory)
        }
    }
}