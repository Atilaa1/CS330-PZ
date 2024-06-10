package rs.ac.metropolitan.cs330_pz.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import rs.ac.metropolitan.cs330_pz.data.db.entity.FlipCardStatistic

@Dao
interface FlipCardStatisticDao {
    @Insert
    suspend fun insertStatistic(statistic: FlipCardStatistic)

    @Query("SELECT * FROM flip_card_statistics WHERE userId = :userId")
    suspend fun getStatisticsByUserId(userId: Int): List<FlipCardStatistic>
}
