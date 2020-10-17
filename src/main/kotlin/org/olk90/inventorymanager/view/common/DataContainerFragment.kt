package org.olk90.inventorymanager.view.common

import de.jensd.fx.glyphs.octicons.OctIcon
import javafx.stage.FileChooser
import org.olk90.inventorymanager.logic.Config
import org.olk90.inventorymanager.logic.controller.WorkspaceController
import org.olk90.inventorymanager.model.DataContainer
import org.olk90.inventorymanager.model.DataContainerModel
import org.olk90.inventorymanager.model.FileExtension
import tornadofx.*
import java.io.File

class DataContainerFragment : Fragment("Create new data container") {

    private val controller: WorkspaceController by inject()
    val model = DataContainerModel(DataContainer())

    override val root = borderpane {
        center {
            form {
                fieldset {
                    field("Identifier") {
                        textfield(model.identifier)
                    }
                }
            }
        }

        bottom {
            buttonbar {
                button {
                    tooltip("Create container")
                    addClass("icon-only")
                    graphic = icon(OctIcon.CHECK)
                    action {
                        val fileChooser = FileChooser().apply {
                            extensionFilters.addAll(Config.jsonFilters)
                            initialDirectory = Config.userHome
                        }

                        var file = fileChooser.showSaveDialog(currentWindow)
                        if (file != null) {
                            try {
                                if (!file.name.endsWith(FileExtension.JSON.extension)) {
                                    file = File(file.absolutePath + FileExtension.JSON.extension)
                                }
                                val content = controller.buildDcFile(
                                        model.identifier.value,
                                        emptyList(),
                                        emptyList()
                                )
                                Config.model.pathProperty.value = file.absolutePath
                                File(file.absolutePath).writeText(content)
                                controller.updateHistory(file.absolutePath)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        close()
                    }
                }
                button {
                    tooltip("Cancel")
                    addClass("icon-only")
                    graphic = icon(OctIcon.X)
                    action {
                        close()
                    }
                }
            }
        }
    }
}
