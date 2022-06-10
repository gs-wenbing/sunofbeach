package com.zwb.sob_home.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zwb.sob_home.bean.CategoryBean

@Database(version = 1, exportSchema = false, entities = [CategoryBean::class])
abstract class ChannelDatabase : RoomDatabase() {

    abstract fun getChannelDao() : ChannelDao


}