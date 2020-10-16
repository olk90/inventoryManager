package org.olk90.inventorymanager.logic.controller

import com.beust.klaxon.Klaxon
import javafx.scene.control.TableView
import org.olk90.inventorymanager.model.Person
import org.olk90.inventorymanager.model.PersonModel
import org.olk90.inventorymanager.model.PersonSet
import tornadofx.*
import java.io.File

class PersonController : Controller() {

    var personTable: TableView<Person> by singleAssign()
    val model = PersonModel(Person())

    fun save() {
        model.commit()
        val person = model.item
        // TODO persist data
    }

    fun add() {
        val person = Person(model.firstName.value, model.lastName.value, model.email.value)
        ObjectStore.persons.add(person)
    }

    fun parsePersonsFromFile(jsonDocument: File) {
        val set = Klaxon().parse<PersonSet>(jsonDocument)
        if (set != null) {
            ObjectStore.persons.clear()
            ObjectStore.persons.addAll(set.persons)
        }
    }
}