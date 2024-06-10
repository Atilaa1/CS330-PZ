package rs.ac.metropolitan.cs330_pz.domain_use_case.flipcard_use_cases.get_statistic_by_user_id
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import rs.ac.metropolitan.cs330_pz.data.db.DatabaseDB
import rs.ac.metropolitan.cs330_pz.data.db.entity.FlipCardStatistic
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.data.repository.FlipCardStatisticRepositoryImpl
import rs.ac.metropolitan.cs330_pz.data.repository.UserRepositoryImpl
import rs.ac.metropolitan.cs330_pz.domain.repository.FlipCardStatisticRepository
import rs.ac.metropolitan.cs330_pz.domain.use_case.flipcard_use_case.get_statistic_by_user_id.GetStatisticByUserIdUseCase

@RunWith(AndroidJUnit4::class)
class GetStatisticByUserIdUseCaseTest {

    private lateinit var database: DatabaseDB
    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var statisticRepository: FlipCardStatisticRepository
    private lateinit var getStatisticByUserIdUseCase: GetStatisticByUserIdUseCase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DatabaseDB::class.java
        ).allowMainThreadQueries().build()

        userRepository = UserRepositoryImpl(database.userDao())
        statisticRepository = FlipCardStatisticRepositoryImpl(database.flipCardStatisticDao())
        getStatisticByUserIdUseCase = GetStatisticByUserIdUseCase(statisticRepository)

        runBlocking {

            userRepository.insert(User(id = 1, email = "user1@example.com", username = "user1", password = "password1"))
            userRepository.insert(User(id = 2, email = "user2@example.com", username = "user2", password = "password2"))

            statisticRepository.insertStatistic(
                FlipCardStatistic(id = 1, userId = 1, timeElapsed = 100, clickCount = 10)
            )
            statisticRepository.insertStatistic(
                FlipCardStatistic(id = 2, userId = 1, timeElapsed = 200, clickCount = 20)
            )
            statisticRepository.insertStatistic(
                FlipCardStatistic(id = 3, userId = 2, timeElapsed = 150, clickCount = 15)
            )
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testGetStatisticsByUserId() = runTest {
        val expectedListSize = 2
        val expectedFirst = FlipCardStatistic(id = 1, userId = 1, timeElapsed = 100, clickCount = 10)

        val actualListResult = getStatisticByUserIdUseCase(1)

        assertEquals(expectedListSize, actualListResult.size)
        assertEquals(expectedFirst, actualListResult[0])
    }
}