package com.example.wowther.core.ext

import java.text.DecimalFormat

fun Double.decimalFormatToString(): Double {
    val numero = 21.333333333
    val df = DecimalFormat("#.####")
    return df.format(numero).toDouble()
}