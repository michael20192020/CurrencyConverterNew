package com.hansoft.currencyconverter




import android.net.Uri
import android.util.Base64
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.net.MalformedURLException
import java.net.SocketTimeoutException
import java.net.URL
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
/**
 * created by qi zhu 10/12/2021
 * Create NetworkService class to deal with username,password,url
 * need import okhttp3
 */
enum class NetworkService : NetworkInterface {
    INSTANCE;

    /**
     * Method to build and return an OkHttpClient so we can set/get
     * headers quickly and efficiently.
     * @return OkHttpClient
     */
    private fun buildClient(): OkHttpClient? {
        if (okHttpClient != null) return okHttpClient
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)

        // Logging interceptor
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)

        // custom interceptor for adding header and NetworkMonitor sliding window
        okHttpClientBuilder.addInterceptor { chain -> // Add whatever we want to our request headers.
            val request =
                chain.request().newBuilder().addHeader("Accept", "application/json").build()
            val response: Response
            response = try {
                chain.proceed(request)
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                throw IOException(e)
            } catch (e: UnknownHostException) {
                e.printStackTrace()
                throw IOException(e)
            }
            response
        }
        return okHttpClientBuilder.build()
    }

    private fun buildRequest(url: URL?): Request.Builder {
        return Request.Builder()
            .url(url)
    }

    private fun buildRequest(url: URL?, credential: String): Request.Builder {
        return buildRequest(url).header("Authorization", credential)
    }

    private fun buildURL(builtUrl: Uri?): URL? {
        return if (builtUrl == null) null else try {
            val urlStr = builtUrl.toString()
            URL(urlStr)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            null
        }
    }

    private fun buildURL(url: String?): URL? {
        return try {
            URL(url)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            null
        }
    }

    private fun getData(request: Request): String? {
        val client = buildClient()
        return try {
            val response = client!!.newCall(request).execute()
            response.body().string()
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    override fun getString(endpoint: String?, username: String?, password: String?): String? {
        Log.d("NetworkService", "getString by username and password from $endpoint")
        val credentials = "$username:$password"
        val basicAuth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        val request = buildRequest(buildURL(endpoint), basicAuth).build()
        return getData(request)
    }

    override fun getString(endpoint: String?, token: String?): String? {
        Log.d("NetworkService", "getString by Bearer token from $endpoint")
        val credentials = "Bearer $token"
        val request = buildRequest(buildURL(endpoint), credentials).build()
        return getData(request)
    }

    override fun getString(url: String?): String? {
        val request = buildRequest(buildURL(url)).build()
        return getData(request)
    }

    companion object {
        private const val CONNECT_TIMEOUT: Long = 20000 // 20 seconds
        private const val READ_TIMEOUT: Long = 20000 // 20 seconds
        private val okHttpClient: OkHttpClient? = null
    }
}