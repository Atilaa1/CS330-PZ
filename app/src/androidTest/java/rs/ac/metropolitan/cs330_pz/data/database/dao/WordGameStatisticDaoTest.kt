package rs.ac.metropolitan.cs330_pz.data.database.dao


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.data.db.entity.WordGameStatistic
import rs.ac.metropolitan.cs330_pz.data.db.DatabaseDB
import rs.ac.metropolitan.cs330_pz.data.db.dao.UserDao
import rs.ac.metropolitan.cs330_pz.data.db.dao.WordGameStatisticDao


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class WordGameStatisticDaoTest {


    private lateinit var database: DatabaseDB
    private lateinit var wordGameStatisticDao: WordGameStatisticDao
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DatabaseDB::class.java
        ).allowMainThreadQueries().build()
        wordGameStatisticDao = database.wordGameStatisticDao()
        userDao = database.userDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertStatistic_retrievesByUserId() = runTest {
        val user = User(id = null, email = "test@example.com", username = "testuser", password = "password")
        val userId = userDao.insert(user).toInt()

        val statistic = WordGameStatistic(id = 0, userId = userId, numberOfGuessingWords = 10, score = 100)
        wordGameStatisticDao.insert(statistic)

        val statistics = wordGameStatisticDao.getStatisticsForUser(userId)
        assertEquals(listOf(statistic.copy(id = statistics.first().id)), statistics)
    }

    @Test
    fun insertMultipleStatistics_retrievesAllByUserId() = runTest {
        val user = User(id = null, email = "test@example.com", username = "testuser", password = "password")
        val userId = userDao.insert(user).toInt()

        val statistic1 = WordGameStatistic(id = 0, userId = userId, numberOfGuessingWords = 10, score = 100)
        val statistic2 = WordGameStatistic(id = 0, userId = userId, numberOfGuessingWords = 15, score = 150)
        wordGameStatisticDao.insert(statistic1)
        wordGameStatisticDao.insert(statistic2)

        val statistics = wordGameStatisticDao.getStatisticsForUser(userId)
        assertEquals(2, statistics.size)
        assertEquals(statistic1.copy(id = statistics[0].id), statistics[0])
        assertEquals(statistic2.copy(id = statistics[1].id), statistics[1])
    }

    @Test
    fun getStatisticsForUser_returnsEmptyListIfNoStatistics() = runTest {
        val user = User(id = null, email = "test@example.com", username = "testuser", password = "password")
        val userId = userDao.insert(user).toInt()

        val statistics = wordGameStatisticDao.getStatisticsForUser(userId)
        assertTrue(statistics.isEmpty())
    }
}
