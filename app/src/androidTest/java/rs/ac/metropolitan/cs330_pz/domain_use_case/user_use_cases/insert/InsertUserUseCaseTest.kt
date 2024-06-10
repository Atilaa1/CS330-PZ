package rs.ac.metropolitan.cs330_pz.domain_use_case.user_use_cases.insert

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import rs.ac.metropolitan.cs330_pz.data.db.DatabaseDB
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.data.repository.UserRepositoryImpl
import rs.ac.metropolitan.cs330_pz.domain.repository.UserRepository
import rs.ac.metropolitan.cs330_pz.domain.use_case.user_use_case.insert.InsertUserUseCase

@RunWith(AndroidJUnit4::class)
class InsertUserUseCaseTest {

    private lateinit var database: DatabaseDB
    private lateinit var userRepository: UserRepository
    private lateinit var insertUserUseCase: InsertUserUseCase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DatabaseDB::class.java
        ).allowMainThreadQueries().build()

        userRepository = UserRepositoryImpl(database.userDao())
        insertUserUseCase = InsertUserUseCase(userRepository)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertUser() = runTest {
        val user = User(id = null, email = "newuser@example.com", username = "newuser", password = "password")

        val userId = insertUserUseCase(user)

        assertTrue(userId > 0)
    }
}