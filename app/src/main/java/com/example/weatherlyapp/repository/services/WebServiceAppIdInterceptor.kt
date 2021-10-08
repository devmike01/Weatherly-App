package com.example.weatherlyapp.repository.services

import android.util.Log
import com.example.weatherlyapp.BuildConfig
import okhttp3.*
import okhttp3.internal.concurrent.TaskRunner.Companion.logger
import java.lang.String
import retrofit2.HttpException
import javax.inject.Inject
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject


class WebServiceAppIdInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()



        val url = request.url.newBuilder()
            .addQueryParameter("appid", "6e76055f1d172fe00a7aa9edd1f0ebf5").build()

        request.newBuilder().url(url).build()

        if (BuildConfig.DEBUG){
            Log.d("intercept", "hello -> ${String.format(
                "Sending request %s",
                request.url
            )}")
        }

        return chain.proceed(request)

    }

}