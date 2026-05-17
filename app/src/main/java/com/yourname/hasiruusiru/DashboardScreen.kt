package com.yourname.hasiruusiru

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourname.hasiruusiru.ui.theme.ForestGreen
import com.yourname.hasiruusiru.ui.theme.LightMint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: TreeViewModel,
    onBack: () -> Unit
) {
    val streetScores by viewModel.streetLeaderboard.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Oxygen Dashboard", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = LightMint)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LightMint),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                CityStatsOverview()
            }
            
            item {
                Text("Street Leaderboard", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            if (streetScores.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = ForestGreen)
                    }
                }
            } else {
                items(streetScores.sortedByDescending { it.oxygenScore }) { street ->
                    StreetLeaderboardCard(street)
                }
            }
        }
    }
}

@Composable
fun CityStatsOverview() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = ForestGreen),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Total Trees", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.labelLarge)
                Text("12,450", color = Color.White, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Green Gaps: 142", color = Color.White.copy(alpha = 0.9f), style = MaterialTheme.typography.bodyMedium)
            }
            
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(100.dp)) {
                CircularProgressIndicator(
                    progress = { 0.75f },
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White,
                    strokeWidth = 8.dp,
                    strokeCap = StrokeCap.Round,
                    trackColor = Color.White.copy(alpha = 0.2f)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("75%", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text("Goal", color = Color.White, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@Composable
fun StreetLeaderboardCard(street: StreetScore) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(ForestGreen.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${street.oxygenScore}%",
                    color = ForestGreen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(text = street.streetName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(text = "${street.totalTrees} trees • ${street.greenGaps} gaps", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            }
            
            LinearProgressIndicator(
                progress = { street.oxygenScore / 100f },
                modifier = Modifier.width(60.dp).height(6.dp).clip(CircleShape),
                color = ForestGreen,
                trackColor = Color.LightGray.copy(alpha = 0.3f)
            )
        }
    }
}
