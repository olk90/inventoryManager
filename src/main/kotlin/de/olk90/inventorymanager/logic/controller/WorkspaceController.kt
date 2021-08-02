package de.olk90.inventorymanager.logic.controller

import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import com.beust.klaxon.json
import de.olk90.inventorymanager.logic.Config
import de.olk90.inventorymanager.logic.History
import de.olk90.inventorymanager.logic.HistoryEntry
import de.olk90.inventorymanager.model.DataContainer
import de.olk90.inventorymanager.view.errorDialog
import de.olk90.inventorymanager.view.exceptionDialog
import de.olk90.inventorymanager.view.messages
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WorkspaceController {

    @FXML
    lateinit var mainView: BorderPane

    // disable add/delete button when no file is opened
    val dataContainerOpen: SimpleBooleanProperty = SimpleBooleanProperty(false)

    val pathProperty = SimpleStringProperty(this, "path", Config.userHome.toString())
    val identifierProperty = SimpleStringProperty(this, "identifier", "")

    val history = FXCollections.observableArrayList<HistoryEntry>()

    fun openNewDatabase() {
        val resource = javaClass.classLoader.getResource("fxml/databaseFragment.fxml")
        val fragment = FXMLLoader.load<Parent>(resource, Config.getResourceBundle())
        val secondaryScene = Scene(fragment, 300.0, 170.0)

        val newWindow = Stage()
        newWindow.scene = secondaryScene
        newWindow.show()
    }

    fun openPersonsView() {
        val resource = javaClass.classLoader.getResource("fxml/personsView.fxml")
        mainView.center = FXMLLoader.load<Parent>(resource, Config.getResourceBundle())
    }

    fun openInventoryView() {
        val resource = javaClass.classLoader.getResource("fxml/inventoryView.fxml")
        mainView.center = FXMLLoader.load<Parent>(resource, Config.getResourceBundle())
    }

    @Throws(KlaxonException::class)
    fun loadConfigFile() {
        if (!Config.configDirectory.exists()) {
            Config.configDirectory.mkdirs()
        }
        val configFile = Paths.get(System.getProperty("user.home"), ".inventoryManager", "config.json").toFile()

        // initial setup of the file
        if (!configFile.exists()) {
            writeHistory()
        } else {
            // load contents from existing file
            try {
                val history = Klaxon().parse<History>(configFile)
                if (history != null) {
                    setHistory(history)
                }
            } catch (e: KlaxonException) {
                e.printStackTrace()
            }
        }
    }

    private fun writeHistory(entries: List<HistoryEntry> = emptyList()) {
        val configFile = Paths.get(System.getProperty("user.home"), ".inventoryManager", "config.json").toFile()
        val text = json {
            obj("history" to array(entries))
        }.toJsonString(prettyPrint = true)

        // replace special chars to avoid mess in Windows
        val fileContent = stringToCodePoint(text)
        configFile.writeText(fileContent)
    }

    private fun setHistory(h: History) {
        history.clear()
        // remove all entries whose files were deleted
        val existingEntries = h.history.filter { File(it.filePath).exists() }
        history.addAll(existingEntries)

        history.sortByDescending { it.lastAccessTime }
        if (history.isNotEmpty()) {
            pathProperty.value = history.first().filePath
            writeHistory(history)
        }
    }

    //Kindly supported by Stack Overflow
    private fun stringToCodePoint(s: String): String {
        val builder = StringBuilder()
        var index = 0
        while (index < s.length) {
            val cp = Character.codePointAt(s, index)
            val charCount = Character.charCount(cp)
            if (charCount > 1) {
                index += charCount - 1
                require(index < s.length) { "truncated unexpectedly" }
            }
            if (cp < 128) {
                builder.appendCodePoint(cp)
            } else {
                builder.append(String.format("\\u%04X", cp))
            }
            index++
        }
        return builder.toString()
    }

    private fun findAndReplaceCodePoints(s: String): String {
        val cpRegex = "\\\\u[0-9A-F]{4}".toRegex()
        val find = cpRegex.findAll(s)
        var result = s
        find.forEach {
            val hexValue = it.value.substring(2)
            val char = codePointToString(Integer.parseInt(hexValue, 16))
            result = result.replace(it.value, char)
        }
        return result
    }

    private fun codePointToString(cp: Int): String {
        val builder = StringBuilder()
        when {
            Character.isBmpCodePoint(cp) -> builder.append(cp.toChar())
            Character.isValidCodePoint(cp) -> {
                builder.append(Character.highSurrogate(cp))
                builder.append(Character.lowSurrogate(cp))
            }
        }
        return builder.toString()
    }

    fun openDataContainer(documentPath: String) {
        try {
            val dc = Klaxon().parse<DataContainer>(File(documentPath))
            if (dc != null) {
                dc.persons.forEach {
                    it.firstName = findAndReplaceCodePoints(it.firstName)
                    it.lastName = findAndReplaceCodePoints(it.lastName)
                }
                dc.items.forEach {
                    it.name = findAndReplaceCodePoints(it.name)
                    if (!it.lendingDateString.isNullOrEmpty()) {
                        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
                        it.lendingDate = LocalDate.parse(it.lendingDateString, formatter)
                    }
                    if (!it.nextMotString.isNullOrEmpty()) {
                        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
                        it.nextMot = LocalDate.parse(it.nextMotString, formatter)
                    }

                }
                dc.history.forEach {
                    if (!it.lendingDateString.isNullOrEmpty()) {
                        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
                        it.lendingDate = LocalDate.parse(it.lendingDateString, formatter)
                    }
                    if (!it.returnDateString.isNullOrEmpty()) {
                        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
                        it.returnDate = LocalDate.parse(it.returnDateString, formatter)
                    }
                }

                identifierProperty.value = dc.identifier
                pathProperty.value = documentPath
                ObjectStore.fillStore(dc.persons, dc.items, dc.history)
                updateHistory(documentPath)

                dataContainerOpen.set(true)

                // reload table contents, otherwise there will be issues when loading another container from history
                getPersonControllerInstance(javaClass.classLoader).reloadTableItems()
                getInventoryControllerInstance(javaClass.classLoader).reloadTableItems()

            } else {
                errorDialog(messages("error.header.open"), messages("error.content.path", documentPath))
            }
        } catch (e: KlaxonException) {
            exceptionDialog(messages("error.header.exception"), e.stackTraceToString(), e)
        }
    }

    private fun updateHistory(filePath: String) {
        val entry = history.find { it.filePath == filePath }
        if (entry != null) {
            entry.lastAccessTime = System.currentTimeMillis()
        } else {
            history.add(HistoryEntry(filePath, System.currentTimeMillis()))
        }
        writeHistory(history)

    }
}