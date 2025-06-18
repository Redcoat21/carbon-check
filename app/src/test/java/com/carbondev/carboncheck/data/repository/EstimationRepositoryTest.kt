package com.carbondev.carboncheck.data.repository

import com.carbondev.carboncheck.data.remote.api.CarbonInterfaceService
import com.carbondev.carboncheck.data.remote.model.attribute.NetworkFlightAttribute
import com.carbondev.carboncheck.data.remote.model.request.NetworkFlightLeg
import com.carbondev.carboncheck.data.remote.repository.EstimationRepositoryImplementation
import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.model.FlightLeg
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import kotlinx.io.IOException
import org.junit.Before
import org.junit.Test

class EstimationRepositoryTest {
    @MockK
    private lateinit var mockService: CarbonInterfaceService
    @MockK
    private lateinit var mockErrorHandler: ErrorHandler
    private lateinit var repository: EstimationRepositoryImplementation

    // Common test values
    private val passengers = 1
    private val distanceUnit = "km"
    private val testLeg = mockk<FlightLeg> { every { toRemoteModel() } returns mockk() }
    private val legs = listOf(testLeg)

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = EstimationRepositoryImplementation(mockService, mockErrorHandler)
    }

    @Test
    fun `getFlightEstimation should return error if the service return null`() = runTest {
        // Mock response with null body
        coEvery { mockService.getFlightEstimation(any()) } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns null
        }
        every { mockErrorHandler.mapToDomainError(any<IOException>()) } returns ErrorType.NETWORK_ERROR

        // Act & Assert
        repository.getFlightEstimation(passengers, legs, distanceUnit).let { result ->
            assertTrue(result is Result.Error)
            assertTrue((result as Result.Error).exception is IOException)
        }
    }

    @Test
    fun `getFlightEstimation should return error if the service throw an error`() = runTest {
        // Simulate network error
        val networkException = IOException("Network error")
        coEvery { mockService.getFlightEstimation(any()) } throws networkException
        every { mockErrorHandler.mapToDomainError(networkException) } returns ErrorType.NETWORK_ERROR

        // Act & Assert
        repository.getFlightEstimation(passengers, legs, distanceUnit).let { result ->
            assertTrue(result is Result.Error)
            assertEquals(ErrorType.NETWORK_ERROR, (result as Result.Error).type)
            assertEquals(networkException, result.exception)
        }
    }

    @Test
    fun `getFlightEstimation should return estimation data if the service succeed`() = runTest {
        // Setup test data
        val testData = TestData()

        // Create mock network leg
        val mockNetworkLeg = mockk<NetworkFlightLeg>().apply {
            every { departureAirport } returns "LHR"
            every { destinationAirport } returns "JFK"
            every { cabinClass } returns "economy"
        }

        // Prepare mocks for the success path with fluent configuration
        val mockNetworkFlightAttribute = mockk<NetworkFlightAttribute>().apply {
            every { passengers } returns this@EstimationRepositoryTest.passengers
            every { legs } returns listOf(mockNetworkLeg)
            every { estimatedAt } returns testData.estimatedAt
            every { carbonG } returns testData.carbonG
            every { carbonKg } returns testData.carbonKg
            every { carbonLb } returns testData.carbonLb
            every { carbonMt } returns testData.carbonMt
            every { distanceUnit } returns this@EstimationRepositoryTest.distanceUnit
            every { distanceValue } returns testData.distanceValue
        }

        // Configure API response chain
        coEvery { mockService.getFlightEstimation(any()) } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { data } returns mockk {
                    every { id } returns testData.id
                    every { attributes } returns mockNetworkFlightAttribute
                }
            }
        }

        // Act & Assert
        repository.getFlightEstimation(passengers, legs, distanceUnit).let { result ->
            assertTrue(result is Result.Success)
            (result as Result.Success).data.let { estimation ->
                assertEquals(testData.id, estimation.id)
                assertEquals(testData.estimatedAt, estimation.estimatedAt)
                assertEquals(testData.carbonG, estimation.carbonData.gram)
                assertEquals(Pair(distanceUnit, testData.distanceValue), estimation.distance)
            }
        }
    }

    // Data class to hold test constants
    private class TestData {
        val id = "test-id"
        val estimatedAt = Instant.parse("2023-01-01T00:00:00Z")
        val distanceValue = 1000.0
        val carbonG = 250000.0
        val carbonKg = 250.0
        val carbonLb = 551.0
        val carbonMt = 0.25
    }
}