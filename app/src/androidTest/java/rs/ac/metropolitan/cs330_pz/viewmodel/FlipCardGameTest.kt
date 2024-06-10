package rs.ac.metropolitan.cs330_pz.viewmodel

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import rs.ac.metropolitan.cs330_pz.R
import rs.ac.metropolitan.cs330_pz.data.db.DatabaseDB
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.data.repository.FlipCardStatisticRepositoryImpl
import rs.ac.metropolitan.cs330_pz.domain.use_case.flipcard_use_case.insert_statistic.InsertStatisticUseCase
import rs.ac.metropolitan.cs330_pz.presentation.flipcardgame.FlipCardGameViewModel

@ExperimentalCoroutinesApi
class FlipCardGameTest {

    private lateinit var database: DatabaseDB
    private lateinit var repository: FlipCardStatisticRepositoryImpl
    private lateinit var insertStatisticUseCase: InsertStatisticUseCase
    private lateinit var viewModel: FlipCardGameViewModel

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DatabaseDB::class.java
        ).allowMainThreadQueries().build()

        repository = FlipCardStatisticRepositoryImpl(database.flipCardStatisticDao())
        insertStatisticUseCase = InsertStatisticUseCase(repository)
        viewModel = FlipCardGameViewModel(insertStatisticUseCase)
    }

    @After
    fun tearDown() {
        database.close()
    }
    @Test
    fun startGame_initializesGame() = runTest {
        val user = User(id = 1, email = "test@example.com", username = "testuser", password = "password")
        viewModel.startGame(user)

        assertNotNull(viewModel.currentUser)
        assertEquals(user, viewModel.currentUser)
        assertTrue(viewModel.gameStarted)
        assertEquals(0, viewModel.score)
        assertEquals(0, viewModel.clickCount)
        assertEquals(0, viewModel.timeElapsed)
        assertFalse(viewModel.showDialog)
    }
    @Test
    fun flipCard_updatesState() = runTest {
        val user = User(id = 1, email = "test@example.com", username = "testuser", password = "password")
        viewModel.startGame(user)
        val index = 0

        viewModel.flipCard(index)

        assertTrue(viewModel.revealedCards.contains(index))
        assertEquals(1, viewModel.clickCount)
    }
    @Test
    fun flipCard_matchesCards() = runTest {
        val user = User(id = 1, email = "test@example.com", username = "testuser", password = "password")
        viewModel.startGame(user)


        viewModel.cardImages = listOf(
            R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4,
            R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4
        )

        val firstIndex = 0
        val secondIndex = 4

        viewModel.flipCard(firstIndex)
        viewModel.flipCard(secondIndex)

        // Debugging logs
        println("Matched cards: ${viewModel.matchedCards}")
        println("Revealed cards: ${viewModel.revealedCards}")
        println("Score: ${viewModel.score}")

        assertTrue("Expected matched cards to contain first index", viewModel.matchedCards.contains(firstIndex))
        assertTrue("Expected matched cards to contain second index", viewModel.matchedCards.contains(secondIndex))
        assertEquals(1, viewModel.score)
    }

    @Test
    fun flipCard_doesNotMatchCards() = runTest {
        val user = User(id = 1, email = "test@example.com", username = "testuser", password = "password")
        viewModel.startGame(user)

        // Ensure the cards at indices 0 and 1 do not have the same image
        viewModel.cardImages = listOf(
            R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4,
            R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4
        )

        val firstIndex = 0
        val secondIndex = 1

        viewModel.flipCard(firstIndex)
        viewModel.flipCard(secondIndex)

        // Debugging logs
        println("Matched cards: ${viewModel.matchedCards}")
        println("Revealed cards: ${viewModel.revealedCards}")
        println("Score: ${viewModel.score}")

        assertFalse("Expected matched cards not to contain first index", viewModel.matchedCards.contains(firstIndex))
        assertFalse("Expected matched cards not to contain second index", viewModel.matchedCards.contains(secondIndex))
        assertEquals(0, viewModel.score)
    }

}