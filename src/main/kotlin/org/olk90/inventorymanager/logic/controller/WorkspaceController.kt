package org.olk90.inventorymanager.logic.controller

import org.olk90.inventorymanager.logic.History
import org.olk90.inventorymanager.logic.HistoryEntry
import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.model.Person
import tornadofx.*

open class WorkspaceController : Controller() {
    val persons = mutableListOf<Person>().asObservable()

    val inventoryItems = mutableListOf<InventoryItem>().asObservable()
    // used for managing data containers
    val history = mutableListOf<HistoryEntry>().asObservable()

    fun setHistory(h: History) {
        history.clear()
        history.addAll(h.history)
        history.sortByDescending { it.lastAccessTime }
    }

    fun openDataContainer(path: String) {
        TODO("Not yet implemented")
    }
}
