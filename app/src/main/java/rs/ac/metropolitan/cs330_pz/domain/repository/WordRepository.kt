package rs.ac.metropolitan.cs330_pz.domain.repository

interface WordRepository {
    suspend fun fetchWords(number: Int): List<String>
}