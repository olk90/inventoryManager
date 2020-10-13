package org.olk90.inventorymanager.view

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.geometry.Pos
import org.olk90.inventorymanager.controller.PersonController
import org.olk90.inventorymanager.model.Person
import tornadofx.*

class PersonView : View("Person Overview") {

    private val controller: PersonController by inject()

    override val root = borderpane {
        center {
            tableview(controller.persons) {
                columnResizePolicy = SmartResize.POLICY

                controller.personTable = this
                column("First name", Person::firstNameProperty).apply {
                    align(Pos.CENTER)
                    pctWidth(50.0)
                }
                column("Last name", Person::firstNameProperty).apply {
                    align(Pos.CENTER)
                    pctWidth(50.0)
                }

                // Update the person inside the view model on selection change
                controller.model.rebindOnChange(this) {
                    item = it ?: Person()
                }
            }
        }
        right {
            form {
                fieldset("Add user") {
                    field("First name") {
                        textfield(controller.model.firstName)
                    }
                    field("Last name") {
                        textfield(controller.model.lastName)
                    }
                    buttonbar {
                        button {
                            tooltip("Save")
                            addClass("icon-only")
                            graphic = icon(FontAwesomeIcon.SAVE)
                            enableWhen(controller.model.dirty)
                            action {
                                controller.save()
                            }
                        }
                        button {
                            tooltip("Reset")
                            addClass("icon-only")
                            graphic = icon(FontAwesomeIcon.REMOVE)
                            enableWhen(controller.model.dirty)
                            action {
                                controller.model.rollback()
                            }
                        }
                    }
                }
            }
        }
    }

}
