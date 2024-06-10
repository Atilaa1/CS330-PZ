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
import rs.ac.metropolitan.cs330_pz.data.api.RandomWordApi
import rs.ac.metropolitan.cs330_pz.data.db.DatabaseDB
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.data.repository.WordGameStatisticRepositoryImpl
import rs.ac.metropolitan.cs330_pz.data.repository.FlipCardStatisticRepositoryImpl
import rs.ac.metropolitan.cs330_pz.data.repository.UserRepositoryImpl
import rs.ac.metropolitan.cs330_pz.domain.use_case.flipcard_use_case.get_statistic_by_user_id.GetStatisticByUserIdUseCase
import rs.ac.metropolitan.cs330_pz.domain.use_case.flipcard_use_case.insert_statistic.InsertStatisticUseCase
import rs.ac.metropolitan.cs330_pz.domain.use_case.user_use_case.get_user_by_id.GetUserByIdUseCase
import rs.ac.metropolitan.cs330_pz.domain.use_case.user_use_case.get_user_by_username_and_password.GetUserByUsernameAndPasswordUseCase
import rs.ac.metropolitan.cs330_pz.domain.use_case.user_use_case.insert.InsertUserUseCase

import rs.ac.metropolitan.cs330_pz.domain.use_case.word_game_statistic.get_statistics_for_user.GetStatisticsForUserUseCase
import rs.ac.metropolitan.cs330_pz.domain.use_case.word_game_statistic.insert.InsertWordGameStatisticUseCase

import rs.ac.metropolitan.cs330_pz.domain.use_case.word_use_case.fetch_use_case.FetchWordsUseCase
import rs.ac.metropolitan.cs330_pz.presentation.flipCardStatistic.FlipCardStatisticViewModelFactory
import rs.ac.metropolitan.cs330_pz.presentation.flipcardgame.FlipCardGameViewModel
import rs.ac.metropolitan.cs330_pz.presentation.flipcardgame.FlipCardGameViewModelFactory
import rs.ac.metropolitan.cs330_pz.presentation.flipcardgame.components.FlipCardGameScreen
import rs.ac.metropolitan.cs330_pz.presentation.home.components.HomeScreen
import rs.ac.metropolitan.cs330_pz.presentation.login.LoginViewModel
import rs.ac.metropolitan.cs330_pz.presentation.login.LoginViewModelFactory
import rs.ac.metropolitan.cs330_pz.presentation.register.RegisterViewModel
import rs.ac.metropolitan.cs330_pz.presentation.register.RegisterViewModelFactory
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
        val userRepositoryImpl = UserRepositoryImpl(database.userDao())
        val flipCardStatisticRepositoryImpl = FlipCardStatisticRepositoryImpl(database.flipCardStatisticDao())
        val wordGameStatisticRepositoryImpl = WordGameStatisticRepositoryImpl(database.wordGameStatisticDao())
        val randomWordApi = RandomWordApi.create()  // Assuming you have a function to create the API instance

        // Initialize use cases
        val getUserByUsernameAndPasswordUseCase = GetUserByUsernameAndPasswordUseCase(userRepositoryImpl)
        val getUserByIdUseCase = GetUserByIdUseCase(userRepositoryImpl)
        val insertUserUseCase = InsertUserUseCase(userRepositoryImpl)
        val insertWordGameStatisticUseCase = InsertWordGameStatisticUseCase(wordGameStatisticRepositoryImpl)
        val getStatisticsForUserUseCase = GetStatisticsForUserUseCase(wordGameStatisticRepositoryImpl)
        val fetchWordsUseCase = FetchWordsUseCase(randomWordApi)

        val insertStatisticUseCase= InsertStatisticUseCase(flipCardStatisticRepositoryImpl)
        val getStatisticByUserIdUseCase = GetStatisticByUserIdUseCase(flipCardStatisticRepositoryImpl)

        // Initialize ViewModel factories with use cases
        val loginViewModelFactory = LoginViewModelFactory(getUserByUsernameAndPasswordUseCase, getUserByIdUseCase)
        val registerViewModelFactory = RegisterViewModelFactory(insertUserUseCase)
        val wordGameViewModelFactory = WordGameViewModelFactory(fetchWordsUseCase, insertWordGameStatisticUseCase)
        val wordGameStatisticViewModelFactory = WordGameStatisticViewModelFactory(insertWordGameStatisticUseCase, getStatisticsForUserUseCase)

        val loginViewModel = ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)
        val registerViewModel = ViewModelProvider(this, registerViewModelFactory).get(RegisterViewModel::class.java)
        val wordGameViewModel = ViewModelProvider(this, wordGameViewModelFactory).get(WordGameViewModel::class.java)
        val wordGameStatisticViewModel = ViewModelProvider(this, wordGameStatisticViewModelFactory).get(WordGameStatisticViewModel::class.java)

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(navController = navController, loginViewModel = loginViewModel)
                }
                composable("register") {
                    RegisterScreen(navController = navController, registerViewModel = registerViewModel)
                }
                composable("home/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
                    userId?.let {
                        FetchUserAndNavigate(loginViewModel, it) { user ->
                            HomeScreen(navController = navController, user = user)
                        }
                    }
                }
                composable("wordgame/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
                    userId?.let {
                        FetchUserAndNavigate(loginViewModel, it) { user ->
                            WordGameScreen(navController = navController, user = user, viewModel = wordGameViewModel, statisticViewModel = wordGameStatisticViewModel)
                        }
                    }
                }
                composable("flip_card_game/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
                    userId?.let {
                        FetchUserAndNavigate(loginViewModel, it) { user ->
                            val flipCardGameViewModelFactory = FlipCardGameViewModelFactory(insertStatisticUseCase)
                            val flipCardGameViewModel = ViewModelProvider(this@MainActivity, flipCardGameViewModelFactory).get(FlipCardGameViewModel::class.java)
                            FlipCardGameScreen(navController = navController, user = user, viewModel = flipCardGameViewModel)
                        }
                    }
                }
                composable("statistics/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
                    userId?.let {
                        FetchUserAndNavigate(loginViewModel, it) { user ->
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
                        FetchUserAndNavigate(loginViewModel, it) { user ->
                            val flipCardStatisticViewModelFactory = FlipCardStatisticViewModelFactory(getStatisticByUserIdUseCase)
                            val flipCardStatisticViewModel = ViewModelProvider(this@MainActivity, flipCardStatisticViewModelFactory).get(FlipCardStatisticViewModel::class.java)
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
                        FetchUserAndNavigate(loginViewModel, it) { user ->
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
fun FetchUserAndNavigate(loginViewModel: LoginViewModel, userId: Int, content: @Composable (User) -> Unit) {
    val user by produceState<User?>(initialValue = null, userId) {
        value = withContext(Dispatchers.IO) {
            loginViewModel.fetchUserById(userId)
        }
    }
    user?.let { content(it) }
}
