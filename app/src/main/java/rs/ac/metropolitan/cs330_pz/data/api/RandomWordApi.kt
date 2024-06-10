package rs.ac.metropolitan.cs330_pz.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomWordApi {
    @GET("word")
    suspend fun getWords(@Query("number") number: Int): List<String>

    companion object {
        private const val BASE_URL = "https://random-word-api.herokuapp.com/"

        fun create(): RandomWordApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RandomWordApi::class.java)
        }
    }
}