package org.olk90.inventorymanager.controller

import org.olk90.inventorymanager.model.InventoryItem
import tornadofx.*

class InventoryController : Controller() {

    val inventoryItems = mutableListOf<InventoryItem>().asObservable()
}