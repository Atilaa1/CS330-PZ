package rs.ac.metropolitan.cs330_pz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rs.ac.metropolitan.cs330_pz.data.db.DatabaseDB
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.data.repository.WordGameStatisticRepository
import rs.ac.metropolitan.cs330_pz.data.repository.FlipCardStatisticRepository
import rs.ac.metropolitan.cs330_pz.data.repository.UserRepository
import rs.ac.metropolitan.cs330_pz.presentation.flipCardStatistic.FlipCardStatisticViewModelFactory
import rs.ac.metropolitan.cs330_pz.presentation.flipcardgame.FlipCardGameViewModel
import rs.ac.metropolitan.cs330_pz.presentation.flipcardgame.FlipCardGameViewModelFactory

import rs.ac.metropolitan.cs330_pz.presentation.flipcardgame.components.FlipCardGameScreen
import rs.ac.metropolitan.cs330_pz.presentation.home.components.HomeScreen
import rs.ac.metropolitan.cs330_pz.presentation.login.UserViewModel
import rs.ac.metropolitan.cs330_pz.presentation.login.UserViewModelFactory
import rs.ac.metropolitan.cs330_pz.presentation.statistics.FlipCardStatisticScreen
import rs.ac.metropolitan.cs330_pz.presentation.statistics.FlipCardStatisticViewModel
import rs.ac.metropolitan.cs330_pz.presentation.statistics.components.StatisticsScreen
import rs.ac.metropolitan.cs330_pz.presentation.wordGameStatistic.WordGameStatisticViewModel
import rs.ac.metropolitan.cs330_pz.presentation.wordGameStatistic.WordGameStatisticViewModelFactory
import rs.ac.metropolitan.cs330_pz.presentation.wordGameStatistic.components.WordGameStatisticScreen
import rs.ac.metropolitan.cs330_pz.presentation.wordgame.WordGameViewModel
import rs.ac.metropolitan.cs330_pz.presentation.wordgame.WordGameViewModelFactory
import rs.ac.metropolitan.cs330_pz.presentation.wordgame.components.WordGameScreen

import rs.ac.metropolitan.cs330_pz.ui.screen.LoginScreen
import rs.ac.metropolitan.cs330_pz.ui.screen.RegisterScreen
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = DatabaseDB.getDatabase(this)
        val userRepository = UserRepository(database.userDao())
        val flipCardStatisticRepository = FlipCardStatisticRepository(database.flipCardStatisticDao())
        val wordGameStatisticRepository = WordGameStatisticRepository(database.wordGameStatisticDao())

        val userViewModelFactory = UserViewModelFactory(userRepository)
        val userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)

        val flipCardStatisticViewModelFactory = FlipCardStatisticViewModelFactory(flipCardStatisticRepository)
        val flipCardStatisticViewModel = ViewModelProvider(this, flipCardStatisticViewModelFactory).get(FlipCardStatisticViewModel::class.java)

        val flipCardGameViewModelFactory = FlipCardGameViewModelFactory(flipCardStatisticRepository)
        val wordGameStatisticViewModelFactory = WordGameStatisticViewModelFactory(wordGameStatisticRepository)
        val wordGameStatisticViewModel = ViewModelProvider(this, wordGameStatisticViewModelFactory).get(WordGameStatisticViewModel::class.java)
        val wordGameViewModelFactory = WordGameViewModelFactory(wordGameStatisticRepository)
        val wordGameViewModel = ViewModelProvider(this, wordGameViewModelFactory).get(WordGameViewModel::class.java)

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(navController = navController, userViewModel = userViewModel)
                }
                composable("register") {
                    RegisterScreen(navController = navController, userViewModel = userViewModel)
                }
                composable("home/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
                    userId?.let {
                        FetchUserAndNavigate(userViewModel, it) { user ->
                            HomeScreen(navController = navController, user = user)
                        }
                    }
                }
                composable("wordgame/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
                    userId?.let {
                        FetchUserAndNavigate(userViewModel, it) { user ->
                            WordGameScreen(navController = navController, user = user, viewModel = wordGameViewModel, statisticViewModel = wordGameStatisticViewModel)
                        }
                    }
                }
                composable("flip_card_game/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
                    userId?.let {
                        FetchUserAndNavigate(userViewModel, it) { user ->
                            val flipCardGameViewModel = ViewModelProvider(this@MainActivity, flipCardGameViewModelFactory).get(
                                FlipCardGameViewModel::class.java)
                            FlipCardGameScreen(navController = navController, user = user, viewModel = flipCardGameViewModel)
                        }
                    }
                }
                composable("statistics/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
                    userId?.let {
                        FetchUserAndNavigate(userViewModel, it) { user ->
                            StatisticsScreen(
                                navController = navController,
                                user = user
                            )
                        }
                    }
                }
                composable("flipCardStatisticScreen/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
                    userId?.let {
                        FetchUserAndNavigate(userViewModel, it) { user ->
                            FlipCardStatisticScreen(
                                navController = navController,
                                user = user,
                                viewModel = flipCardStatisticViewModel
                            )
                        }
                    }
                }
                composable("wordGameStatisticScreen/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
                    userId?.let {
                        FetchUserAndNavigate(userViewModel, it) { user ->
                            WordGameStatisticScreen(
                                navController = navController,
                                user = user,
                                viewModel = wordGameStatisticViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FetchUserAndNavigate(userViewModel: UserViewModel, userId: Int, content: @Composable (User) -> Unit) {
    val user by produceState<User?>(initialValue = null, userId) {
        value = withContext(Dispatchers.IO) {
            userViewModel.fetchUserById(userId)
        }
    }
    user?.let { content(it) }
}