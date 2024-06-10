package rs.ac.metropolitan.cs330_pz.domain_use_case.flipcard_use_cases.insert_statistic
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
import rs.ac.metropolitan.cs330_pz.data.db.entity.FlipCardStatistic
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.data.repository.FlipCardStatisticRepositoryImpl
import rs.ac.metropolitan.cs330_pz.data.repository.UserRepositoryImpl
import rs.ac.metropolitan.cs330_pz.domain.repository.FlipCardStatisticRepository
import rs.ac.metropolitan.cs330_pz.domain.use_case.flipcard_use_case.insert_statistic.InsertStatisticUseCase

@RunWith(AndroidJUnit4::class)
class InsertStatisticUseCaseTest {

    private lateinit var database: DatabaseDB
    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var statisticRepository: FlipCardStatisticRepository
    private lateinit var insertStatisticUseCase: InsertStatisticUseCase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DatabaseDB::class.java
        ).allowMainThreadQueries().build()

        userRepository = UserRepositoryImpl(database.userDao())
        statisticRepository = FlipCardStatisticRepositoryImpl(database.flipCardStatisticDao())
        insertStatisticUseCase = InsertStatisticUseCase(statisticRepository)

        runBlocking {
            // Insert a user into the User table
            userRepository.insert(User(id = 1, email = "user1@example.com", username = "user1", password = "password1"))
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertStatistic() = runTest {
        val statistic = FlipCardStatistic(id = 1, userId = 1, timeElapsed = 100, clickCount = 10)

        insertStatisticUseCase(statistic)

        val actualStatistic = statisticRepository.getStatisticsByUserId(1).firstOrNull()

        assertEquals(statistic, actualStatistic)
    }
}