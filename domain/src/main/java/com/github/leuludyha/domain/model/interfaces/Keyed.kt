package com.github.leuludyha.domain.model.interfaces

interface Keyed {

    /** @return The unique key by which to identify this object */
    fun getId(): String

}