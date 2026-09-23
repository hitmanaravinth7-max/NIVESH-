package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.auth.UserSession
import com.example.data.model.PredictionEntity
import com.example.ui.components.AppFooter
import com.example.ui.screens.sections.AboutSection
import com.example.ui.screens.sections.HistorySection
import com.example.ui.screens.sections.HowItWorksSection
import com.example.ui.screens.sections.OverviewSection
import com.example.ui.screens.sections.ResultSection
import com.example.ui.screens.sections.ScreeningFormSection
import com.example.ui.viewmodel.DashboardSection
import com.example.ui.viewmodel.FormFields

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    session: UserSession,
    currentSection: DashboardSection,
    formState: FormFields,
    fieldErrors: Map<String, String>,
    formErrorMessage: String?,
    isPredicting: Boolean,
    currentResult: PredictionEntity?,
    historyList: List<PredictionEntity>,
    totalCount: Int,
    detectedCount: Int,
    onNavigateSection: (DashboardSection) -> Unit,
    onFieldChange: (key: String, value: String) -> Unit,
    onLoadSample: (isHealthy: Boolean) -> Unit,
    onClearForm: () -> Unit,
    onPredict: () -> Unit,
    onViewResult: (PredictionEntity) -> Unit,
    onDeleteResult: (Long) -> Unit,
    onClearAllHistory: () -> Unit,
    onLogout: () -> Unit
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    val tabs = listOf(
        DashboardSection.OVERVIEW to "Overview",
        DashboardSection.SCREENING to "Screening Form",
        DashboardSection.RESULT to "Result",
        DashboardSection.HISTORY to "History (${historyList.size})",
        DashboardSection.ABOUT to "About Disease",
        DashboardSection.HOW_IT_WORKS to "How It Works"
    )

    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 2.dp
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.HealthAndSafety,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Parkinson’s Disease Detection",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "AI/ML Clinical Voice Analysis System",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // User Profile Pill & Logout
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = session.username.ifBlank { "Researcher" },
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        IconButton(
                            onClick = { showLogoutDialog = true },
                            modifier = Modifier.testTag("logout_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Logout,
                                contentDescription = "Log out",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    // Horizontal Navigation Tab Bar
                    ScrollableTabRow(
                        selectedTabIndex = tabs.indexOfFirst { it.first == currentSection }.coerceAtLeast(0),
                        edgePadding = 12.dp,
                        containerColor = MaterialTheme.colorScheme.surface,
                        indicator = { tabPositions ->
                            val index = tabs.indexOfFirst { it.first == currentSection }.coerceAtLeast(0)
                            if (index < tabPositions.size) {
                                TabRowDefaults.SecondaryIndicator(
                                    Modifier.tabIndicatorOffset(tabPositions[index]),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    ) {
                        tabs.forEachIndexed { index, (section, title) ->
                            val selected = currentSection == section
                            Tab(
                                selected = selected,
                                onClick = { onNavigateSection(section) },
                                text = {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                                        ),
                                        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                modifier = Modifier.testTag("tab_${section.name.lowercase()}")
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            AppFooter()
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 720.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 24.dp)
            ) {
                when (currentSection) {
                    DashboardSection.OVERVIEW -> {
                        OverviewSection(
                            totalScreenings = totalCount,
                            detectedCount = detectedCount,
                            onNavigateSection = onNavigateSection
                        )
                    }
                    DashboardSection.SCREENING -> {
                        ScreeningFormSection(
                            formState = formState,
                            fieldErrors = fieldErrors,
                            formErrorMessage = formErrorMessage,
                            isPredicting = isPredicting,
                            onFieldChange = onFieldChange,
                            onLoadSample = onLoadSample,
                            onClear = onClearForm,
                            onPredict = onPredict
                        )
                    }
                    DashboardSection.RESULT -> {
                        ResultSection(
                            result = currentResult,
                            onNavigateSection = onNavigateSection
                        )
                    }
                    DashboardSection.HISTORY -> {
                        HistorySection(
                            historyList = historyList,
                            onViewResult = onViewResult,
                            onDeleteResult = onDeleteResult,
                            onClearAll = onClearAllHistory,
                            onNavigateSection = onNavigateSection
                        )
                    }
                    DashboardSection.ABOUT -> {
                        AboutSection()
                    }
                    DashboardSection.HOW_IT_WORKS -> {
                        HowItWorksSection()
                    }
                }
            }
        }

        // Logout Confirmation Dialog
        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("Log Out of Session?") },
                text = { Text("Are you sure you want to end your current academic research session?") },
                confirmButton = {
                    Button(
                        onClick = {
                            showLogoutDialog = false
                            onLogout()
                        }
                    ) {
                        Text("Log Out")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
