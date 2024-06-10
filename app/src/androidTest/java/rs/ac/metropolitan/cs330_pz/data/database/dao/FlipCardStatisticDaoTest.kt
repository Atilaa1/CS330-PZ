package rs.ac.metropolitan.cs330_pz.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import rs.ac.metropolitan.cs330_pz.data.db.entity.FlipCardStatistic
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.data.db.DatabaseDB
import rs.ac.metropolitan.cs330_pz.data.db.dao.FlipCardStatisticDao
import rs.ac.metropolitan.cs330_pz.data.db.dao.UserDao


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)

class FlipCardStatisticDaoTest {


    private lateinit var database: DatabaseDB
    private lateinit var flipCardStatisticDao: FlipCardStatisticDao
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DatabaseDB::class.java
        ).allowMainThreadQueries().build()
        flipCardStatisticDao = database.flipCardStatisticDao()
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

        val statistic = FlipCardStatistic(id = null, userId = userId, timeElapsed = 120, clickCount = 30)
        flipCardStatisticDao.insertStatistic(statistic)

        val statistics = flipCardStatisticDao.getStatisticsByUserId(userId)
        assertEquals(listOf(statistic.copy(id = statistics.first().id)), statistics)
    }

    @Test
    fun insertMultipleStatistics_retrievesAllByUserId() = runTest {
        val user = User(id = null, email = "test@example.com", username = "testuser", password = "password")
        val userId = userDao.insert(user).toInt()

        val statistic1 = FlipCardStatistic(id = null, userId = userId, timeElapsed = 120, clickCount = 30)
        val statistic2 = FlipCardStatistic(id = null, userId = userId, timeElapsed = 90, clickCount = 20)
        flipCardStatisticDao.insertStatistic(statistic1)
        flipCardStatisticDao.insertStatistic(statistic2)

        val statistics = flipCardStatisticDao.getStatisticsByUserId(userId)
        assertEquals(2, statistics.size)
        assertEquals(statistic1.copy(id = statistics[0].id), statistics[0])
        assertEquals(statistic2.copy(id = statistics[1].id), statistics[1])
    }
    @Test
    fun getStatisticsByUserId_returnsEmptyListIfNoStatistics() = runTest {
        val user = User(id = null, email = "test@example.com", username = "testuser", password = "password")
        val userId = userDao.insert(user).toInt()

        val statistics = flipCardStatisticDao.getStatisticsByUserId(userId)
        assertTrue(statistics.isEmpty())
    }
}
