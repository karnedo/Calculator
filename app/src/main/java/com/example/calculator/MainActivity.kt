package com.example.calculator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
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
import java.math.BigDecimal
import java.util.LinkedList
import java.util.Queue
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

        var lastExpression by rememberSaveable {
            mutableStateOf("")
        }

        //val terms = mutableListOf<String>()

        //Resultado operaciones
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f)
                .background(Color.Gray)
        ){
            Column(Modifier.fillMaxSize()){
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                    Expression(40, lastExpression) //Resultado de la última operación
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                    //Expresión introducida
                    Expression(35, listToString(parse(expression))) //Expresión introducida
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                    //Resultado
                    Expression(50,  calculate(expression).toString()) //Resultado expresión
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Black)
        ){
            Column(Modifier
                .fillMaxSize()){
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        OperationButton("(", { expression += "(" })
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        OperationButton(")", { expression += ")" })
                    }
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        OperationButton("CE", { expression = "" })
                    }
                }
            }

        }

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
                        .weight(2f)){
                    Row(Modifier.fillMaxWidth().weight(1f)){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)){
                            OperationButton("8", { expression += "8" })
                        }
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)){
                            OperationButton("9", { expression += "9" })
                        }
                    }
                    Row(Modifier.fillMaxWidth().weight(1f)){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)){
                            OperationButton("5", { expression += "5" })
                        }
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)){
                            OperationButton("6", { expression += "6" })
                        }
                    }
                    Row(Modifier.fillMaxWidth().weight(1f)){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)){
                            OperationButton("2", { expression += "2" })
                        }
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)){
                            OperationButton("3", { expression += "3" })
                        }
                    }
                    Row(Modifier.fillMaxWidth().weight(1f)){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)){
                            OperationButton("=", {
                                lastExpression = expression
                                expression = ""
                            })
                        }
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
                        OperationButton("x", { expression += "*" })
                    }
                }

            }

        }

    }
}

@Composable
fun Expression(fontSize: Int, expr: String){
    Text(
        text = expr, Modifier.fillMaxWidth(),
        style = TextStyle(
                fontSize = fontSize.sp,
        shadow = Shadow(
            color = Color.White
        )
    ),
    textAlign = TextAlign.End,
    )
}

private fun calculate(expression: String): Double{
    return calcPostfix(infixToPostfix(parse(expression)))
}

private fun listToString(list: MutableList<Token>): String{
    var str = ""
    for (i in list){
        if(i.type == 'n'){
            str += i.value.toInt()
        }else{
            str += i.type
        }
    }
    return str
}

//Parseamos el string introducido para meter en una lista los números y las operaciones, además
//de ignorar los operadores duplicados.
fun parse(expression: String): MutableList<Token>{
    val terms = mutableListOf<Token>()
    val length = expression.length
    var i = 0
    while(i < length){
        val char = expression.get(i).toChar()
        if(char.isDigit()){
            var num = 0
            //Si delante del número se cierra un paréntesis, añadimos un signo de multiplicación
            if(i > 1){
                if((expression.get(i-1) == ')')){
                    terms.add(Token('*', 0.0))
                }
            }
            //Parseamos el número aun Int //todo: support for decimal numbers
            for(j in i..length-1){
                if(expression.get(j).isDigit()){
                    num = num*10 + expression.get(j).digitToInt()
                    i++
                }else{
                    break;
                }
            }
            val token = Token('n', num.toDouble())
            terms.add(token)
        }else{
            if(isOp(char)){
                for(j in i..length-2){
                    var nextChar = expression.get(j + 1).toChar()
                    if(!isOp(nextChar)){
                        val token = Token(expression.get(i).toChar(), 0.0)
                        terms.add(token)
                        break;
                    } else{
                        i++
                    }
                }
            }else {
                if(char == '(') {
                    if(isOp(expression.get(i-1))){
                        terms.add(Token(char, 0.0))
                    }
                }else if(char == ')'){
                    terms.add(Token(char, 0.0))
                }
            }
            i++
        }
    }
    return terms
}

//Evalua una expresión en postfix a un doble
fun calcPostfix(list: List<Token>): Double{

    var stack: Stack<Token> = Stack<Token>()

    for(token in list){
        Log.i("myTag", "Stack" + stack.toString())
        if(token.type == 'n'){
            stack.push(token)
        }else{
            var n1: Double
            var n2: Double
            //el stack puede estar vacio si hay un parátensis sin cerrar
            //todo: quitar los ifs estos
            if(stack.isEmpty()){
                n1 = 0.0
            }else{
                n1 = stack.pop().value
            }
            if(stack.isEmpty()){
                n2 = 0.0
            }else{
                n2 = stack.pop().value
            }

            if(token.type == '+'){
                stack.push(Token('n', n2+n1))
            }else if(token.type == '-'){
                stack.push(Token('n', n2-n1))
            }else if(token.type == '/'){
                stack.push(Token('n', n2/n1)) //C O N T R O L A R N 2 = 0
            }else if(token.type == '*'){
                stack.push(Token('n', n2*n1))
            }
        }
    }
    if(stack.isEmpty()) return 0.0
    return stack.pop().value //retornamos el item 0 puesto que si la expresión termina en operador, devolverá el operador
}

//Utilizando el algoritmo de Shunting yard, pasamos la expresión introducida a Postfixs
fun infixToPostfix(list: MutableList<Token>): List<Token>{
    var queue: Queue<Token> = LinkedList<Token>()
    var stack: Stack<Token> = Stack<Token>()

    for(token in list){
        when(token.type){
            'n' -> queue.add(token)
            '(' -> stack.push(token)
            ')' -> {
                while(!stack.isEmpty() && stack.peek().type != '(') { // pop and enqueue all tokens until an opening parenthesis or an empty stack
                    queue.add(stack.pop())
                }
                if(!stack.isEmpty() && stack.peek().type == '(') { // pop and discard the opening parenthesis
                    stack.pop()
                }
            }
            else -> {
                while(!stack.isEmpty() && getPrecedence(stack.peek()) >= getPrecedence(token)) { // pop and enqueue all tokens with higher or equal precedence
                    queue.add(stack.pop())
                }
                stack.push(token) // push the current token to the stack
            }
        }

    }
    //Volcamos todo el stack en la pila
    while(!stack.isEmpty()) {
        queue.add(stack.pop())
    }
    Log.i("myTag", "Queue" + queue.toString())
    return queue.toList()
}

private fun isOp(operator: Char): Boolean{
    if((operator == '+') or (operator == '-') or (operator == '/') or (operator == '*')) return true
    return false
}

private fun isOp(token: Token): Boolean{
    if((token.type == '+') or (token.type == '-') or (token.type == '/') or (token.type == '*')) return true
    return false
}

private fun getPrecedence(token: Token): Int{
    if((token.type == '+') or (token.type == '-')) return 1
    if((token.type == '*') or (token.type == '/')) return 2
    return 0
}

@Composable
fun OperationButton(operation: String, onKeyPressed: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxSize(),
        enabled = true,
        onClick = { onKeyPressed() },
        border = BorderStroke(1.dp, Color.White),
        shape = RoundedCornerShape(10))
    {
        Text(text = operation,
            style = TextStyle(
                color = Color.White,
                fontSize = 25.sp)
        )

    }
}