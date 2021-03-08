package id.calocallo.sicape.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkConfig {

    fun getInterceptor() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return  okHttpClient
    }
    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://test.sipaminal-propam.com/api/")
//            .baseUrl("https://api.mocki.io/")
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getServPers() = getRetrofit().create(ApiPersonel::class.java)
    fun getServUser() = getRetrofit().create(ApiUser::class.java)
    fun getServLp() = getRetrofit().create(ApiLp::class.java)
    fun getServLhp() = getRetrofit().create(ApiLhp::class.java)
}