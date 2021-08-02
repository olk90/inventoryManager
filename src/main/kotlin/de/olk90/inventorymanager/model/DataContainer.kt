package de.olk90.inventorymanager.model

class DataContainer(
    val identifier: String? = null,
    val persons: List<Person> = mutableListOf(),
    val items: List<InventoryItem> = mutableListOf(),
    val history: List<LendingHistoryRecord> = mutableListOf()
) {

}