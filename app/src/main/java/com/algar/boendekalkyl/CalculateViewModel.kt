package com.algar.boendekalkyl

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculateViewModel : ViewModel() {

    var model = MutableLiveData<CalculateModel>().apply {
        value = CalculateModel.initialModel()
    }

    fun manualConsideration(amount: String) {
        val consideration = parseConsideration(amount)
        val downPayment = calculateDownPayment(
                progress = model.value!!.progress)
        val downPaymentPercentage = formatDownPaymentPercentage(
                consideration = consideration,
                downPayment = downPayment
        )

        model.value = model.value?.copy(
                consideration = consideration,
                downPayment = downPayment,
                downPaymentPercentage = downPaymentPercentage
        )
    }

    fun manualDownPaymentSeekBar(progress: Int) {
        val consideration = model.value!!.consideration

        val downPayment = calculateDownPayment(
                progress = progress
        )

        val downPaymentPercentage = formatDownPaymentPercentage(
                consideration = consideration,
                downPayment = downPayment
        )

        model.value = model.value?.copy(
                progress = progress,
                downPayment = downPayment,
                downPaymentPercentage = downPaymentPercentage
        )
    }

    fun manualPantbrev(amount: String) {
        model.value = model.value?.copy(tidigarePantbrev = parseConsideration(amount = amount))
    }

    @VisibleForTesting
    fun formatDownPaymentPercentage(consideration: Int, downPayment: Int): String {
        return "${downPayment * 100 / consideration} %"
    }

    @VisibleForTesting
    fun calculateDownPayment(progress: Int): Int {
        return progress
    }

    @VisibleForTesting
    fun parseConsideration(amount: String): Int {
        return if (amount.isEmpty() || amount.isBlank()) {
            0
        } else {
            amount.toDouble().toInt()
        }
    }

    fun calculateExtraCost(model: CalculateModel): Int {
        val lagfart = model.consideration * 0.015 + 825
        val kostnadPantbrev = model.consideration - model.tidigarePantbrev - model.downPayment
        val pantbrev = kostnadPantbrev * 0.02 + 375

        return (lagfart + pantbrev).toInt()
    }
}