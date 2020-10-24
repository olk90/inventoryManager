package org.olk90.inventorymanager.logic.controller

import javafx.collections.ObservableList
import javafx.scene.control.TextField
import org.olk90.inventorymanager.model.*
import tornadofx.*

fun getInventoryControllerInstance(): InventoryController {
    return find(InventoryController::class)
}

class InventoryController : Controller() {

    val model = InventoryItemModel(InventoryItem())

    val multiLendingModel = MultiLendingModel(MultiLending())

    private val filteredData = SortedFilteredList(ObjectStore.inventoryItems)
    val tableItems = mutableListOf<InventoryItem>().asObservable()

    fun save(lendItem: Boolean = false) {
        model.commit()

        val item = model.item
        val i = ObjectStore.findInventoryById(item.id)!!
        if (lendItem) {
            i.available = false
            i.lender = item.lender
            i.lendingDate = item.lendingDate
            val lenderId = item.lender
            updateLenderNameProperty(lenderId, i)
        } else {
            i.name = item.name
            i.available = item.available
        }


        getWorkspaceControllerInstance().writeDcFile()
    }

    private fun updateLenderNameProperty(lenderId: Int, item: InventoryItem) {
        if (lenderId > -1) {
            val lender = ObjectStore.persons.find { it.id == lenderId }
            item.lenderNameProperty.value = lender!!.getFullName()
        } else {
            item.lenderNameProperty.value = ""
        }
    }

    fun add() {
        val item = InventoryItem(model.name.value, model.available.value, model.lendingDate.value)
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

    fun returnItems(items: List<InventoryItem>) {
        items.forEach {
            it.lender = -1
            it.lendingDate = null
            it.lendingDateString = null
            it.available = true
        }
        getWorkspaceControllerInstance().writeDcFile()
    }

    fun lendMultipleItems() {
        val lender = multiLendingModel.lender.value
        val lendingDate = multiLendingModel.lendingDate.value

        multiLendingModel.items.forEach {
            val item = ObjectStore.findInventoryById(it.id)!!
            item.lender = lender.id
            item.available = false
            item.lendingDate = lendingDate
            updateLenderNameProperty(item.lender, item)
        }

        // no need to keep the data in the model
        multiLendingModel.rollback()

        getWorkspaceControllerInstance().writeDcFile()
    }
}