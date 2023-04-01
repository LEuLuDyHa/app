package com.github.leuludyha.domain.model.library.recommendation

import kotlin.math.exp

interface ActivationFunction {

    /** Apply the function */
    operator fun invoke(x: Float): Float

    /** Apply the gradient of the function */
    fun gradient(x: Float): Float
}

class Sigmoid : ActivationFunction {

    override fun invoke(x: Float): Float {
        return 1.0f / (1.0f + exp(-x))
    }

    override fun gradient(x: Float): Float {
        val sigX = this(x)
        return sigX * (1.0f - sigX)
    }
}

