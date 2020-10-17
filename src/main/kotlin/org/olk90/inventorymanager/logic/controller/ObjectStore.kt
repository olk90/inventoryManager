package org.olk90.inventorymanager.logic.controller

import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.model.Person
import tornadofx.*

object ObjectStore {

    val persons = mutableListOf<Person>().asObservable()
    val inventoryItems = mutableListOf<InventoryItem>().asObservable()

    fun nextPersonId(): Int {
        return persons.size
    }

    fun findPersonById(id: Int): Person? {
        return persons.find { it.id == id }
    }

    fun nextInventoryId(): Int {
        return inventoryItems.size
    }

    fun findInventoryById(id: Int): InventoryItem? {
        return inventoryItems.find { it.id == id }
    }

    fun fillStore(p: List<Person>, i: List<InventoryItem>) {
        persons.clear()
        inventoryItems.clear()
        persons.addAll(p)
        inventoryItems.addAll(i)
    }
}