package de.olk90.inventorymanager.logic.controller

import de.olk90.inventorymanager.model.*
import de.olk90.inventorymanager.view.inventory.HistoryView
import impl.org.controlsfx.autocompletion.SuggestionProvider
import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.ObservableList
import javafx.scene.control.TextField
import tornadofx.*
import java.time.LocalDate

fun getInventoryControllerInstance(): InventoryController {
    return find(InventoryController::class)
}

class InventoryController : Controller() {

    val model = InventoryItemModel(InventoryItem())

    val multiLendingModel = MultiLendingModel(MultiLending())

    // used in InventoryContextMenu
    val selectedItemAvailable = SimpleBooleanProperty(false)

    private val filteredData = SortedFilteredList(ObjectStore.inventoryItems)
    val tableItems = mutableListOf<InventoryItem>().asObservable()
    val historyItems = mutableListOf<LendingHistoryRecord>().asObservable()

    val provider: SuggestionProvider<String> = SuggestionProvider.create(ObjectStore.categories)

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
            selectedItemAvailable.set(i.available)
        } else {
            i.name = item.name
            i.available = item.available
            i.nextMot = item.nextMot
            i.category = item.category
            ObjectStore.updateCategories()
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
        val item = InventoryItem(
                name = model.name.value,
                available = model.available.value,
                lendingDate = model.lendingDate.value,
                info = model.info.value,
                category = model.category.value,
                nextMot = model.nextMot.value
        )
        insertItem(item)
        getWorkspaceControllerInstance().writeDcFile()
    }

    private fun insertItem(item: InventoryItem) {
        ObjectStore.inventoryItems.add(item)
        ObjectStore.updateCategories()
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

                val nextMotMatch = if (it.nextMotString != null) {
                    it.nextMotString.toLowerCase().indexOf(lowerCaseFilter) != -1
                } else {
                    false
                }

                val infoMatch = if (it.info != null) {
                    it.info.toLowerCase().indexOf(lowerCaseFilter) != -1
                } else {
                    false
                }

                val categoryMatch = if (it.category != null) {
                    it.category.toLowerCase().indexOf(lowerCaseFilter) != -1
                } else {
                    false
                }

                filterTextEmpty || nameMatch || lenderMatch || lendingDateMatch || nextMotMatch || infoMatch || categoryMatch
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
            createLendingRecord(it)
            it.lender = -1
            it.lendingDate = null
            it.lendingDateString = null
            it.available = true
            selectedItemAvailable.set(true)
        }
        getWorkspaceControllerInstance().writeDcFile()
    }

    private fun createLendingRecord(item: InventoryItem) {
        val record = LendingHistoryRecord(
                lendingDate = item.lendingDate,
                returnDate = LocalDate.now()
        )
        record.lender = item.lender
        record.item = item.id
        record.lendingDateString = record.lendingDate.toString()
        record.returnDateString = record.returnDate.toString()
        ObjectStore.history.add(record)
    }

    fun lendMultipleItems() {
        multiLendingModel.commit()

        val lender = multiLendingModel.lender.value
        val lendingDate = multiLendingModel.lendingDate.value
        val lendingDateString = multiLendingModel.lendingDateString.value

        multiLendingModel.items.forEach {
            val item = ObjectStore.findInventoryById(it.id)!!
            item.lender = lender.id
            item.available = false
            item.lendingDate = lendingDate
            item.lendingDateString = lendingDateString
            updateLenderNameProperty(item.lender, item)
        }

        getWorkspaceControllerInstance().writeDcFile()
    }

    fun updateProvider() {
        provider.clearSuggestions()
        provider.addPossibleSuggestions(ObjectStore.categories)
    }

    fun setupView(item: InventoryItem): HistoryView {
        historyItems.clear()
        val records = ObjectStore.getHistoryOfItem(item)
        historyItems.addAll(records)
        return HistoryView(item)
    }
}