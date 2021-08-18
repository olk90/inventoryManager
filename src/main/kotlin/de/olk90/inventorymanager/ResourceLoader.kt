package de.olk90.inventorymanager

import java.net.URL

object ResourceLoader {

    fun loadUrl(path: String): URL? {
        return ResourceLoader::class.java.getResource(path)
    }

}
