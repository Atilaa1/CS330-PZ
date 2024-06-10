package rs.ac.metropolitan.cs330_pz.presentation.wordgame.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import rs.ac.metropolitan.cs330_pz.R
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.presentation.wordGameStatistic.WordGameStatisticViewModel
import rs.ac.metropolitan.cs330_pz.presentation.wordgame.WordGameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WordGameScreen(navController: NavController, user: User, viewModel: WordGameViewModel = viewModel(), statisticViewModel: WordGameStatisticViewModel = viewModel()) {
    var numberOfWords by remember { mutableStateOf(10) }
    var currentGuess by remember { mutableStateOf("") }
    var showWords by remember { mutableStateOf(false) }
    var inputVisible by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Word Game") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home/${user.id}") }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!showWords && !inputVisible) {
                Text("How many words do you want to guess?")
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { if (numberOfWords > 1) numberOfWords-- }) {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_minus), contentDescription = "Decrease")
                    }
                    Text(text = numberOfWords.toString(), modifier = Modifier.padding(horizontal = 8.dp))
                    IconButton(onClick = { numberOfWords++ }) {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus), contentDescription = "Increase")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    viewModel.fetchWords(numberOfWords)
                    showWords = true
                }) {
                    Text("Fetch Words")
                }
            }

            if (showWords) {
                Text("Random Words: ${viewModel.words.joinToString(", ")}")
                Spacer(modifier = Modifier.height(16.dp))

                LaunchedEffect(Unit) {
                    delay(15000)
                    showWords = false
                    inputVisible = true
                }
            }

            if (inputVisible) {
                TextField(
                    value = currentGuess,
                    onValueChange = { currentGuess = it },
                    label = { Text("Enter your guess") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    viewModel.addUserGuess(currentGuess)
                    currentGuess = ""
                }) {
                    Text("Submit Guess")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Your Guesses: ${viewModel.userGuesses.joinToString(", ")}")
                Text("Score: ${viewModel.score}")
                Text("Incorrect Guesses: ${viewModel.incorrectGuesses.joinToString(", ")}")

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    viewModel.saveStatistic(user.id!!, numberOfWords)
                    showDialog = true
                }) {
                    Text("Stop Game")
                }

                // Automatically update the score whenever user guesses change
                LaunchedEffect(key1 = viewModel.userGuesses) {
                    viewModel.checkScore()
                    if (viewModel.userGuesses.count { it in viewModel.words } == numberOfWords) {
                        viewModel.saveStatistic(user.id!!, numberOfWords)
                        showDialog = true
                    }
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Congratulations!") },
                        text = {
                            Column {
                                Text("Your score is: ${viewModel.score}")
                                Text("Times guessed: ${viewModel.userGuesses.size}")
                                Text("Incorrect Guesses: ${viewModel.incorrectGuesses.size}")
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                showDialog = false
                                navController.navigate("home/${user.id}")
                            }) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
        }
    }
}