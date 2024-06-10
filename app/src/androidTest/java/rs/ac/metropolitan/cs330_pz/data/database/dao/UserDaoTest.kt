package rs.ac.metropolitan.cs330_pz.data.database.dao


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import kotlinx.coroutines.ExperimentalCoroutinesApi

import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.data.db.DatabaseDB
import rs.ac.metropolitan.cs330_pz.data.db.dao.UserDao


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)

class UserDaoTest {


    private lateinit var database: DatabaseDB
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DatabaseDB::class.java
        ).allowMainThreadQueries().build()
        userDao = database.userDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertUser_retrievesById() = runTest {
        val user = User(id = null, email = "test@example.com", username = "testuser", password = "password")
        val id = userDao.insert(user)
        val retrievedUser = userDao.getUserById(id.toInt())
        assertEquals(user.copy(id = id.toInt()), retrievedUser)
    }

    @Test
    fun updateUser_retrievesUpdatedUser() = runTest {
        val user = User(id = null, email = "test@example.com", username = "testuser", password = "password")
        val id = userDao.insert(user)
        val updatedUser = user.copy(id = id.toInt(), email = "newemail@example.com")
        userDao.update(updatedUser)
        val retrievedUser = userDao.getUserById(id.toInt())
        assertEquals(updatedUser, retrievedUser)
    }

    @Test
    fun getUserByUsernameAndPassword_returnsCorrectUser() = runTest {
        val user = User(id = null, email = "test@example.com", username = "testuser", password = "password")
        userDao.insert(user)
        val retrievedUser = userDao.getUserByUsernameAndPassword("testuser", "password")
        assertEquals(user.copy(id = retrievedUser?.id), retrievedUser)
    }

    @Test
    fun getUserByUsernameAndPassword_returnsNullForIncorrectCredentials() = runTest {
        val user = User(id = null, email = "test@example.com", username = "testuser", password = "password")
        userDao.insert(user)
        val retrievedUser = userDao.getUserByUsernameAndPassword("testuser", "wrongpassword")
        assertNull(retrievedUser)
    }
    @Test
    fun getUserById_returnsNullForNonExistentId() = runTest {
        val retrievedUser = userDao.getUserById(999)
        assertNull(retrievedUser)
    }
}
