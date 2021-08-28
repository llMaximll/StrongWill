package com.github.llmaximll.strongwill.di

import android.content.Context
import androidx.room.Room
import com.github.llmaximll.strongwill.model.local.AppDatabase
import com.github.llmaximll.strongwill.model.local.WillDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

	@Singleton
	@Provides
	fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
		return Room.databaseBuilder(
			appContext,
			AppDatabase::class.java,
			"StrongWillDB"
		).build()
	}

	@Singleton
	@Provides
	fun provideWillDao(appDatabase: AppDatabase): WillDao = appDatabase.willDao()
}