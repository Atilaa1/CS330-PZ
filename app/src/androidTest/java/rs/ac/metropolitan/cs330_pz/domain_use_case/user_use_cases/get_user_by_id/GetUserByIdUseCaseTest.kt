package rs.ac.metropolitan.cs330_pz.domain_use_case.user_use_cases.get_user_by_id

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
import rs.ac.metropolitan.cs330_pz.data.repository.UserRepositoryImpl
import rs.ac.metropolitan.cs330_pz.domain.repository.UserRepository
import rs.ac.metropolitan.cs330_pz.domain.use_case.user_use_case.get_user_by_id.GetUserByIdUseCase

@RunWith(AndroidJUnit4::class)
class GetUserByIdUseCaseTest {

    private lateinit var database: DatabaseDB
    private lateinit var userRepository: UserRepository
    private lateinit var getUserByIdUseCase: GetUserByIdUseCase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DatabaseDB::class.java
        ).allowMainThreadQueries().build()

        userRepository = UserRepositoryImpl(database.userDao())
        getUserByIdUseCase = GetUserByIdUseCase(userRepository)

        runBlocking {

            userRepository.insert(User(id = 1, email = "user1@example.com", username = "user1", password = "password1"))
            userRepository.insert(User(id = 2, email = "user2@example.com", username = "user2", password = "password2"))
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testGetUserById() = runTest {
        val expectedUser = User(id = 1, email = "user1@example.com", username = "user1", password = "password1")

        val actualUser = getUserByIdUseCase(1)

        assertEquals(expectedUser, actualUser)
    }
}