package com.github.leuludyha.data.api

import com.github.leuludyha.data.RequiringLibraryApiTest
import com.github.leuludyha.data.api.ApiHelper.extractIdFromKey
import com.github.leuludyha.data.api.ApiHelper.rawResponseToModel
import com.github.leuludyha.data.api.ApiHelper.rawResponseToModelResult
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.model.library.Work
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flowOf
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

class ApiHelperTest: RequiringLibraryApiTest() {

    @Test
    fun rawResponseToModelResultReturnsCorrectWork() {
        val rawWork = RawWork(
            key = "/works/key",
            title = null,
            rawAuthors = null,
            coverIds = null,
            subjects = null,
            error = null
        )
        val work = Work(
            id = "key",
            title = null,
            editions = flowOf(),
            authors = flowOf(),
            covers = flowOf(),
            subjects = flowOf(),
        )

        val response = Response.success<RawWork>(rawWork)
        val result = rawResponseToModelResult(response, libraryApi)
        assertThat(result.data).isEqualTo(work)
    }

    @Test
    fun rawResponseToModelResultReturnsErrorOnNullBody() {
        val response = Response.success<RawWork>(null)
        val result = rawResponseToModelResult(response, libraryApi)
        assertThat(result).isInstanceOf(Result.Error::class.java)
    }

    @Test
    fun rawResponseToModelResultReturnsErrorOnErrorInBody() {
        val work = RawWork(
            key = "/works/key",
            title = null,
            rawAuthors = null,
            coverIds = null,
            subjects = null,
            error = "x"
        )
        val response = Response.success<RawWork>(work)
        val result = rawResponseToModelResult(response, libraryApi)
        assertThat(result).isInstanceOf(Result.Error::class.java)
    }

    @Test
    fun rawResponseToModelResultReturnsErrorOnErrorInBodyToModel() {
        val work = RawWork(
            key = "/work/key",
            title = null,
            rawAuthors = null,
            coverIds = null,
            subjects = null,
            error = null
        )
        val response = Response.success<RawWork>(work)
        val result = rawResponseToModelResult(response, libraryApi)
        assertThat(result).isInstanceOf(Result.Error::class.java)
    }

    @Test
    fun rawResponseToModelResultReturnsErrorOnUnsuccessfulResponse() {
        val response = Response.error<RawWork>(404, "Not found".toResponseBody())
        val result = rawResponseToModelResult(response, libraryApi)
        assertThat(result).isInstanceOf(Result.Error::class.java)
    }

    @Test
    fun rawResponseToModelReturnsCorrectWork() {
        val rawWork = RawWork(
            key = "/works/key",
            title = null,
            rawAuthors = null,
            coverIds = null,
            subjects = null,
            error = null
        )
        val work = Work(
            id = "key",
            title = null,
            editions = flowOf(),
            authors = flowOf(),
            covers = flowOf(),
            subjects = flowOf(),
        )

        val response = Response.success<RawWork>(rawWork)
        val result = rawResponseToModel(response, libraryApi)
        assertThat(result).isEqualTo(work)
    }

    @Test
    fun rawResponseToModelIsNullOnNullBody() {
        val response = Response.success<RawWork>(null)
        val result = rawResponseToModel(response, libraryApi)
        assertThat(result).isNull()
    }

    @Test
    fun rawResponseToModelIsNullOnErrorInBody() {
        val work = RawWork(
            key = "/works/key",
            title = null,
            rawAuthors = null,
            coverIds = null,
            subjects = null,
            error = "x"
        )
        val response = Response.success<RawWork>(work)
        val result = rawResponseToModel(response, libraryApi)
        assertThat(result).isNull()
    }

    @Test
    fun rawResponseToModelIsNullOnErrorInBodyToModel() {
        val work = RawWork(
            key = "/work/key",
            title = null,
            rawAuthors = null,
            coverIds = null,
            subjects = null,
            error = null
        )
        val response = Response.success<RawWork>(work)
        val result = rawResponseToModel(response, libraryApi)
        assertThat(result).isNull()
    }

    @Test
    fun rawResponseToModelIsNullOnUnsuccessfulResponse() {
        val response = Response.error<RawWork>(404, "Not found".toResponseBody())
        val result = rawResponseToModel(response, libraryApi)
        assertThat(result).isNull()
    }

    @Test
    fun extractIdFromKeyReturnsCorrectIdOnCorrectKey() {
        val id = extractIdFromKey("/works/OL45804W", "/works/")
        assertThat(id).isNotNull()
        assertThat(id).isEqualTo("OL45804W")
    }

    @Test
    fun extractIdFromKeyReturnsNullOnIncorrectKey() {
        val id = extractIdFromKey("/works/OL45804W", "wrong")
        assertThat(id).isNull()
    }

    @Test
    fun extractIdFromKeyReturnsNullOnEmptyId() {
        val id = extractIdFromKey("/works/", "/works/")
        assertThat(id).isNull()
    }

    @Test
    fun extractIdFromKeyReturnsNullOnNullKey() {
        val id = extractIdFromKey(null, "/works/")
        assertThat(id).isNull()
    }
}