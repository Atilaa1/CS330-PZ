package rs.ac.metropolitan.cs330_pz.data.db.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import rs.ac.metropolitan.cs330_pz.data.db.entity.WordGameStatistic

@Dao
interface WordGameStatisticDao {
    @Insert
    suspend fun insert(statistic: WordGameStatistic)

    @Query("SELECT * FROM word_game_statistics WHERE userId = :userId")
    suspend fun getStatisticsForUser(userId: Int): List<WordGameStatistic>
}