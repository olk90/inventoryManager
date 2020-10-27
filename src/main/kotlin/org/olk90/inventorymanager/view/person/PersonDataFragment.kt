package org.olk90.inventorymanager.view.person

import de.jensd.fx.glyphs.octicons.OctIcon
import org.olk90.inventorymanager.logic.controller.PersonController
import org.olk90.inventorymanager.view.common.icon
import org.olk90.inventorymanager.view.common.messages
import tornadofx.*

class PersonDataFragment(private val create: Boolean = false) : Fragment() {

    val controller: PersonController by inject()

    override val root = borderpane {
        center {
            form {
                val title = if (create) messages("label.addUser") else messages("label.editUser")
                fieldset(title) {
                    field(messages("person.firstName")) {
                        textfield(controller.model.firstName)
                    }
                    field(messages("person.lastName")) {
                        textfield(controller.model.lastName)
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
                            enableWhen(controller.model.dirty)
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
                            }
                        }
                    }
                }
            }

        }

    }
}
