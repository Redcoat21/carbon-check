package com.carbondev.carboncheck.domain.usecase

import com.carbondev.carboncheck.domain.common.ErrorType
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.exception.ErrorHandler
import com.carbondev.carboncheck.domain.model.FlightEstimation
import com.carbondev.carboncheck.domain.model.FlightLeg
import com.carbondev.carboncheck.domain.repository.EstimationRepository
import com.carbondev.carboncheck.domain.usecase.carbon.CalculateFlightCarbonEstimationUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CalculateFlightCarbonEstimationUseCaseTest {
    @MockK
    private lateinit var mockRepository: EstimationRepository
    @MockK
    private lateinit var mockErrorHandler: ErrorHandler
    private lateinit var useCase: CalculateFlightCarbonEstimationUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = CalculateFlightCarbonEstimationUseCase(
            repository = mockRepository,
            errorHandler = mockErrorHandler
        )

        // Set up common mocking for error handler
        every { mockErrorHandler.mapToDomainError(any<IllegalArgumentException>()) } returns ErrorType.VALIDATION_ERROR
    }

    @Test
    fun `invoke should return validation error when passengers count is less than 1`() = runTest {
        // Arrange
        val passengers = 0
        val legs = listOf<FlightLeg>(
            mockk()
        )

        // Act
        val result = useCase.invoke(passengers = passengers, legs = legs)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.VALIDATION_ERROR, (result as Result.Error).type)
    }

    @Test
    fun `invoke should return validation error when legs are empty`() = runTest {
        // Arrange
        val passengers = 1
        val legs = emptyList<FlightLeg>()

        // Act
        val result = useCase.invoke(passengers = passengers, legs = legs)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.VALIDATION_ERROR, (result as Result.Error).type)
    }

    @Test
    fun `invoke should return validation error when give invalid distance unit`() = runTest {
        // Arrange
        val passengers = 1
        val legs = listOf<FlightLeg>(mockk())
        val distanceUnit = "invalid_unit"

        // Act
        val result = useCase.invoke(passengers = passengers, legs = legs, distanceUnit = distanceUnit)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.VALIDATION_ERROR, (result as Result.Error).type)
    }

    @Test
    fun `invoke should return FlightEstimation when given valid data`() = runTest {
        // Arrange
        val passengers = 1
        val legs = listOf<FlightLeg>(mockk())
        val expectedEstimation = mockk<Result.Success<FlightEstimation>>()

        coEvery { mockRepository.getFlightEstimation(passengers, legs, "km") } returns expectedEstimation

        // Act
        val result = useCase.invoke(passengers = passengers, legs = legs)

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(expectedEstimation, result)
    }
}