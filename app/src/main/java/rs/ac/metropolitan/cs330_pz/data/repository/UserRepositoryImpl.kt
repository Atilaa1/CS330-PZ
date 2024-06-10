package rs.ac.metropolitan.cs330_pz.data.repository

import rs.ac.metropolitan.cs330_pz.data.db.dao.UserDao
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.domain.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository{

  override  suspend fun insert(user: User): Long {
        return userDao.insert(user)
    }


    override suspend fun getUserByUsernameAndPassword(username: String, password: String): User? {
        return userDao.getUserByUsernameAndPassword(username, password)
    }

    override fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }
}