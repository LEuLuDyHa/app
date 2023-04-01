package com.github.leuludyha.domain.model.recommandation

import com.github.leuludyha.domain.TestMocks
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.recommendation.UserTasteDistance
import com.github.leuludyha.domain.model.library.recommendation.WorkTasteDistance
import org.junit.Test

class TasteDistancesTest {

    @Test
    fun workTasteDistanceIsNotNan() {
        val dist = WorkTasteDistance()
        assert(!dist(TestMocks.user1, Mocks.work1984).isNaN())
    }

    @Test
    fun userTasteDistanceIsNotNan() {
        val dist = UserTasteDistance()
        assert(!dist(TestMocks.user1, TestMocks.user2).isNaN())
    }
}