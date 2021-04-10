package ua.kh.em.rickmo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ua.kh.em.rickmo.data.network.ApiService
import ua.kh.em.rickmo.utils.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val baseUrl = Constants.BASE_URL

    private fun getRetrofitInstance(): Retrofit {
        val rxAdapter: RxJava2CallAdapterFactory =
            RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(rxAdapter)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService? {
        return getRetrofitInstance().create(ApiService::class.java)
    }
}