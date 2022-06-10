package com.zwb.sob_home.db

import androidx.room.*
import com.zwb.sob_home.bean.CategoryBean

@Dao
interface ChannelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(teacher: List<CategoryBean>)

    @Insert
    suspend fun insertChannels(vararg cs: CategoryBean)

    @Query("select * from channel where is_my = :isMy")
    suspend fun getChannelsByFilter(isMy: Boolean): MutableList<CategoryBean>

    @Query("select * from channel")
    suspend fun getChannels(): List<CategoryBean>?

    @Update
    suspend fun updateChannels(cs: List<CategoryBean>)

    @Update
    suspend fun updateChannel(cs: CategoryBean)
}