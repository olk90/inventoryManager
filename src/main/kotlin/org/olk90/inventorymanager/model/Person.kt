package org.olk90.inventorymanager.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Person(
        firstName: String? = null,
        lastName: String? = null
) {

    val firstNameProperty = SimpleStringProperty(this, "firstName", firstName)
    var firstName by firstNameProperty

    val lastNameProperty = SimpleStringProperty(this, "lastName", lastName)
    var lastName by lastNameProperty

}

class PersonModel(person: Person) : ItemViewModel<Person>(person) {
    val firstName = bind(Person::firstNameProperty)
    val lastName = bind(Person::lastNameProperty)
}