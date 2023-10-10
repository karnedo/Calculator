package com.example.calculator

/*type values:
    'n' for numbers
    '+', '-', '*', '/' for operations
*/
data class Token(val type:Char, val value:Double){
    override fun toString(): String = type.toString() + value.toString()
}


