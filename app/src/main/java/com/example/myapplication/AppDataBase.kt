package com.example.myapplication

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [adressAndTime::class], version = 1
)
abstract class adressAndTimeDatabase : RoomDatabase() {
    abstract fun getAdressAndTimeDao(): adressAndTimeDao
}