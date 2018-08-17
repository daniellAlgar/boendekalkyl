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
                progress = progress
        )

        val expected = 0
        assertEquals(expected, actual)
    }

    @Test
    fun calculateDownPayment_MaxDownPayment() {
        val consideration = 1000000

        val actual = viewModel.calculateDownPayment(
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
                progress = progress
        )

        val expected = 500000
        assertEquals(expected, actual)
    }

    @Test
    fun formatDownPaymentPercentage_MinDownPayment() {
        val consideration = 1000000
        val downPayment = 0

        val actual = viewModel.formatDownPaymentPercentage(
                consideration = consideration,
                downPayment = downPayment
        )

        val expected = "0 %"
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

    @Test
    fun calculateExtraCost() {
        val model = CalculateModel(
                consideration = 3000000,
                downPayment = 450000,
                tidigarePantbrev = 100000,
                progress = 450000,
                downPaymentPercentage = "15 %"
        )

        val actual = viewModel.calculateExtraCost(model = model)

        val expected = 95200
        assertEquals(expected, actual)
    }

    val a = """
        Villan kostar 3 000 000 kr
        Har tidigare pantbrev 100 000 kr
        Lägger 15% kontantinsats = 450 000 kr

        Lagfart = 3 000 000 * 0.015 + 825 = 45 825
        Pantbrevskostnad = 3 000 000 - 100 000 - 450 000 = 2 450 000
        Pantbrev = 2 450 000 * 0.02 + 375 = 49 375

        Extra kostnad = 45 825 + 49 375 = 95 200
    """.trimIndent()
}