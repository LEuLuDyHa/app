package com.github.leuludyha.data.db

import androidx.room.TypeConverter

/**
 * Converter from [WorkEntity] to [ByteArray] and conversely. Used to store [WorkEntity]
 * as an embedded field.
 */
class WorkEntityConverter {
    @TypeConverter
    fun fromWorkEntity(workEntity: WorkEntity) : ByteArray {
        val byteId = workEntity.workId.toByteArray()
        val title = workEntity.title
        return if(title != null) byteId + 0 + title.toByteArray() else byteId
    }

    @TypeConverter
    fun toWorkEntity(bytes: ByteArray) : WorkEntity {
        val string = String(bytes)
        val id = string.takeWhile { it.code != 0 }
        // drop 1 more for the 0 char
        val title = string.dropWhile { it.code != 0 }.drop(1).ifEmpty { null }

        return WorkEntity(
            workId = id,
            title = title
        )
    }
}