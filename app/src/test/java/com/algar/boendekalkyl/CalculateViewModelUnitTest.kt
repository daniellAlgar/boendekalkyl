package com.algar.boendekalkyl

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CalculateViewModelUnitTest {

    private val viewModel = CalculateViewModel()

    @Test
    fun parseConsideration_NormalUseCase() {
        val amounts = listOf("1", "25", "1000")
        val actual = arrayListOf<Int>()

        amounts.mapTo(actual) { amount ->
            viewModel.parseConsideration(amount)
        }

        val expected = listOf(1, 25, 1000)
        assertEquals(expected, actual)
    }

    @Test
    fun parseConsideration_EdgeCases() {
        val amounts = listOf("", "0", "00", "1.1")
        val actual = arrayListOf<Int>()

        amounts.mapTo(actual) { amount ->
            viewModel.parseConsideration(amount)
        }

        val expected = listOf(0, 0, 0, 1)
        assertEquals(expected, actual)
    }

    @Test
    fun calculateDownPayment_MinDownPayment() {
        val consideration = 1000000
        val progress = 0

        val actual = viewModel.calculateDownPayment(
                consideration = consideration,
                progress = progress
        )

        val expected = 150000
        assertEquals(expected, actual)
    }

    @Test
    fun calculateDownPayment_MaxDownPayment() {
        val consideration = 1000000

        val actual = viewModel.calculateDownPayment(
                consideration = consideration,
                progress = consideration
        )

        val expected = consideration
        assertEquals(expected, actual)
    }

    @Test
    fun calculateDownPayment_HalfDownPayment() {
        val consideration = 1000000
        val progress = 500000

        val actual = viewModel.calculateDownPayment(
                consideration = consideration,
                progress = progress
        )

        val expected = 575000
        assertEquals(expected, actual)
    }

    @Test
    fun formatDownPaymentPercentage_MinDownPayment() {
        val consideration = 1000000
        val downPayment = 150000

        val actual = viewModel.formatDownPaymentPercentage(
                consideration = consideration,
                downPayment = downPayment
        )

        val expected = "15 %"
        assertEquals(expected, actual)
    }

    @Test
    fun formatDownPaymentPercentage_MaxDownPayment() {
        val consideration = 1000000

        val actual = viewModel.formatDownPaymentPercentage(
                consideration = consideration,
                downPayment = consideration
        )

        val expected = "100 %"
        assertEquals(expected, actual)
    }

    @Test
    fun formatDownPaymentPercentage_HalfDownPayment() {
        val consideration = 1000000
        val downPayment = 500000

        val actual = viewModel.formatDownPaymentPercentage(
                consideration = consideration,
                downPayment = downPayment
        )

        val expected = "50 %"
        assertEquals(expected, actual)
    }
}