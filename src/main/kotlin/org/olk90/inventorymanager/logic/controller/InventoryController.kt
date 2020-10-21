package org.olk90.inventorymanager.logic.controller

import javafx.collections.ObservableList
import javafx.scene.control.TextField
import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.model.InventoryItemModel
import tornadofx.*

fun getInventoryControllerInstance(): InventoryController {
    return find(InventoryController::class)
}

class InventoryController : Controller() {

    val model = InventoryItemModel(InventoryItem())

    private val filteredData = SortedFilteredList(ObjectStore.inventoryItems)
    val tableItems = mutableListOf<InventoryItem>().asObservable()

    fun save() {
        model.commit()

        val item = model.item
        val i = ObjectStore.findInventoryById(item.id)!!
        i.name = item.name
        i.available = item.available
        i.lender = item.lender
        i.lendingDate = item.lendingDate

        val lenderId = item.lender
        if (lenderId > -1) {
            val lender = ObjectStore.persons.find { it.id == lenderId }
            i.lenderNameProperty.value = lender!!.getFullName()
        }

        getWorkspaceControllerInstance().writeDcFile()
    }

    fun add() {
        val item = InventoryItem(model.name.value, model.available.value, model.lendingDate.value)
        item.lenderNameProperty.value = model.lenderName.value
        insertItem(item)
        getWorkspaceControllerInstance().writeDcFile()
    }

    private fun insertItem(item: InventoryItem) {
        ObjectStore.inventoryItems.add(item)
        tableItems.add(item)
    }

    fun configureFilterListener(textField: TextField) {
        textField.textProperty().addListener { _, _, newValue ->
            tableItems.clear()
            filteredData.predicate = {
                val filterTextEmpty = newValue == null || newValue.isEmpty()

                val lowerCaseFilter = newValue.toLowerCase()
                val nameMatch = it.name.toLowerCase().indexOf(lowerCaseFilter) != -1

                val lenderMatch = if (it.lender > -1) {
                    val lender = ObjectStore.persons.first { p -> p.id == it.lender }
                    lender.getFullName().toLowerCase().indexOf(lowerCaseFilter) != -1
                } else {
                    false
                }

                val lendingDateMatch = if (it.lendingDateString != null) {
                    it.lendingDateString.toLowerCase().indexOf(lowerCaseFilter) != -1
                } else {
                    false
                }

                filterTextEmpty || nameMatch || lenderMatch || lendingDateMatch
            }
            tableItems.addAll(filteredData)
        }

    }

    fun delete(items: ObservableList<InventoryItem>) {
        items.forEach {
            ObjectStore.inventoryItems.remove(it)
            tableItems.remove(it)
        }
        getWorkspaceControllerInstance().writeDcFile()
    }
}