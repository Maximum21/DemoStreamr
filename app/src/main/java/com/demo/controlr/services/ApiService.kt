package com.demo.controlr.services

import com.demo.controlr.framework.GeneralService
import com.demo.controlr.services.retorfit.RxErrorHandlingCallAdapterFactory
import com.demo.controlr.utils.Contains
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.BuildConfig
import java.util.concurrent.TimeUnit

class ApiService private constructor() {


    private val retrofitWithRetry: Retrofit


    val generalService: GeneralService
        get() = retrofitWithRetry.create(GeneralService::class.java)


    init {

        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val clientWithRetry = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(object : Interceptor{
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val token = PreferenceService.getInstance().getToken()

                        val builder = chain.request().newBuilder()
//                        if (token != null) {
//                            builder.addHeader("Authorization", "Bearer $token")
//                        }

                        return chain.proceed(builder.build())
                    }

                })
                .build()

        retrofitWithRetry = Retrofit.Builder()
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(
                        GsonBuilder()
                                .enableComplexMapKeySerialization()
                                .serializeNulls()
                                .setPrettyPrinting()
                                .create())
                )
                .client(clientWithRetry)
                .baseUrl(urlApi)
                .build()
    }

    companion object {

        private var instance: ApiService? = null

        private val urlApi: String
            get() = Contains.getUrlApi()

        fun getInstance(): ApiService {
            if (instance == null) {
                instance = ApiService()
            }
            return ApiService()
        }
    }
}
