package de.olk90.inventorymanager.view

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import java.io.PrintWriter
import java.io.StringWriter
import java.text.MessageFormat
import java.util.*


fun messages(key: String, vararg args: Any?): String {
    val locale = Locale.getDefault()
    val bundle: ResourceBundle = ResourceBundle.getBundle("Messages", locale)
    return MessageFormat.format(bundle.getString(key), *args)
}

fun errorDialog(header: String, content: String) {
    val alert = Alert(AlertType.ERROR)
    alert.headerText = header
    alert.contentText = content

    alert.showAndWait()
}

fun exceptionDialog(header: String, ex: Exception) {
    val alert = Alert(AlertType.ERROR)
    alert.headerText = header

    val sw = StringWriter()
    val pw = PrintWriter(sw)
    ex.printStackTrace(pw)
    val exceptionText = sw.toString()

    val label = Label(messages("error.content.stacktrace"))

    val textArea = TextArea(exceptionText)
    textArea.isEditable = false
    textArea.isWrapText = true

    GridPane.setVgrow(textArea, Priority.ALWAYS)
    GridPane.setHgrow(textArea, Priority.ALWAYS)

    val expContent = GridPane()
    expContent.maxWidth = Double.MAX_VALUE
    expContent.add(label, 0, 0)
    expContent.add(textArea, 0, 1)

    alert.dialogPane.expandableContent = expContent
    alert.showAndWait()
}