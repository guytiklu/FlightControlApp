package com.example.myapplication

import androidx.room.*

@Dao
interface adressAndTimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUrl(adressAndTime: adressAndTime)

    @Query("DELETE FROM adresstable") //erase all
    suspend fun deleteAll();

    @Query("SELECT * FROM adresstable ORDER BY number ASC LIMIT 5") //takes the smallest 5
    suspend fun getAllUrls(): List<adressAndTime>

    //after every insertion, we must call this function in order keep LRU cache architecture
    @Query("UPDATE adresstable SET number = number + 1") /// increasing by 1 every element
    suspend fun updateNumbers()
}