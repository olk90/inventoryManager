package org.olk90.inventorymanager.model

import com.beust.klaxon.Json
import javafx.beans.property.*
import org.olk90.inventorymanager.logic.controller.ObjectStore
import tornadofx.*
import java.time.LocalDate

class InventoryItem(
        name: String? = null,
        available: Boolean = false,
        lendingDate: LocalDate? = null,
        lendingDateString: String? = null
) {

    @Json(ignored = true)
    val nameProperty = SimpleStringProperty(this, "name", name)

    @Json(ignored = true)
    val availableProperty = SimpleBooleanProperty(this, "available", available)

    @Json(ignored = true)
    val lenderProperty = SimpleIntegerProperty(this, "lender", -1)

    @Json(ignored = true)
    val lenderNameProperty = SimpleStringProperty(this, "lenderName", null)

    @Json(ignored = true)
    val lendingDateProperty = SimpleObjectProperty<LocalDate>(this, "lendingDate", lendingDate)

    @Json(ignored = true)
    val lendingDateStringProperty = SimpleStringProperty(this, "lendingDate", lendingDateString)

    val id = ObjectStore.nextInventoryId()
    var name by nameProperty
    var available by availableProperty
    var lender by lenderProperty

    @Json(ignored = true)
    var lendingDate by lendingDateProperty

    @Json(name = "lendingDate")
    var lendingDateString by lendingDateStringProperty

}

class InventoryItemModel(item: InventoryItem) : ItemViewModel<InventoryItem>(item) {
    val name = bind(InventoryItem::nameProperty)
    val available = bind(InventoryItem::availableProperty)
    val lender = bind(InventoryItem::lenderProperty)
    val lenderName = bind(InventoryItem::lenderNameProperty)
    val lendingDate = bind(InventoryItem::lendingDateProperty)
    val lendingDateString = bind(InventoryItem::lendingDateStringProperty)
}
