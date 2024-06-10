package rs.ac.metropolitan.cs330_pz.presentation.home.components

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import rs.ac.metropolitan.cs330_pz.R
import rs.ac.metropolitan.cs330_pz.data.db.entity.User


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, user: User) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Welcome, ${user.username}", fontSize = 20.sp)
                },
                actions = {
                    IconButton(onClick = { navController.navigate("login") }) {
                        Icon(painter = painterResource(R.drawable.ic_logout), contentDescription = "Logout")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, user = user)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.brain),
                contentDescription = "Register Icon",
                modifier = Modifier.size(316.dp)
            )
            Button(
                onClick = { navController.navigate("flip_card_game/${user.id}") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(text = "Flip Card Game", fontSize = 18.sp)
            }
            Button(
                onClick = { navController.navigate("wordgame/${user.id}") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(text = "Word Remembering Game", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, user: User) {
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_home),
                    contentDescription = "Home"
                )
            },
            selected = false,
            onClick = { navController.navigate("home/${user.id}") }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_profile),
                    contentDescription = "Statistics"
                )
            },
            selected = false,
            onClick = { navController.navigate("statistics/${user.id}") }
        )
    }
}