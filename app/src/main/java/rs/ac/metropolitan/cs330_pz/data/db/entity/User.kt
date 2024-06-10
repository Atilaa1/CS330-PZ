package rs.ac.metropolitan.cs330_pz.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val email: String?,
    val username: String?,
    val password: String?,
)
