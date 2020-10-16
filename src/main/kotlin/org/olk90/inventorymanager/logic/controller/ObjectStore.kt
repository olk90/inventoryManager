package org.olk90.inventorymanager.logic.controller

import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.model.Person
import tornadofx.*

object ObjectStore {

    val persons = mutableListOf<Person>().asObservable()
    val inventoryItems = mutableListOf<InventoryItem>().asObservable()

}