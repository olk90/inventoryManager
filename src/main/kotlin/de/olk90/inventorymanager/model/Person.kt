package de.olk90.inventorymanager.model

import com.beust.klaxon.Json
import javafx.beans.property.SimpleStringProperty
import de.olk90.inventorymanager.logic.controller.ObjectStore
import tornadofx.*

class Person(
        firstName: String? = null,
        lastName: String? = null,
        email: String? = null
) {

    @Json(ignored = true)
    val firstNameProperty = SimpleStringProperty(this, "firstName", firstName)
    @Json(ignored = true)
    val lastNameProperty = SimpleStringProperty(this, "lastName", lastName)
    @Json(ignored = true)
    val emailProperty = SimpleStringProperty(this, "email", email)

    val id = ObjectStore.nextPersonId()
    var firstName: String by firstNameProperty
    var lastName: String by lastNameProperty
    var email: String by emailProperty

    fun getFullName(): String {
        return "$firstName $lastName"
    }

}

class PersonModel(person: Person) : ItemViewModel<Person>(person) {
    val firstName = bind(Person::firstNameProperty)
    val lastName = bind(Person::lastNameProperty)
    val email = bind(Person::emailProperty)
}
