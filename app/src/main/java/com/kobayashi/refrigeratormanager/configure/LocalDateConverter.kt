package com.kobayashi.refrigeratormanager.configure

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import androidx.room.util.StringUtil
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

internal class LocalDateConverter {
    @TypeConverter
    fun fromDate(date: Date?): String{
        return date?.toString() ?: ""
    }

    @TypeConverter
    fun toLocalDate(stringDate: String): Date {
        var date = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN)
        if(stringDate.equals("")){
            return date
        }

        try{
            date = sdf.parse(stringDate)!!
        }catch (e: ParseException){
            e.printStackTrace()
        }

        return date
    }

}