package com.github.leuludyha.domain.model.library

enum class CoverSize {
    Small,
    Medium,
    Large;

    override fun toString(): String = when (this) {
        Small -> "S"
        Medium -> "M"
        Large -> "L"
    }
}