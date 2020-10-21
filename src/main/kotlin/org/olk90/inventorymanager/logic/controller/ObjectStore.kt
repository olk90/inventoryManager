package org.olk90.inventorymanager.logic.controller

import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.model.Person
import tornadofx.*

object ObjectStore {

    val persons = mutableListOf<Person>().asObservable()
    val inventoryItems = mutableListOf<InventoryItem>().asObservable()

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

    fun fillStore(p: List<Person>, i: List<InventoryItem>) {
        persons.clear()
        inventoryItems.clear()
        persons.addAll(p)
        inventoryItems.addAll(i)
    }
}