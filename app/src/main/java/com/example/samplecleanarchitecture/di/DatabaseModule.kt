package com.example.samplecleanarchitecture.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.dao.DepartmentDao
import com.example.data.local.database.DepartmentRoomDatabase
import com.example.samplecleanarchitecture.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): DepartmentRoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext, DepartmentRoomDatabase::class.java, Constants.DB_NAME
        ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    fun provideChatDao(database: DepartmentRoomDatabase): DepartmentDao {
        return database.dao()
    }

    @Provides
    fun provideContext(
        @ApplicationContext context: Context,
    ): Context {
        return context
    }
}