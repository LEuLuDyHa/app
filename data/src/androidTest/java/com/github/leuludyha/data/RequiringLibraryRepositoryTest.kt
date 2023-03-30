package com.github.leuludyha.data

import com.github.leuludyha.data.api.LibraryApi
import com.github.leuludyha.data.repository.LibraryRepositoryImpl
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.data.repository.datasourceImpl.LibraryRemoteDataSourceImpl
import com.github.leuludyha.domain.repository.LibraryRepository
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

open class RequiringLibraryRepositoryTest: RequiringLibraryDatabaseTest() {
    protected lateinit var libraryApi: LibraryApi
    protected lateinit var mockWebServer: MockWebServer
    protected lateinit var remoteDataSource: LibraryRemoteDataSource

    protected lateinit var libraryRepository: LibraryRepository

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        libraryApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibraryApi::class.java)
        remoteDataSource = LibraryRemoteDataSourceImpl(libraryApi)

        libraryRepository = LibraryRepositoryImpl(remoteDataSource, localDataSource)
    }

    @After
    @Throws(IOException::class)
    fun shutdown() {
        mockWebServer.shutdown()
    }
}