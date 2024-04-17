package com.devotion.makancuy.data.source.network.service


import com.devotion.makancuy.BuildConfig
import com.devotion.makancuy.data.source.network.model.category.CategoryResponse
import com.devotion.makancuy.data.source.network.model.checkout.CheckoutRequestPayload
import com.devotion.makancuy.data.source.network.model.checkout.CheckoutResponse
import com.devotion.makancuy.data.source.network.model.menu.MenuResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RestaurantApiService {

    @GET("category")
    suspend fun getCategories(): CategoryResponse

    @GET("listmenu")
    suspend fun getMenu(@Query("c") categoryName : String? = null): MenuResponse

    @GET("orders")
    suspend fun createOrder(@Body payload : CheckoutRequestPayload): CheckoutResponse

    companion object{
        @JvmStatic
        operator fun invoke(): RestaurantApiService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(RestaurantApiService::class.java)
        }
    }
}