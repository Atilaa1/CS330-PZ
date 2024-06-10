package rs.ac.metropolitan.cs330_pz.domain.use_case.word_use_case.fetch_use_case

import rs.ac.metropolitan.cs330_pz.data.api.RandomWordApi
import javax.inject.Inject

class FetchWordsUseCase @Inject constructor(
    private val api: RandomWordApi
) {
    suspend operator fun invoke(number: Int): List<String> {
        return api.getWords(number)
    }
}