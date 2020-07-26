package com.yomi.doggo.data

import com.google.gson.GsonBuilder
import com.yomi.doggo.network.BreedApiException
import com.yomi.doggo.network.BreedService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertFailsWith


class BreedRepositoryTest {
    private lateinit var server: MockWebServer
    private lateinit var retrofit: Retrofit
    private lateinit var service: BreedService
    private lateinit var repo: BreedRepository

    @Before
    fun setup() {
        server = MockWebServer()
        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(server.url("/"))
            .build()
        service = retrofit.create(BreedService::class.java)

        repo = BreedRepository(service)
    }

    @After
    fun teardown() {
        server.close()
    }

    @Test
    fun `successful getBreeds api response is parsed correctly`() = runBlocking {
        server.enqueue(MockResponse().setBody(allBreedsResponseSuccessResponse))
        val breeds = repo.getBreeds()
        assertEquals(16, breeds.size)
    }

    @Test(expected = BreedApiException::class)
    fun `exception is thrown when getBreeds api response status is not success`() {
        runBlocking {
            server.enqueue(MockResponse().setBody(allBreedsResponseErrorResponse))
            assertFailsWith<BreedApiException> { repo.getBreeds() }
        }
    }

    //when response code is 200 but response status is 'error'
    @Test
    fun `exception is thrown when getSampleBreeds api response status is not success`() {
        runBlocking {
            server.enqueue(MockResponse().setBody(allResponseErrorResponse))
            assertFailsWith<BreedApiException> { repo.getSampleBreeds() }
        }
    }

    @Test
    fun `exception is thrown when getSampleBreeds api code is not 200`() {
        runBlocking {
            server.enqueue(MockResponse().setResponseCode(404))
            assertFailsWith<BreedApiException> { repo.getSampleBreeds() }
        }
    }

    private val allBreedsResponseSuccessResponse = "{\n" +
                "   \"message\":{\n" +
                "      \"affenpinscher\":[],\n" +
                "      \"african\":[],\n" +
                "      \"airedale\":[],\n" +
                "      \"akita\":[],\n" +
                "      \"appenzeller\":[],\n" +
                "      \"australian\":[\n" +
                "         \"shepherd\"],\n" +
                "      \"basenji\":[],\n" +
                "      \"beagle\":[],\n" +
                "      \"bluetick\":[],\n" +
                "      \"borzoi\":[],\n" +
                "      \"bouvier\":[],\n" +
                "      \"boxer\":[],\n" +
                "      \"brabancon\":[],\n" +
                "      \"briard\":[],\n" +
                "      \"buhund\":[\n" +
                "         \"norwegian\"\n" +
                "      ],\n" +
                "      \"bulldog\":[\n" +
                "         \"boston\",\n" +
                "         \"english\",\n" +
                "         \"french\"\n" +
                "      ]\n" +
                "   },\n" +
                "   \"status\": \"success\"\n" +
                "   }"


    private val allBreedsResponseErrorResponse = "{\n" +
            "   \"message\":{},\n" +
            "   \"status\": \"error\"\n" +
            "   }"

    private val allResponseErrorResponse = "{\n" +
            "   \"message\":[],\n" +
            "   \"status\": \"error\"\n" +
            "   }"
}