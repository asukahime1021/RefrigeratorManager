package com.kobayashi.refrigeratormanager.configure

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kobayashi.refrigeratormanager.dao.DoorDao
import com.kobayashi.refrigeratormanager.dao.GenreMstDao
import com.kobayashi.refrigeratormanager.dao.ItemsDao
import com.kobayashi.refrigeratormanager.dao.RefrigeratorDao
import com.kobayashi.refrigeratormanager.entity.Door
import com.kobayashi.refrigeratormanager.entity.GenreMst
import com.kobayashi.refrigeratormanager.entity.Items
import com.kobayashi.refrigeratormanager.entity.Refrigerator
import java.security.AccessControlContext

@Database(
    entities = [Refrigerator::class,
                Door::class,
                Items::class,
                GenreMst::class],
    exportSchema = false,
    version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun refDao(): RefrigeratorDao

    abstract fun doorDao(): DoorDao

    abstract fun itemsDao(): ItemsDao

    abstract fun genreMstDao(): GenreMstDao

    companion object{
        private var DB_INSTANCE: AppDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): AppDatabase{
            synchronized(lock){
                if(DB_INSTANCE == null){
                    DB_INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,"refrigeratorManager.db")
                        .allowMainThreadQueries()
                        .build()
                }
                return DB_INSTANCE!!
            }
        }
    }
}