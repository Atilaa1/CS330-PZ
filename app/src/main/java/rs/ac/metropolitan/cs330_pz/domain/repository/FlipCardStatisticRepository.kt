package rs.ac.metropolitan.cs330_pz.domain.repository

import rs.ac.metropolitan.cs330_pz.data.db.entity.FlipCardStatistic

interface FlipCardStatisticRepository {
    suspend fun getStatisticsByUserId(userId: Int): List<FlipCardStatistic>
    suspend fun insertStatistic(statistic: FlipCardStatistic)
}