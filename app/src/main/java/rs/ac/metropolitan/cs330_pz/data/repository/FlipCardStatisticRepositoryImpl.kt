package rs.ac.metropolitan.cs330_pz.data.repository


import rs.ac.metropolitan.cs330_pz.data.db.dao.FlipCardStatisticDao
import rs.ac.metropolitan.cs330_pz.data.db.entity.FlipCardStatistic
import rs.ac.metropolitan.cs330_pz.domain.repository.FlipCardStatisticRepository

class FlipCardStatisticRepositoryImpl(private val statisticDao: FlipCardStatisticDao):FlipCardStatisticRepository {
   override suspend fun getStatisticsByUserId(userId: Int): List<FlipCardStatistic> {
        return statisticDao.getStatisticsByUserId(userId)
    }
    override suspend fun insertStatistic(statistic: FlipCardStatistic){
        statisticDao.insertStatistic(statistic)
    }
}
