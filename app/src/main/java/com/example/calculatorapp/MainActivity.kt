package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var inputTextview : TextView? = null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputTextview = findViewById(R.id.inputTextview)
    }

    fun onDigit(view : View){
        inputTextview?.append((view as Button).text)
        lastNumeric=true
        lastDot=false
    }

    fun onClear(view : View){
        inputTextview?.text=""
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            inputTextview?.append(".")
            lastNumeric=false
            lastDot=true
        }
    }

    fun onOperator(view: View){
        inputTextview?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                inputTextview?.append((view as Button).text)
                lastNumeric=false
                lastDot=false
            }
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var expression = inputTextview?.text.toString()
            var prefix = ""

            if(expression.startsWith("-")){
                prefix="-"
                expression=expression.substring(1)
            }

            try{
                if(expression.contains("-")){
                    val splitExpression = expression.split('-')
                    var one = splitExpression[0]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    var two = splitExpression[1]
                    inputTextview?.text=removeZero((one.toDouble()-two.toDouble()).toString())
                }
                else if(expression.contains("+")){
                    val splitExpression = expression.split('+')
                    var one = splitExpression[0]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    var two = splitExpression[1]
                    inputTextview?.text=removeZero((one.toDouble()+two.toDouble()).toString())
                }
                else if(expression.contains("x")){
                    val splitExpression = expression.split('x')
                    var one = splitExpression[0]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    var two = splitExpression[1]
                    inputTextview?.text=removeZero((one.toDouble()*two.toDouble()).toString())
                }
                else if(expression.contains("/")){
                    val splitExpression = expression.split('/')
                    var one = splitExpression[0]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    var two = splitExpression[1]
                    inputTextview?.text=removeZero((one.toDouble()/two.toDouble()).toString())
                }

            }catch(e : ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZero(result : String) : String{
        var ans=result
        if(result.contains(".0")){
            ans = result.substring(0,result.length-2)
        }

        return ans
    }

    private fun isOperatorAdded(value : String) : Boolean {
        return if(value.startsWith("-")){
            false
        }
        else{
            value.contains("+") ||
                    value.contains("-") ||
                    value.contains("x") ||
                    value.contains("/")
        }
    }

}