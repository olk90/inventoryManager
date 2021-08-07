package de.olk90.inventorymanager.logic.datahelpers

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import com.beust.klaxon.KlaxonException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Target(AnnotationTarget.FIELD)
annotation class IsoDate

class LendingDate @JvmOverloads constructor(
    @IsoDate
    val date: LocalDate = LocalDate.of(1970, 1, 1)
) {
    override fun toString(): String {
        val isDummy = date.year == 1970
        return if (isDummy) {
            ""
        } else {
            date.toString()
        }
    }
}

val lendingDateConverter = object : Converter {

    override fun canConvert(cls: Class<*>) = cls == LocalDate::class.java

    override fun fromJson(jv: JsonValue) =
        if (jv.string != null) {
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE
            LocalDate.parse(jv.string, formatter)
        } else {
            throw KlaxonException("Couldn't parse date: ${jv.string}")
        }

    override fun toJson(value: Any) = """ { "date" : $value } """

}

@Target(AnnotationTarget.FIELD)
annotation class MonthYearDate

class MotDate @JvmOverloads constructor(
    @MonthYearDate
    val date: LocalDate = LocalDate.of(1970, 1, 1)
) {
    override fun toString(): String {
        val isDummy = date.year == 1970
        return if (isDummy) {
            ""
        } else {
            date.toString()
        }
    }
}

val motDateConverter = object : Converter {

    override fun canConvert(cls: Class<*>) = cls == LocalDate::class.java

    override fun fromJson(jv: JsonValue): Any? {
        return if (jv.string != null) {
            val formatter = DateTimeFormatter.ofPattern("d/MMM/yyyy")
            LocalDate.parse(jv.string, formatter)
        } else {
            val formatter = DateTimeFormatter.ofPattern("d/MMM/yyyy")
            LocalDate.parse("1/Jan/2000", formatter)
        }
    }

    override fun toJson(value: Any) = """ { "date" : $value } """

}
