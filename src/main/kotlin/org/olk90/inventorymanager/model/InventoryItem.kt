package org.olk90.inventorymanager.model

import java.sql.Timestamp

class InventoryItem(
        val name: String,
        val available: Boolean,
        val lender: User,
        val lendingDate: Timestamp
) {

}