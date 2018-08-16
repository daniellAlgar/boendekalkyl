package com.algar.boendekalkyl

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculateViewModel : ViewModel() {

    private val minInterest = 0.15

    var model = MutableLiveData<CalculateModel>().apply {
        value = CalculateModel.initialModel()
    }

    fun manualConsideration(amount: String) {
        val consideration = parseConsideration(amount)
        val downPayment = calculateDownPayment(
                consideration = consideration,
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
                consideration = consideration,
                progress = progress)
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

    @VisibleForTesting
    fun formatDownPaymentPercentage(consideration: Int, downPayment: Int): String {
        return if (downPayment == 0) {
            "${100*minInterest} %"
        } else {
            "${downPayment*100 / consideration} %"
        }
    }

    @VisibleForTesting
    fun calculateDownPayment(consideration: Int, progress: Int): Int {

        val minDownPayment = consideration * minInterest
        val percentOfProgress = if (progress == 0) {
            0.0f
        } else {
            progress.toFloat() / consideration
        }

        val scaledDownPaymentAddition = minDownPayment * (1 - percentOfProgress)
        return (progress + scaledDownPaymentAddition).toInt()
    }

    @VisibleForTesting
    fun parseConsideration(amount: String): Int {
        return if (amount.isEmpty() || amount.isBlank()) {
            0
        } else {
            amount.toDouble().toInt()
        }
    }
}