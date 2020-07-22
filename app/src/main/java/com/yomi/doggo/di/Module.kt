package com.yomi.doggo.di

import com.google.gson.GsonBuilder
import com.yomi.doggo.data.BreedRepository
import com.yomi.doggo.data.IRepository
import com.yomi.doggo.network.BreedService
import com.yomi.doggo.ui.breeds.BreedsUseCase
import com.yomi.doggo.ui.breeds.BreedsViewModel
import com.yomi.doggo.ui.home.HomeViewModel
import com.yomi.doggo.ui.home.BreedQuestionUseCase
import com.yomi.doggo.util.Endpoints
import com.yomi.doggo.util.TIME_OUT
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Yomi Joseph on 2020-07-20.
 */

val repositoryModule = module {
    factory<IRepository> { BreedRepository(get()) }
}

val useCaseModule = module {
    factory { BreedQuestionUseCase(get()) }
    factory { BreedsUseCase(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { BreedsViewModel(get()) }
}

val networkModule = module {
    single {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(androidContext().cacheDir, cacheSize)

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .cache(myCache)
            .addInterceptor{ chain ->
                val request = chain.request()
                //add default caching. Endpoint that should not be cached should be annotated with @Headers
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(Endpoints.BASE_URL).build()
    }

    factory { get<Retrofit>().create(BreedService::class.java) }
}