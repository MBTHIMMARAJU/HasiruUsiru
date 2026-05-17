package com.yourname.hasiruusiru

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.yourname.hasiruusiru.ui.theme.AmberAccent
import com.yourname.hasiruusiru.ui.theme.ForestGreen
import com.yourname.hasiruusiru.ui.theme.LightGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: TreeViewModel,
    onAddTreeClick: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToGuide: () -> Unit
) {
    val trees by viewModel.trees.collectAsState()
    val bangalore = LatLng(12.9716, 77.5946)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(bangalore, 13f)
    }
    
    var selectedTree by remember { mutableStateOf<Tree?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Hasiru Usiru",
                        fontWeight = FontWeight.Bold,
                        color = ForestGreen,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                actions = {
                    IconButton(onClick = { }) { Icon(Icons.Default.Search, "Search") }
                    IconButton(onClick = { }) { Icon(Icons.Default.FilterList, "Filter") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTreeClick,
                containerColor = ForestGreen,
                contentColor = Color.White,
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(Icons.Default.Park, contentDescription = "Add Tree", modifier = Modifier.size(32.dp))
            }
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Map, null) },
                    label = { Text("Map") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToGuide,
                    icon = { Icon(Icons.AutoMirrored.Filled.MenuBook, null) },
                    label = { Text("Species") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToDashboard,
                    icon = { Icon(Icons.Default.BarChart, null) },
                    label = { Text("Stats") }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(zoomControlsEnabled = false)
            ) {
                // Real data markers
                trees.forEach { tree ->
                    Marker(
                        state = MarkerState(position = LatLng(tree.latitude, tree.longitude)),
                        title = tree.species,
                        onClick = {
                            selectedTree = tree
                            showBottomSheet = true
                            true
                        }
                    )
                }
            }

            StreetOxygenCard(modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp))

            AnimatedVisibility(
                visible = showBottomSheet,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                TreeDetailCard(tree = selectedTree, onClose = { showBottomSheet = false })
            }
        }
    }
}

@Composable
fun StreetOxygenCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.width(300.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🌳 MG Road Oxygen", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.weight(1.0f))
                Text("88%", fontWeight = FontWeight.ExtraBold, color = ForestGreen)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { 0.88f },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                color = ForestGreen,
                trackColor = LightGreen.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun TreeDetailCard(tree: Tree?, onClose: () -> Unit) {
    if (tree == null) return
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = tree.species, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text(text = tree.kannadaName, style = MaterialTheme.typography.titleMedium, color = ForestGreen)
                }
                HealthBadge(health = tree.health)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                StatBox(label = "Oxygen Score", value = "${tree.oxygenScore}%", color = ForestGreen)
                StatBox(label = "Girth", value = "${tree.girthCm} cm", color = AmberAccent)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onClose,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
            ) {
                Text("Close Details")
            }
        }
    }
}

@Composable
fun HealthBadge(health: TreeHealth) {
    Surface(
        color = health.color.copy(alpha = 0.2f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = health.label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = health.color,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun StatBox(label: String, value: String, color: Color) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = color)
    }
}
