package rs.ac.metropolitan.cs330_pz.presentation.wordGameStatistic.components


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import rs.ac.metropolitan.cs330_pz.R
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.data.db.entity.WordGameStatistic
import rs.ac.metropolitan.cs330_pz.presentation.wordGameStatistic.WordGameStatisticViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordGameStatisticScreen(navController: NavController, user: User, viewModel: WordGameStatisticViewModel) {
    val statistics by viewModel.getStatisticsForUser(user.id!!).collectAsState(initial = emptyList())


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Word Game Statistics") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("profile/${user.id}") }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    )
    {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (statistics.isEmpty()) {
                Text("No statistics available.")
            } else {
                Spacer(modifier = Modifier.height(48.dp))
                LazyColumn {
                    items(statistics) { statistic ->
                        StatisticItem(statistic)
                    }
                }
            }
        }
    }
}

@Composable
fun StatisticItem(statistic: WordGameStatistic) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Number of Guessing Words: ${statistic.numberOfGuessingWords}")
            Text("Score: ${statistic.score}")

        }
    }
}