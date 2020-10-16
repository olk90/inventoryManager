package org.olk90.inventorymanager.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class DataContainer(
        identifier: String? = null,
        val persons: List<Person> = mutableListOf(),
        val items: List<InventoryItem> = mutableListOf()
) {
    val identifierProperty = SimpleStringProperty(this, "identifier", identifier)
    var indentifier by identifierProperty
}

class DataContainerModel(container: DataContainer) : ItemViewModel<DataContainer>(container) {
    val identifier = bind(DataContainer::identifierProperty)
}