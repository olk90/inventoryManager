package de.olk90.inventorymanager.model

import de.olk90.inventorymanager.logic.shared.ObjectStore

class Person(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = ""
) {

    val id = ObjectStore.nextPersonId()

    fun getFullName(): String {
        return "$firstName $lastName"
    }
}