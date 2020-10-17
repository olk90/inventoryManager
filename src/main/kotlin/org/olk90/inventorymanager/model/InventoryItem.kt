package org.olk90.inventorymanager.model

import com.beust.klaxon.Json
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.olk90.inventorymanager.logic.controller.ObjectStore
import tornadofx.*
import java.time.LocalDate

class InventoryItem(
        name: String? = null,
        available: Boolean = false,
        lender: Person? = null,
        lendingDate: LocalDate? = null
) {

    @Json(ignored = true)
    val nameProperty = SimpleStringProperty(this, "name", name)
    @Json(ignored = true)
    val availableProperty = SimpleBooleanProperty(this, "available", available)
    @Json(ignored = true)
    val lenderProperty = SimpleObjectProperty<Person>(this, "lender", lender)
    @Json(ignored = true)
    val lendingDateProperty = SimpleObjectProperty<LocalDate>(this, "lendingDate", lendingDate)

    val id = ObjectStore.nextInventoryId()
    var name by nameProperty
    var available by availableProperty
    var lender by lenderProperty
    var lendingDate by lendingDateProperty

}

class InventoryItemModel(item: InventoryItem) : ItemViewModel<InventoryItem>(item) {
    val name = bind(InventoryItem::nameProperty)
    val available = bind(InventoryItem::availableProperty)
    val lender = bind(InventoryItem::lenderProperty)
    val lendingDate = bind(InventoryItem::lendingDateProperty)
}
