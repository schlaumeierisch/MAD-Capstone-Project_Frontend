package nl.hva.frontend.common.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.hva.frontend.common.api.VocabilityAPIService
import nl.hva.frontend.common.api.VocabilityRetrofitAPI
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideVocabilityAPI(): VocabilityAPIService {
        return VocabilityRetrofitAPI.apiService
    }

}