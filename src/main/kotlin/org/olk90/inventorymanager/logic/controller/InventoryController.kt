package org.olk90.inventorymanager.logic.controller

import javafx.scene.control.TextField
import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.model.InventoryItemModel
import tornadofx.*

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
        i.lenderNameProperty.value = item.lender.getFullName()

        getWorkspaceControllerInstance().writeDcFile()
    }

    fun add() {
        val item = InventoryItem(model.name.value, model.available.value, model.lender.value, model.lendingDate.value)
        item.lenderNameProperty.value =  model.lenderName.value
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
                // If filter text is empty, display all persons.
                val filterTextEmpty = newValue == null || newValue.isEmpty()

                // Compare first name and last name of every person with filter text.
                val lowerCaseFilter = newValue.toLowerCase()
                val nameMatch = it.name.toLowerCase().indexOf(lowerCaseFilter) != -1
                val lenderMatch = it.lender.getFullName().toLowerCase().indexOf(lowerCaseFilter) != -1
                val lendingDateMatch = it.lendingDateString.toLowerCase().indexOf(lowerCaseFilter) != -1

                filterTextEmpty || nameMatch || lenderMatch || lendingDateMatch
            }
            tableItems.addAll(filteredData)
        }

    }
}