package com.example.budget_management_system

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

// Объект для создания Retrofit клиента
object RetrofitClient {

    // Базовый URL API. Убедитесь, что это правильный IP и порт.
    private const val BASE_URL = "https://192.168.88.18:7051/"

    // Логгер для отладки запросов
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Создайте небезопасный TrustManager для игнорирования самоподписанных сертификатов
    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    })

    // Создайте небезопасный SSLContext
    private val sslContext = SSLContext.getInstance("SSL").apply {
        init(null, trustAllCerts, java.security.SecureRandom())
    }

    private val sslSocketFactory = sslContext.socketFactory

    // CookieManager для сохранения и отправки httpOnly cookies
    private val cookieManager = CookieManager()
    private val cookieJar: CookieJar = JavaNetCookieJar(cookieManager)

    // HTTP клиент с логгером, поддержкой кук и небезопасным SSL
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor) // Логирование запросов
        .cookieJar(cookieJar) // Поддержка httpOnly cookies
        .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier { _, _ -> true } // Игнорируем проверку hostname
        .build()

    // Настройка Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create()) // Преобразование JSON в объекты
        .build()

    // Экземпляр ApiService
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}