package rs.ac.metropolitan.cs330_pz.domain_use_case.word_game_statistic_use_cases.get_statistic_for_user


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import rs.ac.metropolitan.cs330_pz.data.db.DatabaseDB
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.data.db.entity.WordGameStatistic
import rs.ac.metropolitan.cs330_pz.data.repository.UserRepositoryImpl
import rs.ac.metropolitan.cs330_pz.data.repository.WordGameStatisticRepositoryImpl
import rs.ac.metropolitan.cs330_pz.domain.repository.WordGameStatisticRepository
import rs.ac.metropolitan.cs330_pz.domain.use_case.word_game_statistic.get_statistics_for_user.GetStatisticsForUserUseCase

@RunWith(AndroidJUnit4::class)
class GetStatisticsForUserUseCaseTest {

    private lateinit var database: DatabaseDB
    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var statisticRepository: WordGameStatisticRepository
    private lateinit var getStatisticsForUserUseCase: GetStatisticsForUserUseCase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DatabaseDB::class.java
        ).allowMainThreadQueries().build()

        userRepository = UserRepositoryImpl(database.userDao())
        statisticRepository = WordGameStatisticRepositoryImpl(database.wordGameStatisticDao())
        getStatisticsForUserUseCase = GetStatisticsForUserUseCase(statisticRepository)

        runBlocking {

            userRepository.insert(User(id = 1, email = "user1@example.com", username = "user1", password = "password1"))
            userRepository.insert(User(id = 2, email = "user2@example.com", username = "user2", password = "password2"))

            statisticRepository.insert(WordGameStatistic(id = 1, userId = 1, numberOfGuessingWords = 10, score = 50))
            statisticRepository.insert(WordGameStatistic(id = 2, userId = 1, numberOfGuessingWords = 20, score = 100))
            statisticRepository.insert(WordGameStatistic(id = 3, userId = 2, numberOfGuessingWords = 15, score = 75))
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testGetStatisticsForUser() = runTest {
        val expectedListSize = 2
        val expectedFirst = WordGameStatistic(id = 1, userId = 1, numberOfGuessingWords = 10, score = 50)

        val actualListResult = getStatisticsForUserUseCase(1)

        assertEquals(expectedListSize, actualListResult.size)
        assertEquals(expectedFirst, actualListResult[0])
    }
}