package de.olk90.inventorymanager.model

data class DataContainer(
    val identifier: String = "",
    val persons: List<Person> = mutableListOf(),
    val items: List<InventoryItem> = mutableListOf(),
    val history: List<LendingHistoryRecord> = mutableListOf()
)