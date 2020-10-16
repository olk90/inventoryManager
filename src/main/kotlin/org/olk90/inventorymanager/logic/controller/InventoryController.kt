package org.olk90.inventorymanager.logic.controller

import com.beust.klaxon.Klaxon
import javafx.collections.ObservableList
import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.model.InventoryItemModel
import org.olk90.inventorymanager.model.InventorySet
import org.olk90.inventorymanager.model.Person
import tornadofx.*
import java.io.File

class InventoryController : Controller() {

    val model = InventoryItemModel(InventoryItem())

    fun save() {
        model.commit()

        val inventoryItem = model.item
        // TODO persist data
    }

    fun add() {
        val item = InventoryItem(model.name.value, model.available.value, model.lender.value, model.lendingDate.value)
        ObjectStore.inventoryItems.add(item)
    }

    fun parsePersonsFromFile(jsonDocument: File) {
        val set = Klaxon().parse<InventorySet>(jsonDocument)
        if (set != null) {
            ObjectStore.inventoryItems.clear()
            ObjectStore.inventoryItems.addAll(set.inventory)
        }
    }
}