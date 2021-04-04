package ua.kh.em.rickmo.data.network

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ua.kh.em.rickmo.utils.Constants

object ApiClient {

    private const val BASE_URL = Constants.BASE_URL

    private fun getRetrofitInstance(): Retrofit {
        val rxAdapter: RxJava2CallAdapterFactory =
            RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(rxAdapter)
            .build()
    }

    fun getApiService(): ApiService? {
        return getRetrofitInstance().create(ApiService::class.java)
    }

}