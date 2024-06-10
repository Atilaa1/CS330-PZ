package rs.ac.metropolitan.cs330_pz.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "flip_card_statistics",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class FlipCardStatistic(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val userId: Int,
    val timeElapsed: Int,
    val clickCount: Int
)
