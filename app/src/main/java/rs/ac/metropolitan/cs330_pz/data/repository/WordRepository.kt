package rs.ac.metropolitan.cs330_pz.data.repository

import rs.ac.metropolitan.cs330_pz.data.api.RandomWordApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordRepository @Inject constructor(private val api: RandomWordApi) {

    suspend fun fetchWords(number: Int): List<String> {
        return api.getWords(number)
    }
}