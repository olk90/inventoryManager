package org.olk90.inventorymanager.model

data class DataContainer(
        val identifier: String,
        val persons: List<Person>,
        val items: List<InventoryItem>
)
