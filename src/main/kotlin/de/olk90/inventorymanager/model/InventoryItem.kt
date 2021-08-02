package de.olk90.inventorymanager.model

import java.time.LocalDate

class InventoryItem(
    name: String? = null,
    available: Boolean = false,
    lendingDate: LocalDate? = null,
    lendingDateString: String? = null,
    info: String? = null,
    category: String? = null,
    motRequired: Boolean = true,
    nextMot: LocalDate? = null,
    nextMotString: String? = null
) {
}