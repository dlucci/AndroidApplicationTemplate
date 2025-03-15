package com.dlucci.baseapplication.di

import com.dlucci.baseapplication.BuildConfig
import com.dlucci.baseapplication.database.SampleDatabase
import com.dlucci.baseapplication.repository.SampleRepository
import com.dlucci.baseapplication.repository.SampleService
import com.dlucci.baseapplication.viewmodel.SampleViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val appModule = module {
    single { SampleRepository(get()) }
    single { SampleDatabase.getDatabase(androidContext()) }
    single {
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .build()
    }
    single { get<Retrofit>().create(SampleService::class.java) }

    viewModel { SampleViewModel(get(), get()) }

}