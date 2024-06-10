package rs.ac.metropolitan.cs330_pz.data.db.entity


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "word_game_statistics",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class WordGameStatistic(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val numberOfGuessingWords: Int,
    val score: Int
)
