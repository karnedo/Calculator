package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.CalculatorTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Shadow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import java.util.Stack

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    mainScreen()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun mainScreen(){
    Column(Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        var expression by rememberSaveable {
            mutableStateOf("0")
        }

        //Resultado operaciones
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f)
                .background(Color.Gray)
        ){
            Column(Modifier.fillMaxSize()){
                Row(Modifier.fillMaxWidth().weight(1f)){
                    //poner aquí el resultado de la operación anterior
                    Expression(expression)
                }
                Row(Modifier.fillMaxWidth().weight(1f)){
                    Expression(calculate(expression).toString())
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Black)
        ){}

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3.5f)
                .background(Color.DarkGray)
        ){

            Row(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            //botonera
            ){

                Column(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)){

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        OperationButton("7", { expression += "7" })
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        OperationButton("4", { expression += "4" })
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        OperationButton("1", { expression += "1" })
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                        OperationButton("0", { expression += "0" })
                    }
                }

                Column(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)){
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        OperationButton("8", { expression += "8" })
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        OperationButton("5", { expression += "5" })
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        OperationButton("2", { expression += "2" })
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                        OperationButton(".", { expression += "." })
                    }
                }

                Column(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)){
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        OperationButton("9", { expression += "9" })
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        OperationButton("6", { expression += "6" })
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        OperationButton("3", { expression += "3" })
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                        OperationButton("=", { expression += "=" })
                    }
                }

                Column(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)){
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        OperationButton("+", { expression += "+" })
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        OperationButton("-", { expression += "-" })
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        OperationButton("/", { expression += "/" })
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                        OperationButton("%", { expression += "%" })
                    }
                }

            }

        }

    }
}

fun calculate(expression: String): Double {
    val postfixExpression = infixToPostfix(expression)
    return evaluatePostfix(postfixExpression)
}

private fun infixToPostfix(expression: String): String {
    val output = StringBuilder()
    val operators = Stack<Char>()
    val precedence = hashMapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2)

    for (char in expression) {
        if (char.isDigit() || char == '.') {
            output.append(char)
        } else if (char == '(') {
            operators.push(char)
        } else if (char == ')') {
            while (!operators.isEmpty() && operators.peek() != '(') {
                output.append(operators.pop())
            }
            operators.pop()
        } else {
            while (!operators.isEmpty() && precedence.getOrDefault(operators.peek(), 0) >= precedence.getOrDefault(char, 0)) {
                output.append(operators.pop())
            }
            operators.push(char)
        }
    }

    while (!operators.isEmpty()) {
        output.append(operators.pop())
    }

    return output.toString()
}

private fun evaluatePostfix(expression: String): Double {
    val stack = Stack<Double>()

    for (char in expression) {
        if (char.isDigit() || char == '.') {
            stack.push(char.toString().toDouble())
        } else {
            val operand2 = stack.pop()
            val operand1 = stack.pop()
            val result = when (char) {
                '+' -> operand1 + operand2
                '-' -> operand1 - operand2
                '*' -> operand1 * operand2
                '/' -> operand1 / operand2
                else -> throw IllegalArgumentException("Invalid operator")
            }
            stack.push(result)
        }
    }

    return stack.pop()
}

@Composable
fun Expression(expr: String){
    Text(
        text = expr, Modifier.fillMaxWidth(),
        style = TextStyle(
                fontSize = 40.sp,
        shadow = Shadow(
            color = Color.White
        )
    ),
    textAlign = TextAlign.End,
    )
}

@Composable
fun OperationButton(operacion: String, onKeyPresseded: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxSize(),
        enabled = true,
        onClick = { onKeyPresseded() },
        border = BorderStroke(1.dp, Color.White),
        shape = RoundedCornerShape(50))
    {
        Text(text = operacion,
            style = TextStyle(
                color = Color.White,
                fontSize = 25.sp)
        )

    }
}