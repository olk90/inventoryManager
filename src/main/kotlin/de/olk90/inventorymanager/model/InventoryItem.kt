package de.olk90.inventorymanager.model

import com.beust.klaxon.Json
import de.olk90.inventorymanager.logic.LendingDate
import de.olk90.inventorymanager.logic.MotDate
import de.olk90.inventorymanager.logic.ObjectStore
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty

class InventoryItem (
    name: String = "",
    available: Boolean? = false,
    lendingDate: LendingDate = LendingDate(),
    lender: Int? = -1,
    info: String? = null,
    category: String = "",
    nextMot: MotDate = MotDate(),
    motRequired: Boolean? = true,
) {

    @Json(ignored = true)
    val nameProperty = SimpleStringProperty(name)

    @Json(ignored = true)
    val availableProperty = SimpleBooleanProperty(available ?: false)

    @Json(ignored = true)
    val lendingDateProperty = SimpleObjectProperty(lendingDate)

    @Json(ignored = true)
    val lenderProperty = SimpleIntegerProperty(lender ?: -1)

    @Json(ignored = true)
    val infoProperty = SimpleStringProperty(info)

    @Json(ignored = true)
    val categoryProperty = SimpleStringProperty(category)

    @Json(ignored = true)
    val motRequiredProperty = SimpleBooleanProperty(motRequired ?: false)

    @Json(ignored = true)
    val nextMotProperty = SimpleObjectProperty(nextMot)

    val id = ObjectStore.nextInventoryId()

    var name: String
        get() = nameProperty.value
        set(value) = nameProperty.set(value)

    var available: Boolean
        get() = availableProperty.value
        set(value) = availableProperty.set(value)

    var lendingDate: LendingDate
        get() = lendingDateProperty.value
        set(value) = lendingDateProperty.set(value)

    var lender: Int
        get() = lenderProperty.value
        set(value) = lenderProperty.set(value)

    var info: String
        get() = infoProperty.value
        set(value) = infoProperty.set(value)

    var category: String
        get() = categoryProperty.value
        set(value) = categoryProperty.set(value)

    var motRequired: Boolean?
        get() = motRequiredProperty.value
        set(value) = motRequiredProperty.set(value ?: false)

    var nextMot: MotDate
        get() = nextMotProperty.value
        set(value) = nextMotProperty.set(value)

}