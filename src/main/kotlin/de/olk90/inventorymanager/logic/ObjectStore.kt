package de.olk90.inventorymanager.logic

import de.olk90.inventorymanager.model.InventoryItem
import de.olk90.inventorymanager.model.LendingHistoryRecord
import de.olk90.inventorymanager.model.Person
import impl.org.controlsfx.autocompletion.SuggestionProvider
import javafx.collections.FXCollections
import javafx.collections.ObservableList

object ObjectStore {

    val persons: ObservableList<Person> = FXCollections.observableArrayList()
    val inventoryItems: ObservableList<InventoryItem> = FXCollections.observableArrayList()
    val history: ObservableList<LendingHistoryRecord> = FXCollections.observableArrayList()

    val categories: ObservableList<String> = FXCollections.observableArrayList()
    val categoryProvider: SuggestionProvider<String> = SuggestionProvider.create(categories)


    fun nextPersonId(): Int {
        return if (persons.isEmpty()) {
            0
        } else {
            val personList = mutableListOf<Person>()
            personList.addAll(persons)
            personList.maxOf { it.id } + 1
        }
    }

    fun findPersonById(id: Int): Person? {
        return persons.find { it.id == id }
    }

    fun nextInventoryId(): Int {
        return if (inventoryItems.isEmpty()) {
            0
        } else {
            val inventoryList = mutableListOf<InventoryItem>()
            inventoryList.addAll(inventoryItems)
            inventoryList.maxOf { it.id } + 1
        }
    }

    fun findInventoryById(id: Int): InventoryItem? {
        return inventoryItems.find { it.id == id }
    }

    fun fillStore(p: List<Person>, i: List<InventoryItem>, h: List<LendingHistoryRecord>) {
        persons.clear()
        inventoryItems.clear()
        history.clear()

        persons.addAll(p)
        inventoryItems.addAll(i)
        history.addAll(h)
        updateCategories()
    }

    fun updateCategories() {
        categories.clear()
        // map to set in order to eliminate duplicates
        val c = inventoryItems.map { it.category }.toSet()
        categories.addAll(c)
        // provide updated values to the suggestions in text field
        updateProvider()
    }

    private fun updateProvider() {
        categoryProvider.clearSuggestions()
        categoryProvider.addPossibleSuggestions(categories)
    }

    fun getHistoryOfItem(item: InventoryItem): List<LendingHistoryRecord> {
        return history.filter { it.item == item.id }.sortedByDescending { it.lendingDate }.toList()
    }
}