package rs.ac.metropolitan.cs330_pz.domain_use_case.word_game_statistic_use_cases.insert

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
import rs.ac.metropolitan.cs330_pz.domain.use_case.word_game_statistic.insert.InsertWordGameStatisticUseCase

@RunWith(AndroidJUnit4::class)
class InsertWordGameStatisticUseCaseTest {

    private lateinit var database: DatabaseDB
    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var statisticRepository: WordGameStatisticRepository
    private lateinit var insertWordGameStatisticUseCase: InsertWordGameStatisticUseCase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DatabaseDB::class.java
        ).allowMainThreadQueries().build()

        userRepository = UserRepositoryImpl(database.userDao())
        statisticRepository = WordGameStatisticRepositoryImpl(database.wordGameStatisticDao())
        insertWordGameStatisticUseCase = InsertWordGameStatisticUseCase(statisticRepository)

        runBlocking {

            userRepository.insert(User(id = 1, email = "user1@example.com", username = "user1", password = "password1"))
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertWordGameStatistic() = runTest {
        val statistic = WordGameStatistic(id = 1, userId = 1, numberOfGuessingWords = 10, score = 50)


        insertWordGameStatisticUseCase(statistic)

        val actualStatistic = statisticRepository.getStatisticsForUser(1).firstOrNull()

        assertEquals(statistic, actualStatistic)
    }
}