package org.olk90.inventorymanager.model

import com.beust.klaxon.Json
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.olk90.inventorymanager.logic.controller.ObjectStore
import tornadofx.*
import java.time.LocalDate

class InventoryItem(
        name: String? = null,
        available: Boolean = false,
        lendingDate: LocalDate? = null,
        lendingDateString: String? = null,
        info: String? = null,
        nextMot: LocalDate? = null,
        nextMotString: String? = null
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
    val lendingDateStringProperty = SimpleStringProperty(this, "lendingDateString", lendingDateString)

    @Json(ignored = true)
    val infoProperty = SimpleStringProperty(this, "info", info)

    @Json(ignored = true)
    val nextMotProperty = SimpleObjectProperty<LocalDate>(this, "nextMot", nextMot)

    @Json(ignored = true)
    val nextMotStringProperty = SimpleStringProperty(this, "nextMotString", nextMotString)

    val id = ObjectStore.nextInventoryId()

    var name by nameProperty
    var available by availableProperty
    var lender by lenderProperty

    @Json(name = "info")
    var info by infoProperty

    @Json(ignored = true)
    var lendingDate by lendingDateProperty

    @Json(name = "lendingDate")
    var lendingDateString by lendingDateStringProperty

    @Json(ignored = true)
    var nextMot by nextMotProperty

    @Json(name = "nextMot")
    var nextMotString by nextMotStringProperty

}

class InventoryItemModel(item: InventoryItem) : ItemViewModel<InventoryItem>(item) {
    val name = bind(InventoryItem::nameProperty)
    val available = bind(InventoryItem::availableProperty)
    val lender = bind(InventoryItem::lenderProperty)
    val lendingDate = bind(InventoryItem::lendingDateProperty)
    val lendingDateString = bind(InventoryItem::lendingDateStringProperty)
    val info = bind(InventoryItem::infoProperty)
    val nextMot = bind(InventoryItem::nextMotProperty)
    val nextMotString = bind(InventoryItem::nextMotStringProperty)
}
