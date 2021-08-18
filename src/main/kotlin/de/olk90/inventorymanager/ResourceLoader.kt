package de.olk90.inventorymanager

import java.net.URL
import java.text.MessageFormat
import java.util.*

object ResourceLoader {

    fun loadUrl(path: String): URL? {
        return ResourceLoader::class.java.getResource(path)
    }

    fun getResourceBundle(): ResourceBundle {
        val locale = Locale.getDefault()
        return ResourceBundle.getBundle("Messages", locale)
    }

    fun messages(key: String, vararg args: Any?): String {
        val locale = Locale.getDefault()
        val bundle: ResourceBundle = ResourceBundle.getBundle("Messages", locale)
        return MessageFormat.format(bundle.getString(key), *args)
    }

}
