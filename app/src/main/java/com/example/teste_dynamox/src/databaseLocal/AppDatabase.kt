package com.example.teste_dynamox.src.databaseLocal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Users::class, jogosDosUsuaios::class], version = 6)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UsersDao
    abstract fun jogosDao(): JogosDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "DB"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
            }
            return INSTANCE!!
        }
    }
}