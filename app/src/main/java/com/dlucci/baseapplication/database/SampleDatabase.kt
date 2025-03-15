package com.dlucci.baseapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.dlucci.baseapplication.model.SampleResponse


@Database(entities = [SampleResponse::class],
    version = 1,
    exportSchema = true)
abstract class SampleDatabase : RoomDatabase() {

    abstract fun sampleDao() : SampleDao

    companion object {

        @Volatile
        private var INSTANCE : SampleDatabase? = null

        fun getDatabase(context : Context) = INSTANCE ?: synchronized(this) {
            val instance = databaseBuilder(
                context.applicationContext,
                SampleDatabase::class.java,
                "sample_database"
            ).build()
            INSTANCE = instance
            instance
        }

    }

}