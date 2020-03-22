package com.example.calculus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val numberButtonsIds = arrayListOf(
        R.id.tvOne,
        R.id.tvTwo,
        R.id.tvThree,
        R.id.tvFour,
        R.id.tvFive,
        R.id.tvSix,
        R.id.tvSeven,
        R.id.tvEight,
        R.id.tvNine
    )

    private val operatorButtonsIds = arrayListOf(
        R.id.tvPlus,
        R.id.tvMinus,
        R.id.tvMul,
        R.id.tvDivide,
        R.id.tvDot
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initButtons(numberButtonsIds, true)
        initButtons(operatorButtonsIds, false)

        tvClear.setOnClickListener {
            tvExpression.text = ""
            tvResult.text = ""
        }

        tvEquals.setOnClickListener {
            val text = tvExpression.text.toString()
            try {
                val expression = ExpressionBuilder(text).build()
                val result = expression.evaluate()
                val isDecimal = result % 1 == 0.0

                tvResult.text = if (!isDecimal) result.toString() else result.toInt().toString()
            } catch (err: Exception) {
            }
        }

        tvBack.setOnClickListener {
            val text = tvExpression.text.toString()
            if(text.isNotEmpty()) {
                tvExpression.text = text.drop(1)
            }

            tvResult.text = ""
        }
    }

    private fun initButtons(ids: ArrayList<Int>, shouldClear: Boolean) {
        ids.forEach{
            val view = findViewById<TextView>(it)

            view.setOnClickListener {
                evaluateExpression(view.text.toString(), shouldClear)
            }
        }
    }

    private fun evaluateExpression(string: String, shouldClear: Boolean) {
        if (shouldClear) {
            if (tvResult.text == "") {
                tvExpression.append(string)
                return
            }
            tvResult.text = ""
            tvExpression.text = string
        } else {
            if (tvResult.text == "") {
                tvExpression.append(tvResult.text)
                tvExpression.append(string)
                tvResult.text = ""
                return
            }
            tvExpression.text = tvResult.text
            tvExpression.append(string)
            tvResult.text = ""
        }
    }
}
