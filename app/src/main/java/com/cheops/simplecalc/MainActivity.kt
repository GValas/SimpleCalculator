package com.cheops.simplecalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

enum class CalcButton(val symbol: String) {
    Zero("0"),
    One("1"),
    Two("2"),
    Three("3"),
    Four("4"),
    Five("5"),
    Six("6"),
    Seven("7"),
    Eight("8"),
    Nine("9"),
    Add("+"),
    Substract("-"),
    Multiply("*"),
    Divide("/"),
    Clear("AC"),
    Delete("<<"),
    Empty(""),
    Equal("="),
    Dot("."),
}


class MainActivity : AppCompatActivity() {

    private val calcButtonsTypes = listOf(
        CalcButton.Seven,
        CalcButton.Eight,
        CalcButton.Nine,
        CalcButton.Substract,
        CalcButton.Clear,
        CalcButton.Four,
        CalcButton.Five,
        CalcButton.Six,
        CalcButton.Add,
        CalcButton.Delete,
        CalcButton.One,
        CalcButton.Two,
        CalcButton.Three,
        CalcButton.Divide,
        CalcButton.Empty,
        CalcButton.Equal,
        CalcButton.Zero,
        CalcButton.Dot,
        CalcButton.Multiply,
        CalcButton.Empty
    )

    private val calcButtons = mutableListOf<Button>()
    private val lineSize = 5


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mainLayout = findViewById<ConstraintLayout>(R.id.constraintLayout)


        // buttons
        for ((idx, cb) in calcButtonsTypes.withIndex()) {


            // creating the button
            val button = Button(this).apply {
                id = View.generateViewId()
                text = cb.symbol
                layoutParams = ConstraintLayout.LayoutParams(toDps(60), toDps(60))
                setOnClickListener { pressButton(cb) }
            }
            mainLayout.addView(button)
            calcButtons.add(button)

            // connections start from second button
            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayout)

            if ((idx) % lineSize == 0) {
                val refButton = if (idx == 0) textMain else calcButtons[idx - lineSize]
                constraintSet.connect(button.id, ConstraintSet.START, refButton.id, ConstraintSet.START, 0)
                constraintSet.connect(button.id, ConstraintSet.TOP, refButton.id, ConstraintSet.BOTTOM, 0)
            } else {
                val refButton = calcButtons[idx - 1]
                constraintSet.connect(button.id, ConstraintSet.START, refButton.id, ConstraintSet.END, 0)
                constraintSet.connect(button.id, ConstraintSet.TOP, refButton.id, ConstraintSet.TOP, 0)
            }
            constraintSet.applyTo(mainLayout)

        }


    }

    private fun pressButton(cb: CalcButton) {
        when (cb) {

            // display new digit
            CalcButton.One, CalcButton.Two, CalcButton.Three, CalcButton.Four, CalcButton.Five,
            CalcButton.Six, CalcButton.Seven, CalcButton.Eight, CalcButton.Nine, CalcButton.Zero -> {
                textMain.text = textMain.text.toString() + cb.symbol
            }

            // display operator
            CalcButton.Add, CalcButton.Substract, CalcButton.Multiply, CalcButton.Divide -> {
                if (textMain.text != "") {
                    textOperand.text = if (textOperator.text != "") getComputation() else textMain.text
                    textOperator.text = cb.symbol
                    textMain.text = ""
                }
            }

            // reset computations
            CalcButton.Clear -> {
                textMain.text = ""
                textOperand.text = ""
                textOperator.text = ""
            }

            // remove last digit
            CalcButton.Delete -> {
                if (textMain.text != "") {
                    textMain.text = textMain.text.toString().dropLast(1)
                }
            }

            // apply computation
            CalcButton.Equal -> {
                textMain.text = getComputation()
                textOperator.text = ""
                textOperand.text = ""
            }
        }

    }

    private fun getComputation(): String {
        if (textOperand.text.isNotEmpty() && textMain.text.isNotEmpty()
        ) {
            val a = textOperand.text.toString().toInt()
            val b = textMain.text.toString().toInt()
            val x = textOperator.text.toString()
            return when (x) {
                "+" -> (a + b).toString()
                "-" -> (a - b).toString()
                "*" -> (a * b).toString()
                "/" -> (a / b).toString()
                else -> ""
            }.toString()
        }
        return ""
    }


}
