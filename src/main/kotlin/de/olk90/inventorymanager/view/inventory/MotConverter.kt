package de.olk90.inventorymanager.view.inventory

import javafx.util.StringConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MotConverter : StringConverter<LocalDate>() {

    var pattern = "MM/yyyy"
    var dateFormatter = DateTimeFormatter.ofPattern(pattern)

    override fun toString(date: LocalDate?): String {
        return if (date != null) {
            dateFormatter.format(date)
        } else {
            ""
        }
    }

    override fun fromString(string: String): LocalDate {
        if (string.isEmpty()) {
            return LocalDate.now()
        }
        val split = string.split("/")
        val month = split[0].toInt()
        val year = split[1].toInt()
        return LocalDate.of(year, month, 1);
    }

}