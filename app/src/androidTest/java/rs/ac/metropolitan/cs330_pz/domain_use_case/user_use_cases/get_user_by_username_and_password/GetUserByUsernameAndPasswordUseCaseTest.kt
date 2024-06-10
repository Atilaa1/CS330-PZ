package rs.ac.metropolitan.cs330_pz.domain_use_case.user_use_cases.get_user_by_username_and_password


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.runner.RunWith
import rs.ac.metropolitan.cs330_pz.data.db.DatabaseDB
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.data.repository.UserRepositoryImpl
import rs.ac.metropolitan.cs330_pz.domain.repository.UserRepository
import rs.ac.metropolitan.cs330_pz.domain.use_case.user_use_case.get_user_by_username_and_password.GetUserByUsernameAndPasswordUseCase

@RunWith(AndroidJUnit4::class)
class GetUserByUsernameAndPasswordUseCaseTest {

    private lateinit var database: DatabaseDB
    private lateinit var userRepository: UserRepository
    private lateinit var getUserByUsernameAndPasswordUseCase: GetUserByUsernameAndPasswordUseCase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DatabaseDB::class.java
        ).allowMainThreadQueries().build()

        userRepository = UserRepositoryImpl(database.userDao())
        getUserByUsernameAndPasswordUseCase = GetUserByUsernameAndPasswordUseCase(userRepository)

        runBlocking {

            userRepository.insert(User(id = null, email = "user1@example.com", username = "user1", password = "password1"))
            userRepository.insert(User(id = null, email = "user2@example.com", username = "user2", password = "password2"))
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testGetUserByUsernameAndPassword_success() = runTest {
        val expectedUser = User(id = 1, email = "user1@example.com", username = "user1", password = "password1")

        val actualUser = getUserByUsernameAndPasswordUseCase("user1", "password1")

        assertEquals(expectedUser, actualUser)
    }

    @Test
    fun testGetUserByUsernameAndPassword_failure() = runTest {
        val actualUser = getUserByUsernameAndPasswordUseCase("user1", "wrongpassword")

        assertNull(actualUser)
    }
}