package com.demo.controlr.framework.network.retrofit
/***
 * @pdypham
 * */
import com.demo.controlr.framework.database.PreferenceManager
import com.demo.controlr.framework.network.ConnectActivity
import com.demo.controlr.utils.Contains
import com.google.gson.GsonBuilder
import com.minhhop.easygolf.framework.network.retrofit.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.BuildConfig
import java.util.concurrent.TimeUnit

class ProviderRetrofit(preferenceManager: PreferenceManager, connectActivity: ConnectActivity) {
    companion object{
        private val BASE_URL = "https://api.twitter.com/"
        private const val TIME_OUT = 60L
    }

    var mRetrofit:Retrofit

    init {
        /**
         * show logging retrofit
         * */
        val interceptor = HttpLoggingInterceptor()
        if(BuildConfig.DEBUG){
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }else{
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val gson = GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setPrettyPrinting()
                .create()


        val clientWithRetrofit = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(HeaderInterceptor(preferenceManager))
                .connectTimeout(TIME_OUT,TimeUnit.SECONDS)
                .readTimeout(TIME_OUT,TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT,TimeUnit.SECONDS)
                .build()

        mRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientWithRetrofit)
                .addCallAdapterFactory(CoroutineErrorCallAdapterFactory.create(connectActivity))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

    }

    inline fun <reified T> createNetworkService():T = mRetrofit.create(T::class.java)
}