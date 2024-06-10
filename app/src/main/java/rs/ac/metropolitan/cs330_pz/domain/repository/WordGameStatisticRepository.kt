package rs.ac.metropolitan.cs330_pz.domain.repository

import rs.ac.metropolitan.cs330_pz.data.db.dao.WordGameStatisticDao
import rs.ac.metropolitan.cs330_pz.data.db.entity.WordGameStatistic

interface WordGameStatisticRepository{
    suspend fun insert(statistic: WordGameStatistic)

    suspend fun getStatisticsForUser(userId: Int): List<WordGameStatistic>
}