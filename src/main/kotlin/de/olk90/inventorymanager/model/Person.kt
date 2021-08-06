package de.olk90.inventorymanager.model

import com.beust.klaxon.Json
import de.olk90.inventorymanager.logic.shared.ObjectStore
import javafx.beans.property.SimpleStringProperty

class Person(
    firstName: String = "",
    lastName: String = "",
    email: String = ""
) {

    @Json(ignored = true)
    val firstNameProperty = SimpleStringProperty(firstName)

    @Json(ignored = true)
    val lastNameProperty = SimpleStringProperty(lastName)

    @Json(ignored = true)
    val emailProperty = SimpleStringProperty(email)

    val id = ObjectStore.nextPersonId()

    var firstName: String
        get() = firstNameProperty.value
        set(value) = firstNameProperty.set(value)

    var lastName: String
        get() = lastNameProperty.value
        set(value) = lastNameProperty.set(value)

    var email: String
        get() = emailProperty.value
        set(value) = emailProperty.set(value)

    fun getFullName(): String {
        return "$firstNameProperty $lastNameProperty"
    }
}