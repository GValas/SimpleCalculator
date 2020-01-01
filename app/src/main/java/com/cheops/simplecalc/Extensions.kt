package com.cheops.simplecalc

import android.content.Context


fun Context.toDps(x: Int): Int {
    val scale = this.resources.displayMetrics.density
    return (x * scale).toInt()
}

fun Int.dps(ctx: Context): Int {
    val scale = ctx.resources.displayMetrics.density
    val pixels = (this * scale).toInt()
    return pixels
}

fun Int.printString(): String = this.toString()

fun feature2() = 2
fun feature1() = 1
fun feature4() = 4
fun feature5() = 5
fun feature9() = 9