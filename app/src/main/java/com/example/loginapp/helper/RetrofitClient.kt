package com.appvantis.perkserp.helper

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitClient {
    lateinit var context: Context
    private var mRetrofit: Retrofit? = null
    private var mRetrofitInstance: Retrofit? = null
    public val okHttpClient: OkHttpClient
        get() {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)

            val trustAllCerts: Array<TrustManager> = arrayOf(
                    object : X509TrustManager {
                        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

                        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return emptyArray()
                        }


                    }
            )
// Install the all-trusting trust manager
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
// Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

            httpClient.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            // httpClient.sslSocketFactory(getSslContextForCertificateFile(this, "my_certificate.pem").socketFactory)
            return httpClient.addInterceptor(logging).build()
        }

    private val gson = GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create()

    fun getClient(base_url: String): Retrofit? {
        mRetrofit = Retrofit.Builder()
                .baseUrl(base_url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return mRetrofit
    }

    fun getOTPClient(base_url: String): Retrofit? {
        mRetrofit = Retrofit.Builder()
                .baseUrl(base_url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return mRetrofit
    }

    fun getMapClient(base_url: String?): Retrofit? {
        if (mRetrofit == null) {
            mRetrofit = Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
        return mRetrofit
    }

    fun getmRetrofit(base_url: String?): Retrofit? {
        if (mRetrofitInstance == null) {
            mRetrofitInstance = Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
        return mRetrofitInstance
    }

    // for SMS
    fun getSMSClient(base_url: String?): Retrofit? {
        if (mRetrofit == null) {
            mRetrofit = Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
        return mRetrofit
    }

    // for SMS
    fun getImageClient(base_url: String?): Retrofit? {
        if (mRetrofit != null) {
            mRetrofit = Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
        return mRetrofit
    }
}