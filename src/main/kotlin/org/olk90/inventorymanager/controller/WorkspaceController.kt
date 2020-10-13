package org.olk90.inventorymanager.controller

import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.model.Person
import tornadofx.*

open class WorkspaceController : Controller() {

    val persons = mutableListOf<Person>().asObservable()
    val inventoryItems = mutableListOf<InventoryItem>().asObservable()

}