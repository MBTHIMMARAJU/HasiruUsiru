package com.yourname.hasiruusiru

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourname.hasiruusiru.ui.theme.ForestGreen
import com.yourname.hasiruusiru.ui.theme.LightMint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreeTaggerScreen(
    viewModel: TreeViewModel,
    onBack: () -> Unit
) {
    var species by remember { mutableStateOf("") }
    var health by remember { mutableStateOf(TreeHealth.GOOD) }
    var girth by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Tag New Tree", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Brush.verticalGradient(listOf(LightMint, Color.White)))
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Camera Button
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .border(2.dp, ForestGreen.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.CameraAlt, null, modifier = Modifier.size(48.dp), tint = ForestGreen)
                    Text("Capture Tree", style = MaterialTheme.typography.labelLarge, color = ForestGreen)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Location Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, null, tint = ForestGreen)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("GPS Location", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        Text("12.9716° N, 77.5946° E", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Form Fields
            OutlinedTextField(
                value = species,
                onValueChange = { species = it },
                label = { Text("Species (e.g. Neem / ಬೇವು)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Tree Health", modifier = Modifier.align(Alignment.Start), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                HealthSelectButton(TreeHealth.GOOD, isSelected = health == TreeHealth.GOOD, modifier = Modifier.weight(1f)) { health = TreeHealth.GOOD }
                HealthSelectButton(TreeHealth.AVERAGE, isSelected = health == TreeHealth.AVERAGE, modifier = Modifier.weight(1f)) { health = TreeHealth.AVERAGE }
                HealthSelectButton(TreeHealth.POOR, isSelected = health == TreeHealth.POOR, modifier = Modifier.weight(1f)) { health = TreeHealth.POOR }
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = girth,
                onValueChange = { girth = it },
                label = { Text("Girth (in cm)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { 
                    viewModel.tagTree(
                        Tree(
                            species = species,
                            health = health,
                            girthCm = girth.toDoubleOrNull() ?: 0.0,
                            latitude = 12.9716,
                            longitude = 77.5946
                        )
                    )
                    onBack()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
            ) {
                Text("Tag This Tree", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun HealthSelectButton(
    type: TreeHealth, 
    isSelected: Boolean, 
    modifier: Modifier = Modifier, 
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(48.dp)
            .clickable { onClick() },
        color = if (isSelected) type.color else Color.White,
        shape = RoundedCornerShape(12.dp),
        border = if (isSelected) null else BorderStroke(1.dp, Color.LightGray)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = type.label,
                color = if (isSelected) Color.White else Color.Black,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}
