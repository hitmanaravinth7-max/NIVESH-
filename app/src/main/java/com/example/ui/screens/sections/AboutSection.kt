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
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Warning
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
fun AboutSection(
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
                            imageVector = Icons.Default.HealthAndSafety,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = "About Parkinson’s Disease",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Pathophysiology, Vocal Biomarkers & Machine Learning",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // 1. What is Parkinson's Disease
        EducationalCard(
            title = "1. What is Parkinson’s Disease?",
            icon = Icons.Default.Psychology,
            color = MaterialTheme.colorScheme.primary
        ) {
            Text(
                text = "Parkinson’s disease is a progressive neurodegenerative disorder of the central nervous system that primarily affects the motor system. It is characterized by the loss of dopamine-producing neurons in a critical brain region called the substantia nigra pars compacta.\n\nBecause dopamine functions as a crucial chemical neurotransmitter controlling smooth, coordinated muscle movements, its depletion leads to involuntary movement disorders and gradual loss of motor control.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }

        // 2. Common Symptoms
        EducationalCard(
            title = "2. Cardinal Symptoms & Clinical Signs",
            icon = Icons.Default.HealthAndSafety,
            color = Color(0xFF0284C7)
        ) {
            Text(
                text = "Clinically, Parkinson’s symptoms are categorized into motor and non-motor manifestations:\n\n• Tremor: Involuntary resting tremor, often beginning unilaterally in the hand (\"pill-rolling\").\n• Rigidity: Inflexible, stiff limb muscles causing resistance to movement (\"cogwheel rigidity\").\n• Bradykinesia: Slowness of voluntary physical movements and reduced facial expression (\"masked facies\").\n• Postural Instability: Impaired balance, stooped posture, and increased fall risks.\n• Dysphonia & Hypophonia: Monotone voice, soft speech volume, vocal micro-tremors, and breathiness.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }

        // 3. Why Early Detection Matters
        EducationalCard(
            title = "3. Importance of Early Detection",
            icon = Icons.Default.Speed,
            color = Color(0xFF059669)
        ) {
            Text(
                text = "By the time characteristic visible motor tremors appear, patients typically have already lost 60% to 80% of their dopaminergic neurons. Early detection is vital because:\n\n• Therapeutic Interventions: Neuroprotective therapies, physical rehabilitation, and dopamine agonists are significantly more effective when initiated in early stages.\n• Quality of Life: Prolongs independent living and preserves cognitive and physical faculties.\n• Non-Invasive Screening: Acoustic and voice-based tests allow rapid, non-invasive, remote pre-screening without hospital visits.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }

        // 4. Role of Voice Measurements
        EducationalCard(
            title = "4. Role of Vocal & Acoustic Measurements",
            icon = Icons.Default.GraphicEq,
            color = Color(0xFF7C3AED)
        ) {
            Text(
                text = "Over 90% of individuals with Parkinson’s develop voice impairments (hypokinetic dysarthria). Because the vocal folds are actuated by delicate laryngeal musculature, subtle dopaminergic deficits manifest as micro-tremors and acoustic instability long before visible limb tremors.\n\nKey acoustic measures include:\n• Fundamental Frequency (Fo): Pitch modulation and stability during sustained vowel phonation (/a/).\n• Jitter: Micro-fluctuations in vocal cycle frequency.\n• Shimmer: Cycle-to-cycle amplitude variations.\n• Harmonics-to-Noise Ratio (HNR): Airflow turbulence and breathiness caused by incomplete glottal closure.\n• Nonlinear Dynamics (PPE, RPDE, spread1/2): Measure complexity and turbulence in vocal fold biomechanics.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }

        // 5. Role of Machine Learning
        EducationalCard(
            title = "5. Role of Machine Learning in Detection",
            icon = Icons.Default.Analytics,
            color = Color(0xFFD97706)
        ) {
            Text(
                text = "Machine learning algorithms—such as Support Vector Machines (SVM), Random Forests, and Logistic Discriminant models—can identify subtle, multi-dimensional correlations across acoustic parameters that are imperceptible to the human ear.\n\nBy mapping 22 acoustic features extracted via software (such as Praat) onto calibrated decision boundaries, ML models achieve screening accuracy rates exceeding 90% on benchmark medical datasets (e.g., Oxford University / UCI Parkinson's Dataset).",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }

        // 6. Limitations of AI-Based Prediction
        EducationalCard(
            title = "6. Limitations & Academic Scope",
            icon = Icons.Default.Warning,
            color = MaterialTheme.colorScheme.error
        ) {
            Text(
                text = "While AI acoustic analysis is a powerful research screening instrument, it carries inherent clinical limitations:\n\n• Confounding Factors: Vocal strain, laryngitis, smoking, background recording noise, or fatigue can alter acoustic metrics.\n• Non-Diagnostic Nature: Voice analysis cannot replace definitive clinical neurological examination, MRI neuroimaging, or DaTscan.\n• Educational Intent: This project serves strictly as an academic demonstration of AI application in biomedical signal processing.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun EducationalCard(
    title: String,
    icon: ImageVector,
    color: Color,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = color.copy(alpha = 0.12f),
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = color,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}
