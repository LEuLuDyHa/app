package com.github.leuludyha.domain

import com.github.leuludyha.domain.model.ret
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun ret_isCorrect() {
        assertEquals(ret(4), 4)
    }
}