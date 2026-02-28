package com.darkfetchvip.tr.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.darkfetchvip.tr.data.api.DarkFetchApi
import com.darkfetchvip.tr.data.local.DarkFetchDao
import com.darkfetchvip.tr.data.local.DarkFetchDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton
    fun provideApi(): DarkFetchApi {
        val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }).build()
        return Retrofit.Builder().baseUrl("https://example.com/api/").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build().create(DarkFetchApi::class.java)
    }

    @Provides @Singleton
    fun provideDb(@ApplicationContext context: Context): DarkFetchDb = Room.databaseBuilder(context, DarkFetchDb::class.java, "darkfetch.db").build()

    @Provides
    fun provideDao(db: DarkFetchDb): DarkFetchDao = db.dao()

    @Provides @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager = WorkManager.getInstance(context)
}
