package org.olk90.inventorymanager.model

import com.beust.klaxon.Json
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class DataContainer(
        identifier: String? = null,
        val persons: List<Person> = mutableListOf(),
        val items: List<InventoryItem> = mutableListOf()
) {

    @Json(ignored = true)
    val identifierProperty = SimpleStringProperty(this, "identifier", identifier)

    var identifier: String by identifierProperty
}

class DataContainerModel(container: DataContainer) : ItemViewModel<DataContainer>(container) {
    val identifier = bind(DataContainer::identifierProperty)
}