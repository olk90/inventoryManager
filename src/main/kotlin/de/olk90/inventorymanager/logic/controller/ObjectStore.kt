package de.olk90.inventorymanager.logic.controller

import de.olk90.inventorymanager.model.InventoryItem
import de.olk90.inventorymanager.model.LendingHistoryRecord
import de.olk90.inventorymanager.model.Person
import tornadofx.*

object ObjectStore {

    // data structures
    val persons = mutableListOf<Person>().asObservable()
    val inventoryItems = mutableListOf<InventoryItem>().asObservable()
    val history = mutableListOf<LendingHistoryRecord>().asObservable()

    // UI
    val categories = mutableListOf<String>().asObservable()

    fun nextPersonId(): Int {
        return if (persons.isEmpty()) {
            0
        } else {
            persons.maxOf { it.id } + 1
        }
    }

    fun findPersonById(id: Int): Person? {
        return persons.find { it.id == id }
    }

    fun nextInventoryId(): Int {
        return if (inventoryItems.isEmpty()) {
            0
        } else {
            inventoryItems.maxOf { it.id } + 1
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
        getInventoryControllerInstance().updateProvider()
    }

    fun getHistoryOfItem(item: InventoryItem): List<LendingHistoryRecord> {
        return history.filter { it.item == item.id }.sortedByDescending { it.lendingDate }.toList()
    }
}