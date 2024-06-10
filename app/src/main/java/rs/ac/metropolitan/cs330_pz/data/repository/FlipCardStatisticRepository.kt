package rs.ac.metropolitan.cs330_pz.data.repository


import rs.ac.metropolitan.cs330_pz.data.db.dao.FlipCardStatisticDao
import rs.ac.metropolitan.cs330_pz.data.db.entity.FlipCardStatistic

class FlipCardStatisticRepository(private val statisticDao: FlipCardStatisticDao) {
    suspend fun getStatisticsByUserId(userId: Int): List<FlipCardStatistic> {
        return statisticDao.getStatisticsByUserId(userId)
    }
    suspend fun insertStatistic(statistic: FlipCardStatistic){
        statisticDao.insertStatistic(statistic)
    }
}
