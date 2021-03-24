package com.michaelprince.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.michaelprince.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateBtn.setOnClickListener { calculateTip() }
        binding.costOfServiceEditText.setOnKeyListener { view, keycode, _ ->
            handleKeyEvent(
                view,
                keycode
            )
        }
    }


    private fun calculateTip() {
        // Get the costOfService and convert it to a string
        val stringInTextField = binding.costOfServiceEditText.text.toString()

        // Re-convert it to a Double
        val cost = stringInTextField.toDoubleOrNull()

//        Checking if the text field is empty
        if (cost == null) {
//            if the text field is empty do not show any result
            binding.tipResult.text = ""

//            A code that notifies the user that the text field is empty
            val toast = Toast.makeText(this, "Your Field is Empty", Toast.LENGTH_SHORT)
            return toast.show()
        }

//Conditional statements
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        // multiply the tip by the cost of service provided
        var tip = tipPercentage * cost

//    Checking to see if the switch button (roundUpSwitch) is checked
        if (binding.roundUpSwitch.isChecked) {
            tip = ceil(tip)
        }
        displayTip(tip)
    }

    // Display the formatted tip value on screen
    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}

