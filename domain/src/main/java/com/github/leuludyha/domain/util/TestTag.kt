package com.github.leuludyha.domain.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

data class TestTag(val tag: String)

fun Modifier.testTag(tag: TestTag): Modifier {
    return this.testTag(tag.tag)
}