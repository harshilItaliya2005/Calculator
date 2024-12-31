package com.example.calculator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme
import kotlin.math.roundToLong

var text = mutableStateOf("")

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Scaffold(topBar = {
                    CenterAlignedTopAppBar(title = {
                        Text(
                            text = "Calculator",
                            color = Color.Blue,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    })
                }) {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .background(color = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .background(color = Color.White)
                                .fillMaxSize()
                                .weight(.2f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = text.value,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .background(color = Color.DarkGray)
                                .fillMaxSize()
                                .weight(.8f),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Row(
                                modifier = Modifier
                                    .padding()
                                    .weight(1f)
                            ) {

                                myButton(scope = this, s = "C", color = Color.Blue)
                                myButton(scope = this, s = "()", color = Color.Blue)
                                myButton(scope = this, s = "%", color = Color.Blue)
                                myButton(scope = this, s = "/", color = Color.Blue)
                            }
                            Row(
                                modifier = Modifier
                                    .padding()
                                    .weight(1f)
                            ) {

                                myButton(scope = this, s = "7", color = Color.LightGray)
                                myButton(scope = this, s = "8", color = Color.LightGray)
                                myButton(scope = this, s = "9", color = Color.LightGray)
                                myButton(scope = this, s = "x", color = Color.Blue)
                            }
                            Row(
                                modifier = Modifier
                                    .padding()
                                    .weight(1f)
                            ) {

                                myButton(scope = this, s = "4", color = Color.LightGray)
                                myButton(scope = this, s = "5", color = Color.LightGray)
                                myButton(scope = this, s = "6", color = Color.LightGray)
                                myButton(scope = this, s = "-", color = Color.Blue)
                            }
                            Row(
                                modifier = Modifier
                                    .padding()
                                    .weight(1f)
                            ) {

                                myButton(scope = this, s = "1", color = Color.LightGray)
                                myButton(scope = this, s = "2", color = Color.LightGray)
                                myButton(scope = this, s = "3", color = Color.LightGray)
                                myButton(scope = this, s = "+", color = Color.Blue)
                            }
                            Row(
                                modifier = Modifier
                                    .padding()
                                    .weight(1f)
                            ) {

                                myButton(scope = this, s = "0", color = Color.LightGray)
                                myButton(scope = this, s = ".", color = Color.LightGray)
                                myButton(scope = this, s = "R", color = Color.LightGray)
                                myButton(scope = this, s = "=", color = Color.Blue)
                            }
                        }
                    }
                }
            }
        }
    }
}

var first_Value = 0.0
var second_Value = 0.0
var current: String? = null

@Composable
fun myButton(scope: RowScope, s: String = "", color: Color = Color.Black) {
    scope.apply {
        Button(
            onClick = {
                try {
                    if (s == "+" || s == "-" || s == "x" || s == "/") {
                        if (text.value.isNotEmpty()) {
                            val currentInput = text.value.toDouble()
                            if (current != null) {
                                if (current == "+") {
                                    first_Value += currentInput
                                } else if (current == "-") {
                                    first_Value -= currentInput
                                } else if (current == "x") {
                                    first_Value *= currentInput
                                } else if (current == "/") {
                                    if (currentInput != 0.0) {
                                        first_Value /= currentInput
                                    } else {
                                        text.value = "ERROR : DIV/0"
                                    }
                                }
                            } else {
                                first_Value = currentInput
                            }
                        }
                        current = s
                        text.value = ""
                    } else if (s == "=") {
                        if (text.value.isNotEmpty() && current != null) {
                            second_Value = text.value.toDouble()
                            if (current == "+") {
                                first_Value += second_Value
                            } else if (current == "-") {
                                first_Value -= second_Value
                            } else if (current == "x") {
                                first_Value *= second_Value
                            } else if (current == "/") {
                                if (second_Value != 0.0) {
                                    first_Value /= second_Value
                                } else {
                                    text.value = "ERROR : DIV/0"
                                }
                            } 
                            if (first_Value.rem(1).equals(0.0)) {
                                text.value = first_Value.roundToLong().toString()
                            } else {
                                text.value = first_Value.toString()
                            }
                            current = null
                        }
                    } else if (s == "R") {
                        text.value = text.value.dropLast(1)
                    } else if (s == "C") {
                        text.value = ""
                        first_Value = 0.0
                        second_Value = 0.0
                        current = null
                    } else if (s == "%") {
                        val value = text.value.toDoubleOrNull()
                        if (value != null) {
                            text.value = "${value / 100}"
                        } else {
                            text.value = "ERROR"
                        }
                    } else if (s == ".") {
                        if (text.value.isEmpty()) {
                            text.value = "0."
                        } else if (!text.value.contains(".")) {
                            text.value += "."
                        }
                    } else if (s == "0") {
                        if (text.value != "0") {
                            text.value += s
                        }
                    } else {
                        text.value += s
                    }
                } catch (e: Exception) {
                    text.value = "ERROR"
                    Log.e("MyButton", "Error in onClick : ", e)
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = color),
            border = BorderStroke(3.dp, Color.Black),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = s, fontSize = 40.sp, color = Color.Black)
        }
    }
}


