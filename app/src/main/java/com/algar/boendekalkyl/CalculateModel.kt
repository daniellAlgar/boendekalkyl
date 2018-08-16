package com.algar.boendekalkyl

data class CalculateModel(
        val consideration: Int,
        val progress: Int,
        val downPayment: Int,
        val downPaymentPercentage: String
) {
    companion object {

        fun initialModel(): CalculateModel {
            return CalculateModel(
                    consideration = 0,
                    progress = 0,
                    downPayment = 0,
                    downPaymentPercentage = "0 %"
            )
        }
    }
}