package de.olk90.inventorymanager.model

import de.olk90.inventorymanager.logic.shared.ObjectStore
import java.time.LocalDate

class InventoryItem(
    var name: String = "",
    var available: Boolean = false,
    var lendingDate: LocalDate? = null,
    var lendingDateString: String = "",
    var info: String = "",
    var category: String = "",
    var motRequired: Boolean = true,
    var nextMot: LocalDate? = null,
    var nextMotString: String = ""
) {

    val id = ObjectStore.nextInventoryId()
}