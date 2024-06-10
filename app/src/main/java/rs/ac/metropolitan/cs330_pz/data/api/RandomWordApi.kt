package rs.ac.metropolitan.cs330_pz.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface RandomWordApi {
    @GET("word")
    suspend fun getWords(@Query("number") number: Int): List<String>
}