package org.olk90.inventorymanager.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.sql.Timestamp

class InventoryItem(
        name: String? = null,
        available: Boolean = false,
        lender: Person? = null,
        lendingDate: Timestamp? = null
) {

    val nameProperty = SimpleStringProperty(this, "name", name)
    var name by nameProperty

    val availableProperty = SimpleBooleanProperty(this, "available", available)
    var available by availableProperty

    val lenderProperty = SimpleObjectProperty<Person>(this, "lender", lender)
    var lender by lenderProperty

    val lendingDateProperty = SimpleObjectProperty<Timestamp>(this, "lendingDate", lendingDate)
    var lendingDate by lendingDateProperty

}

class InventoryItemModel(item: InventoryItem) : ItemViewModel<InventoryItem>(item) {
    val name = bind(InventoryItem::nameProperty)
    val available = bind(InventoryItem::availableProperty)
    val lender = bind(InventoryItem::lenderProperty)
    val lendingDate = bind(InventoryItem::lendingDateProperty)
}