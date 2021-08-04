package de.olk90.inventorymanager.model

import de.olk90.inventorymanager.logic.shared.ObjectStore
import java.time.LocalDate

class InventoryItem(
    var name: String = "",
    var available: Boolean = false,
    var lendingDate: LocalDate? = null,
    var lendingDateString: String? = null,
    var info: String? = null,
    var category: String? = null,
    var motRequired: Boolean = true,
    var nextMot: LocalDate? = null,
    var nextMotString: String? = null
) {

    val id = ObjectStore.nextInventoryId()
}