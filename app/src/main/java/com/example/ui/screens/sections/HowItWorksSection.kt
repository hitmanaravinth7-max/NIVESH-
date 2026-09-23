package com.example.ui.screens.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HowItWorksSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = "System Architecture & Workflow",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Step-by-step pipeline from voice capture to clinical prediction",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Visual Pipeline Steps
        WorkflowStepCard(
            stepNumber = "1",
            title = "User Authentication & Session",
            subtitle = "Role: Clinical Researcher / Student Portal",
            description = "Users sign in via validated credentials or 1-tap academic reviewer access. The session ensures responsible local storage and screening session continuity.",
            icon = Icons.Default.Login,
            nodeColor = MaterialTheme.colorScheme.primary
        )

        WorkflowStepCard(
            stepNumber = "2",
            title = "Enter Clinical Voice Features",
            subtitle = "22 Acoustic Parameters from Sustained /a/ Phonation",
            description = "The user inputs or loads pre-extracted acoustic metrics covering pitch fundamental frequency (Fo/Fhi/Flo), jitter micro-fluctuations, shimmer amplitude variations, harmonics-to-noise ratio (HNR), and nonlinear dynamical measures (RPDE, DFA, PPE).",
            icon = Icons.Default.GraphicEq,
            nodeColor = Color(0xFF0284C7)
        )

        WorkflowStepCard(
            stepNumber = "3",
            title = "Input Validation & Range Verification",
            subtitle = "Biomedical Bounds Checking",
            description = "Every numerical field undergoes rigorous client-side validation to check for completeness, decimal validity, and physiologically acceptable ranges. Helpful diagnostic error messages guide the user if values are anomalous.",
            icon = Icons.Default.Rule,
            nodeColor = Color(0xFF0D9488)
        )

        WorkflowStepCard(
            stepNumber = "4",
            title = "Machine Learning Classification Model",
            subtitle = "Calibrated Logistic Regression / SVM Discriminant",
            description = "Features are standardized against empirical UCI Parkinson dataset statistics (means and standard deviations). The classifier evaluates calibrated feature weights—placing key emphasis on Pitch Period Entropy (PPE), spread1, HNR, and Jitter.",
            icon = Icons.Default.Psychology,
            nodeColor = Color(0xFF7C3AED)
        )

        WorkflowStepCard(
            stepNumber = "5",
            title = "Prediction & Probability Mapping",
            subtitle = "Sigmoid Mapping & Decision Boundary",
            description = "The discriminant logit score is mapped via sigmoid function into class probability P(Parkinson's). If P ≥ 0.50, the result is flagged as 'Parkinson’s Detected'; otherwise 'Healthy / No Parkinson’s Detected'.",
            icon = Icons.Default.Assessment,
            nodeColor = Color(0xFFD97706)
        )

        WorkflowStepCard(
            stepNumber = "6",
            title = "Confidence Score Calculation",
            subtitle = "Statistically Bounded Certainty Metric",
            description = "A confidence score (65% to 98%) is derived based on the distance of the sample from the multi-dimensional decision hyperplane, accompanied by individual biomarker deviation flags.",
            icon = Icons.Default.Speed,
            nodeColor = Color(0xFFE11D48)
        )

        WorkflowStepCard(
            stepNumber = "7",
            title = "Result Display & Clinical Recommendation",
            subtitle = "Actionable Academic Guidance",
            description = "A comprehensive clinical result card provides clear visual indicators, acoustic explanations, and tailored healthcare recommendations. Crucially, the mandatory educational disclaimer confirms the system is for non-diagnostic academic screening.",
            icon = Icons.Default.MedicalServices,
            nodeColor = Color(0xFF10B981)
        )
    }
}

@Composable
private fun WorkflowStepCard(
    stepNumber: String,
    title: String,
    subtitle: String,
    description: String,
    icon: ImageVector,
    nodeColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(36.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = nodeColor,
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = stepNumber,
                            color = Color.White,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = nodeColor,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = nodeColor,
                    modifier = Modifier.padding(top = 2.dp, bottom = 6.dp)
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 19.sp
                )
            }
        }
    }
}
