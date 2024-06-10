package rs.ac.metropolitan.cs330_pz.data.repository

import rs.ac.metropolitan.cs330_pz.data.api.RandomWordApi
import rs.ac.metropolitan.cs330_pz.domain.repository.WordRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordRepositoryImpl @Inject constructor(private val api: RandomWordApi): WordRepository {

    override suspend fun fetchWords(number: Int): List<String> {
        return api.getWords(number)
    }
}