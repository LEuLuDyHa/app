package com.github.leuludyha.data

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object FileReader {
    fun readResourceFromFile(inputStream: InputStream): String {
        try {
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