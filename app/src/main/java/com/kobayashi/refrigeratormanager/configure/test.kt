package com.kobayashi.refrigeratormanager.configure

import java.lang.Exception

inline fun <T> checkAndNotNull(p1: T?, func: () -> T): T = p1 ?: func()

fun appendDateText(year: String, month: String, day: String): String{
    val monthInt = month.toInt()
    val dayInt = day.toInt()
    val format = "%02d"

    return year
        .plus("-")
        .plus(format.format(monthInt))
        .plus("-")
        .plus(format.format(dayInt))
}

fun main(args: Array<String>){
    val str1 = "2020".plus("-").plus("%02d".format(10))
    try{
        val str2 = str1.plus("%2d".format("07".toInt()))
        print(str2)
    }catch (e: Exception){
        print(e)
    }

    print(str1)
}