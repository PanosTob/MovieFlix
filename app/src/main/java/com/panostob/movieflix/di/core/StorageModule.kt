package com.panostob.movieflix.di.core

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import com.panostob.movieflix.BuildConfig
import com.panostob.movieflix.framework.movies.datasource.local.AppDatabase
import com.panostob.movieflix.util.MOVIES_FLIX_PREFS
import com.panostob.movieflix.util.encryptor.Encryptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            MOVIES_FLIX_PREFS,
            Encryptor.masterKeyAlias(context),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "movieFlix.db")
            .apply {
                if (BuildConfig.DEBUG) {
                    setQueryCallback({ sqlQuery, bindArgs ->
                        Log.v(AppDatabase::class.java.simpleName, "SQL Query: $sqlQuery\nSQL Args: $bindArgs")
                    }, Executors.newSingleThreadExecutor())
                }
            }
            .build()
    }
}