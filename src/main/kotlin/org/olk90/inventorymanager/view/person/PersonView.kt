package org.olk90.inventorymanager.view.person

import javafx.geometry.Pos
import org.olk90.inventorymanager.logic.controller.ObjectStore
import org.olk90.inventorymanager.logic.controller.PersonController
import org.olk90.inventorymanager.model.Person
import org.olk90.inventorymanager.view.common.align
import tornadofx.*

class PersonView : View("Person Overview") {

    private val controller: PersonController by inject()

    init {
        controller.tableItems.addAll(ObjectStore.persons)
    }

    override val root = borderpane {
        center {
            vbox {
                textfield {
                    promptText = "Search"
                    controller.configureFilterListener(this)
                }
                tableview(controller.tableItems) {
                    fitToParentSize()
                    columnResizePolicy = SmartResize.POLICY

                    column("First name", Person::firstNameProperty).apply {
                        align(Pos.CENTER)
                        pctWidth(33.0)
                    }
                    column("Last name", Person::lastNameProperty).apply {
                        align(Pos.CENTER)
                        pctWidth(33.0)
                    }
                    column("Email", Person::emailProperty).apply {
                        align(Pos.CENTER)
                        pctWidth(33.0)
                    }

                    // Update the person inside the view model on selection change
                    controller.model.rebindOnChange(this) {
                        item = it ?: Person()
                    }
                }

            }
        }
        right {
            add(PersonDataFragment())
        }
    }

}
