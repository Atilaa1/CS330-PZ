package rs.ac.metropolitan.cs330_pz.data.repository

import rs.ac.metropolitan.cs330_pz.data.db.dao.UserDao
import rs.ac.metropolitan.cs330_pz.data.db.entity.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insert(user: User): Long {
        return userDao.insert(user)
    }


    suspend fun getUserByUsernameAndPassword(username: String, password: String): User? {
        return userDao.getUserByUsernameAndPassword(username, password)
    }

    fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }
}