package rs.ac.metropolitan.cs330_pz.domain.repository

import rs.ac.metropolitan.cs330_pz.data.db.entity.User

interface UserRepository {

    suspend fun insert(user: User): Long

    suspend fun getUserByUsernameAndPassword(username: String, password: String): User?

    fun getUserById(userId: Int): User?
}