package nl.hva.frontend.common.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VocabilityRetrofitAPI {
    companion object {
        // base url of the API
        private const val baseUrl = "http://10.0.2.2:8081"

        // the lazy keyword makes sure the createApi function is not called until these properties are accessed
        val apiService by lazy { createApi() }

        /**
         * @return [VocabilityAPIService] The service class of the retrofit client.
         */
        private fun createApi(): VocabilityAPIService {
            // create an OkHttpClient to be able to make a log of the network traffic
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            // create the retrofit instance
            val patientApi = Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()

            // return the retrofit PatientApiService
            return patientApi.create(VocabilityAPIService::class.java)
        }
    }
}