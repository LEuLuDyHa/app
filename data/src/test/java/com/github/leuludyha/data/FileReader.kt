package com.github.leuludyha.data

import java.io.IOException
import java.io.InputStreamReader

object FileReader {
    fun readResourceFromFile(loader: ClassLoader, fileName: String): String {
        try {
            val inputStream = loader.getResourceAsStream(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }
}