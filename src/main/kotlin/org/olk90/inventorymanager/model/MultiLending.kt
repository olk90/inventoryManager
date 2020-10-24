package org.olk90.inventorymanager.model

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.*
import java.time.LocalDate

class MultiLending(
        lender: Person? = null,
        lendingDate: LocalDate? = null,
        lendingDateString: String? = null,
        items: ObservableList<InventoryItem> = observableListOf()
) {
    val lenderProperty = SimpleObjectProperty<Person>(this, "lender", lender)
    var lender by lenderProperty

    val lendingDateProperty = SimpleObjectProperty<LocalDate>(this, "lendingDate", lendingDate)
    var lendingDate by lendingDateProperty

    val lendingDateStringProperty = SimpleStringProperty(this, "lendingDate", lendingDateString)
    var lendingDateString by lendingDateStringProperty

    val itemsProperty = SimpleListProperty<InventoryItem>(this, "items", items)
    var items by itemsProperty
}

class MultiLendingModel(lending: MultiLending) : ItemViewModel<MultiLending>(lending) {
    val lender = bind(MultiLending::lenderProperty)
    val lendingDate = bind(MultiLending::lendingDateProperty)
    val lendingDateString = bind(MultiLending::lendingDateStringProperty)
    val items = bind(MultiLending::itemsProperty)
}