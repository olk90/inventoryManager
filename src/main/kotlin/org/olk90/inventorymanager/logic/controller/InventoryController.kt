package org.olk90.inventorymanager.logic.controller

import com.beust.klaxon.Klaxon
import javafx.collections.ObservableList
import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.model.InventoryItemModel
import org.olk90.inventorymanager.model.InventorySet
import org.olk90.inventorymanager.model.Person
import java.io.File

class InventoryController : WorkspaceController() {

    val model = InventoryItemModel(InventoryItem())

    fun save() {
        model.commit()

        val inventoryItem = model.item
        // TODO persist data
    }

    fun add() {
        val item = InventoryItem(model.name.value, model.available.value, model.lender.value, model.lendingDate.value)
        inventoryItems.add(item)
    }

    fun parsePersonsFromFile(jsonDocument: File) {
        val set = Klaxon().parse<InventorySet>(jsonDocument)
        if (set != null) {
            inventoryItems.clear()
            inventoryItems.addAll(set.inventory)
        }
    }

    fun loadPersons(): ObservableList<Person> {
        return find(WorkspaceController::class).persons
    }
}