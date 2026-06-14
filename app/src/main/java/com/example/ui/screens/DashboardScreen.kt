package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.data.RecentDocument
import com.example.ui.theme.AadhaarBg
import com.example.ui.theme.AadhaarColor
import com.example.ui.theme.BgRemoveBg
import com.example.ui.theme.BgRemoveColor
import com.example.ui.theme.BulkBg
import com.example.ui.theme.BulkColor
import com.example.ui.theme.PanBg
import com.example.ui.theme.PanColor
import com.example.ui.theme.PassportBg
import com.example.ui.theme.PassportColor
import com.example.ui.theme.ScannerBg
import com.example.ui.theme.ScannerColor
import com.example.ui.viewmodels.DocumentViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.R

data class ToolItem(val id: String, val nameRes: Int, val icon: ImageVector, val iconTint: Color, val iconBg: Color)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DocumentViewModel,
    onNavigateToTool: (String) -> Unit
) {
    val recentDocs by viewModel.recentDocs.collectAsState()
    val isHindi by viewModel.isHindi.collectAsState()
    val tools = listOf(
        ToolItem("aadhaar_crop", R.string.tool_aadhaar, Icons.Default.Crop, AadhaarColor, AadhaarBg),
        ToolItem("pan_crop", R.string.tool_pan, Icons.Default.Description, PanColor, PanBg),
        ToolItem("passport_photo", R.string.tool_passport, Icons.Default.Person, PassportColor, PassportBg),
        ToolItem("scanner", R.string.tool_scanner, Icons.Default.PhotoCamera, ScannerColor, ScannerBg),
        ToolItem("bg_removal", R.string.tool_bg_removal, Icons.Default.AutoFixHigh, BgRemoveColor, BgRemoveBg),
        ToolItem("bulk_process", R.string.tool_bulk, Icons.Default.LibraryBooks, BulkColor, BulkBg)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        val titleParts = stringResource(R.string.dashboard_title).split(" / ")
                        val text1 = if (isHindi) (titleParts.getOrNull(1) ?: titleParts[0]) else titleParts[0]
                        val text2 = if (isHindi) "Privacy First • ${titleParts[0]}" else "Privacy First • ${titleParts.getOrNull(1) ?: ""}"
                        Text(text1, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text(text2, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                actions = {
                    Surface(
                        onClick = { viewModel.toggleLanguage() },
                        shape = RoundedCornerShape(50),
                        color = MaterialTheme.colorScheme.secondary,
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = if (isHindi) "हि/EN" else "EN/हि",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                    Surface(
                        shape = androidx.compose.foundation.shape.CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("KS", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = MaterialTheme.shapes.large
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "CURRENT PLAN: FREE (₹0)",
                            color = MaterialTheme.colorScheme.primaryContainer,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        )
                        Surface(
                            color = Color.White.copy(alpha = 0.2f),
                            shape = androidx.compose.foundation.shape.CircleShape,
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
                        ) {
                            Text(
                                "Upgrade",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "100% Browser-side processing",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Files never leave your phone. Safely print Aadhaar & PAN cards.",
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(tools) { tool ->
                    ToolCard(tool = tool, isHindi = isHindi, onClick = { 
                        // Simulate adding a doc
                        viewModel.addMockDocument("Document_${System.currentTimeMillis() % 10000}.pdf", "PDF")
                        onNavigateToTool(tool.id) 
                    })
                }
            }

            // Recent Files Section
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isHindi) "हालिया फ़ाइलें (Recent Locally)" else "Recent Locally (हालिया फ़ाइलें)",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp, fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    TextButton(onClick = { viewModel.clearHistory() }) {
                        Text(
                            text = if (isHindi) "सभी हटाएं" else "Clear All",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp, fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                if (recentDocs.isEmpty()) {
                    Text(
                        text = "No recent files.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        items(recentDocs) { doc ->
                            RecentFileThumbnailCard(doc)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecentFileThumbnailCard(document: RecentDocument) {
    Card(
        modifier = Modifier
            .width(96.dp)
            .height(112.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp).fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFFF0F4F8), RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = document.type.take(3).uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = document.name,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium, fontSize = 9.sp),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            val format = SimpleDateFormat("HH:mm", Locale.getDefault())
            Text(
                text = "Today ${format.format(Date(document.timestamp))}",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }
    }
}

@Composable
fun ToolCard(tool: ToolItem, isHindi: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Surface(
                shape = androidx.compose.foundation.shape.CircleShape,
                color = tool.iconBg,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = tool.icon,
                        contentDescription = stringResource(tool.nameRes),
                        modifier = Modifier.size(24.dp),
                        tint = tool.iconTint
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            val titleParts = stringResource(tool.nameRes).split(" / ")
            val primaryText = if (isHindi) (titleParts.getOrNull(1) ?: titleParts[0]) else titleParts[0]
            val secondaryText = if (isHindi) titleParts[0] else (titleParts.getOrNull(1) ?: "")
            
            Text(
                text = primaryText,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
            if (secondaryText.isNotEmpty()) {
                Text(
                    text = secondaryText,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
        }
    }
}
