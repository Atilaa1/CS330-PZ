package rs.ac.metropolitan.cs330_pz.domain_use_case.word_use_case

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import rs.ac.metropolitan.cs330_pz.data.api.RandomWordApi
import rs.ac.metropolitan.cs330_pz.domain.use_case.word_use_case.fetch_use_case.FetchWordsUseCase

class FetchWordsUseCaseTest {

    private lateinit var fakeApi: FakeRandomWordApi
    private lateinit var fetchWordsUseCase: FetchWordsUseCase

    @Before
    fun setUp() {
        fakeApi = FakeRandomWordApi()
        fetchWordsUseCase = FetchWordsUseCase(fakeApi)
    }

    @Test
    fun testFetchWords() = runTest {
        val number = 3
        val expectedWords = listOf("apple", "banana", "cherry")

        val actualWords = fetchWordsUseCase(number)

        assertEquals(expectedWords, actualWords)
    }


    class FakeRandomWordApi : RandomWordApi {
        override suspend fun getWords(number: Int): List<String> {
            return listOf("apple", "banana", "cherry").take(number)
        }
    }
}