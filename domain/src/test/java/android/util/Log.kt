@file:JvmName("Log")

package android.util

//This file and methods are meant to be called when executing tests, so that they replace
//any calls to the log in the code.

fun e(tag: String, msg: String, t: Throwable): Int {
    println("ERROR: $tag: $msg")
    println("THROWABLE: $t.toString()")
    return 0
}

fun i(tag: String, msg: String): Int {
    println("INFO: $tag: $msg")
    return 0
}

fun w(tag: String, msg: String): Int {
    println("WARN: $tag: $msg")
    return 0
}

// add other functions if required...
