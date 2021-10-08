package com.example.weatherlyapp.di

import com.example.weatherlyapp.repository.WeatherRepository
import com.example.weatherlyapp.repository.WeatherRepositoryImpl
import com.example.weatherlyapp.repository.WeatherService
import com.example.weatherlyapp.repository.services.ServerErrorInterceptor
import com.example.weatherlyapp.repository.services.WebServiceAppIdInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.HttpUrl
import okhttp3.Request


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherService(@OtherInterceptorOkHttpClient okHttpClient: OkHttpClient,
                              @ErrorInterceptorOkHttpClient errorOkHttpClient: OkHttpClient): WeatherService =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            //.client(errorOkHttpClient)
            .client(okHttpClient)
            //.addCallAdapterFactory()
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)

    @Provides
    @OtherInterceptorOkHttpClient
    fun provideAppIdInterceptorOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor {chain ->
                var request: Request = chain.request()
                val url: HttpUrl =
                    request.url.newBuilder().addQueryParameter("appid", "6e76055f1d172fe00a7aa9edd1f0ebf5").build()
                request = request.newBuilder().url(url).build()
                 chain.proceed(request)
            }
            .build()
    }

    @Provides
    @ErrorInterceptorOkHttpClient
    fun provideServerErrorInterceptor(serverErrorInterceptor: ServerErrorInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addNetworkInterceptor(serverErrorInterceptor)
            .build()
    }

}