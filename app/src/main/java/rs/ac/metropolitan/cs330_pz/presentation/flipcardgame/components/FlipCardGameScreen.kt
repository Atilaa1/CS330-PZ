package rs.ac.metropolitan.cs330_pz.presentation.flipcardgame.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import rs.ac.metropolitan.cs330_pz.R
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.presentation.flipcardgame.FlipCardGameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FlipCardGameScreen(navController: NavController, user: User, viewModel: FlipCardGameViewModel = viewModel()) {
    val showDialog = viewModel.showDialog

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flip Card Game") },
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (viewModel.gameStarted) {
                GameContent(viewModel)

            } else {
                Button(onClick = {
                    viewModel.startGame(user)
                    Log.d("FlipCardGameScreen", "Game started.")
                }) {
                    Text("Start Game")
                }
            }
        }

        if (showDialog) {
            Log.d("FlipCardGameScreen", "Showing AlertDialog.")
            GameEndDialog(navController, user, viewModel) { viewModel.showDialog = it }
        }
    }
}

@Composable
fun GameContent(viewModel: FlipCardGameViewModel) {
    Text("Score: ${viewModel.score}")
    Spacer(modifier = Modifier.height(16.dp))
    Text("Time Elapsed: ${viewModel.timeElapsed} seconds")
    Spacer(modifier = Modifier.height(16.dp))
    Text("Clicks: ${viewModel.clickCount}")
    Spacer(modifier = Modifier.height(16.dp))
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (row in 0 until 4) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (col in 0 until 2) {
                    val index = row * 2 + col
                    CardButton(index = index, viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun GameEndDialog(navController: NavController, user: User, viewModel: FlipCardGameViewModel, onDismiss: (Boolean) -> Unit) {
    AlertDialog(
        onDismissRequest = {
            onDismiss(false)
            Log.d("FlipCardGameScreen", "AlertDialog dismissed.")
        },
        title = { Text("Congratulations!") },
        text = {
            Column {
                Text("Your score is: ${viewModel.score}")
                Text("Times Clicked: ${viewModel.clickCount}")
                Text("Time Elapsed: ${viewModel.timeElapsed} seconds")
            }
        },
        confirmButton = {
            Button(onClick = {
                onDismiss(false)
                navController.navigate("home/${user.id}")
                Log.d("FlipCardGameScreen", "AlertDialog confirm button clicked.")
            }) {
                Text("OK")
            }
        }
    )
}

@Composable
fun CardButton(index: Int, viewModel: FlipCardGameViewModel) {
    val isFlipped = viewModel.revealedCards.contains(index) || viewModel.matchedCards.contains(index)
    val imageRes = if (isFlipped) viewModel.cardImages[index] else R.drawable.brain

    Button(
        onClick = {
            viewModel.flipCard(index)
            Log.d("FlipCardGameScreen", "Card at index $index flipped.")
        },
        enabled = !isFlipped,
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Image(painter = painterResource(id = imageRes), contentDescription = "Card")
    }
}
