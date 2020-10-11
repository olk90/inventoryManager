package org.olk90.inventorymanager.model

class User(
        val firstName: String,
        val lastName: String,
        val objectsOnLoan: MutableList<InventoryItem>
) {
}