package de.olk90.inventorymanager.view.person

import de.jensd.fx.glyphs.octicons.OctIcon
import de.olk90.inventorymanager.logic.controller.PersonController
import de.olk90.inventorymanager.model.Person
import de.olk90.inventorymanager.model.PersonModel
import de.olk90.inventorymanager.view.common.SAVE
import de.olk90.inventorymanager.view.common.icon
import de.olk90.inventorymanager.view.common.messages
import tornadofx.*

class PersonDataFragment(private val create: Boolean = false) : Fragment() {

    val controller: PersonController by inject()

    init {
        if (create) {
            controller.model = PersonModel(Person())
        }
    }

    override val root = borderpane {

        maxWidth = 400.0
        minWidth = 400.0

        center {
            form {
                val title = if (create) messages("label.addUser") else messages("label.editUser")
                fieldset(title) {
                    field(messages("person.firstName")) {
                        textfield(controller.model.firstName).validator {
                            if (it.isNullOrBlank()) error(messages("error.validation.firstName")) else null
                        }
                    }
                    field(messages("person.lastName")) {
                        textfield(controller.model.lastName).validator {
                            if (it.isNullOrBlank()) error(messages("error.validation.lastName")) else null
                        }
                    }
                    field(messages("person.email")) {
                        textfield(controller.model.email)
                    }
                }

                fieldset {
                    buttonbar {
                        button {
                            tooltip(messages("tooltip.save"))
                            addClass("icon-only")
                            graphic = icon(OctIcon.CHECK)
                            enableWhen(controller.model.dirty.and(controller.model.validationContext.valid))
                            shortcut(SAVE)
                            action {
                                if (create) {
                                    controller.add()
                                    close()
                                } else {
                                    controller.save()
                                }
                            }
                        }
                        button {
                            tooltip(messages("tooltip.reset"))
                            addClass("icon-only")
                            graphic = icon(OctIcon.X)
                            enableWhen(controller.model.dirty)
                            action {
                                controller.model.rollback()
                                if (create) {
                                    close()
                                }
                            }
                        }
                    }
                }
            }

            controller.model.validate()
        }

    }
}
