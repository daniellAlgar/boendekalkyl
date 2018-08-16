package com.algar.boendekalkyl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class CalculateFragment : Fragment() {

    private lateinit var viewModel: CalculateViewModel

    private lateinit var consideration: EditText
    private lateinit var downPaymentTextView: TextView
    private lateinit var downPaymentSeekBar: SeekBar
    private lateinit var downPaymentPercentage: TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calculate, container, false)

        consideration = view.findViewById(R.id.consideration_editText)
        consideration.setOnEditorActionListener { textView, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    viewModel.manualConsideration(textView.text.toString())
                    textView.clearFocus()
                    false
                }
                else -> false
            }
        }

        downPaymentTextView = view.findViewById(R.id.downPayment_textView)
        downPaymentSeekBar = view.findViewById(R.id.downPayment_seekBar)
        downPaymentSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(view: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!fromUser) return

                viewModel.manualDownPaymentSeekBar(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
        downPaymentPercentage = view.findViewById(R.id.downPayment_Percentage)

        setupViewModel()

        return view
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(CalculateViewModel::class.java)
        viewModel.model.observe(this, Observer {
            render(it)
        })
    }

    private fun render(data: CalculateModel?) {
        data ?: return

        consideration.setText(data.consideration.toString())

        downPaymentTextView.text = String.format(getString(R.string.downPayment), data.downPayment)
        downPaymentPercentage.text = data.downPaymentPercentage

        downPaymentSeekBar.apply {
            if (data.consideration == 0) {
                isEnabled = false
                progress = 0
            } else {
                isEnabled = true
                max = data.consideration
                progress = data.progress
            }
        }
    }
}
