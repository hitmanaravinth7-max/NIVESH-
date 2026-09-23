package com.example.ui.screens.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Waves
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.FormFields

@Composable
fun ScreeningFormSection(
    formState: FormFields,
    fieldErrors: Map<String, String>,
    formErrorMessage: String?,
    isPredicting: Boolean,
    onFieldChange: (key: String, value: String) -> Unit,
    onLoadSample: (isHealthy: Boolean) -> Unit,
    onClear: () -> Unit,
    onPredict: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Form Title & Benchmark Presets Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Science,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Clinical Voice Measurements",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Enter 22 acoustic parameters or load benchmark samples",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Quick Benchmark Presets (UCI Oxford Dataset):",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { onLoadSample(true) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("load_healthy_preset_button"),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Healthy Sample",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF047857)
                        )
                    }

                    OutlinedButton(
                        onClick = { onLoadSample(false) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("load_detected_preset_button"),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = Color(0xFFF43F5E),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Parkinson's Sample",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFBE123C)
                        )
                    }
                }
            }
        }

        // Global Validation Error Banner
        AnimatedVisibility(visible = formErrorMessage != null) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.errorContainer,
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = formErrorMessage ?: "",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }

        // 1. Vocal Fundamental Frequency (Fo, Fhi, Flo)
        FeatureGroupCard(
            title = "1. Vocal Pitch & Fundamental Frequency",
            subtitle = "Acoustic fundamental frequency extracted from sustained vowel phonation",
            icon = Icons.Default.GraphicEq
        ) {
            ClinicalInputField(
                label = "MDVP:Fo(Hz)",
                placeholder = "e.g. 197.076",
                unit = "Hz",
                helper = "Average vocal fundamental frequency",
                value = formState.fo,
                error = fieldErrors["fo"],
                onValueChange = { onFieldChange("fo", it) }
            )
            ClinicalInputField(
                label = "MDVP:Fhi(Hz)",
                placeholder = "e.g. 206.896",
                unit = "Hz",
                helper = "Maximum vocal fundamental frequency",
                value = formState.fhi,
                error = fieldErrors["fhi"],
                onValueChange = { onFieldChange("fhi", it) }
            )
            ClinicalInputField(
                label = "MDVP:Flo(Hz)",
                placeholder = "e.g. 192.055",
                unit = "Hz",
                helper = "Minimum vocal fundamental frequency",
                value = formState.flo,
                error = fieldErrors["flo"],
                onValueChange = { onFieldChange("flo", it) }
            )
        }

        // 2. Frequency Instability / Jitter
        FeatureGroupCard(
            title = "2. Frequency Perturbation (Jitter)",
            subtitle = "Cycle-to-cycle frequency variations reflecting vocal cord vibration stability",
            icon = Icons.Default.Waves
        ) {
            ClinicalInputField(
                label = "MDVP:Jitter(%)",
                placeholder = "e.g. 0.00289",
                unit = "ratio",
                helper = "MDVP percentage jitter instability",
                value = formState.jitterPercent,
                error = fieldErrors["jitterPercent"],
                onValueChange = { onFieldChange("jitterPercent", it) }
            )
            ClinicalInputField(
                label = "MDVP:Jitter(Abs)",
                placeholder = "e.g. 0.00001",
                unit = "sec",
                helper = "Absolute jitter in microseconds",
                value = formState.jitterAbs,
                error = fieldErrors["jitterAbs"],
                onValueChange = { onFieldChange("jitterAbs", it) }
            )
            ClinicalInputField(
                label = "MDVP:RAP",
                placeholder = "e.g. 0.00166",
                unit = "ratio",
                helper = "Relative amplitude perturbation",
                value = formState.rap,
                error = fieldErrors["rap"],
                onValueChange = { onFieldChange("rap", it) }
            )
            ClinicalInputField(
                label = "MDVP:PPQ",
                placeholder = "e.g. 0.00168",
                unit = "ratio",
                helper = "Five-point period perturbation quotient",
                value = formState.ppq,
                error = fieldErrors["ppq"],
                onValueChange = { onFieldChange("ppq", it) }
            )
            ClinicalInputField(
                label = "Jitter:DDP",
                placeholder = "e.g. 0.00498",
                unit = "ratio",
                helper = "Average difference of differences between cycles (3 × RAP)",
                value = formState.ddp,
                error = fieldErrors["ddp"],
                onValueChange = { onFieldChange("ddp", it) }
            )
        }

        // 3. Amplitude Instability / Shimmer
        FeatureGroupCard(
            title = "3. Amplitude Perturbation (Shimmer)",
            subtitle = "Cycle-to-cycle amplitude variations of the vocal signal",
            icon = Icons.Default.Waves
        ) {
            ClinicalInputField(
                label = "MDVP:Shimmer",
                placeholder = "e.g. 0.01098",
                unit = "ratio",
                helper = "MDVP local shimmer",
                value = formState.shimmer,
                error = fieldErrors["shimmer"],
                onValueChange = { onFieldChange("shimmer", it) }
            )
            ClinicalInputField(
                label = "MDVP:Shimmer(dB)",
                placeholder = "e.g. 0.097",
                unit = "dB",
                helper = "MDVP shimmer in decibels",
                value = formState.shimmerDb,
                error = fieldErrors["shimmerDb"],
                onValueChange = { onFieldChange("shimmerDb", it) }
            )
            ClinicalInputField(
                label = "Shimmer:APQ3",
                placeholder = "e.g. 0.00563",
                unit = "ratio",
                helper = "Three-point amplitude perturbation quotient",
                value = formState.apq3,
                error = fieldErrors["apq3"],
                onValueChange = { onFieldChange("apq3", it) }
            )
            ClinicalInputField(
                label = "Shimmer:APQ5",
                placeholder = "e.g. 0.00680",
                unit = "ratio",
                helper = "Five-point amplitude perturbation quotient",
                value = formState.apq5,
                error = fieldErrors["apq5"],
                onValueChange = { onFieldChange("apq5", it) }
            )
            ClinicalInputField(
                label = "MDVP:APQ",
                placeholder = "e.g. 0.00802",
                unit = "ratio",
                helper = "11-point amplitude perturbation quotient",
                value = formState.apq,
                error = fieldErrors["apq"],
                onValueChange = { onFieldChange("apq", it) }
            )
            ClinicalInputField(
                label = "Shimmer:DDA",
                placeholder = "e.g. 0.01689",
                unit = "ratio",
                helper = "Average difference between consecutive amplitudes (3 × APQ3)",
                value = formState.dda,
                error = fieldErrors["dda"],
                onValueChange = { onFieldChange("dda", it) }
            )
        }

        // 4. Harmonics & Noise Ratios
        FeatureGroupCard(
            title = "4. Signal-to-Noise & Harmonic Ratios",
            subtitle = "Harmonic purity versus turbulent airflow noise (vocal hoarseness)",
            icon = Icons.Default.GraphicEq
        ) {
            ClinicalInputField(
                label = "NHR",
                placeholder = "e.g. 0.00339",
                unit = "ratio",
                helper = "Noise-to-Harmonics ratio (turbulent glottal noise)",
                value = formState.nhr,
                error = fieldErrors["nhr"],
                onValueChange = { onFieldChange("nhr", it) }
            )
            ClinicalInputField(
                label = "HNR",
                placeholder = "e.g. 26.775",
                unit = "dB",
                helper = "Harmonics-to-Noise ratio (>20 dB normal)",
                value = formState.hnr,
                error = fieldErrors["hnr"],
                onValueChange = { onFieldChange("hnr", it) }
            )
        }

        // 5. Nonlinear Dynamic Measures
        FeatureGroupCard(
            title = "5. Nonlinear Dynamic Complexity Measures",
            subtitle = "Fractal scaling, chaotic dynamics, and pitch period entropy",
            icon = Icons.Default.AutoAwesome
        ) {
            ClinicalInputField(
                label = "RPDE",
                placeholder = "e.g. 0.4222",
                unit = "entropy",
                helper = "Recurrence period density entropy (0.0 to 1.0)",
                value = formState.rpde,
                error = fieldErrors["rpde"],
                onValueChange = { onFieldChange("rpde", it) }
            )
            ClinicalInputField(
                label = "DFA",
                placeholder = "e.g. 0.7413",
                unit = "exponent",
                helper = "Detrended fluctuation analysis fractal exponent",
                value = formState.dfa,
                error = fieldErrors["dfa"],
                onValueChange = { onFieldChange("dfa", it) }
            )
            ClinicalInputField(
                label = "spread1",
                placeholder = "e.g. -7.348",
                unit = "nonlinear",
                helper = "Nonlinear fundamental frequency variation parameter",
                value = formState.spread1,
                error = fieldErrors["spread1"],
                onValueChange = { onFieldChange("spread1", it) }
            )
            ClinicalInputField(
                label = "spread2",
                placeholder = "e.g. 0.177",
                unit = "nonlinear",
                helper = "Nonlinear fundamental frequency variation parameter",
                value = formState.spread2,
                error = fieldErrors["spread2"],
                onValueChange = { onFieldChange("spread2", it) }
            )
            ClinicalInputField(
                label = "D2",
                placeholder = "e.g. 1.743",
                unit = "dimension",
                helper = "Correlation dimension (complexity of vocal attractor)",
                value = formState.d2,
                error = fieldErrors["d2"],
                onValueChange = { onFieldChange("d2", it) }
            )
            ClinicalInputField(
                label = "PPE",
                placeholder = "e.g. 0.0855",
                unit = "entropy",
                helper = "Pitch period entropy (critical discriminator)",
                value = formState.ppe,
                error = fieldErrors["ppe"],
                onValueChange = { onFieldChange("ppe", it) }
            )
        }

        // Form Action Buttons (Predict and Clear)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onClear,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .testTag("clear_form_button"),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Clear Form", fontWeight = FontWeight.SemiBold)
            }

            Button(
                onClick = onPredict,
                enabled = !isPredicting,
                modifier = Modifier
                    .weight(1.5f)
                    .height(52.dp)
                    .testTag("predict_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                if (isPredicting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.5.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Classifying...", fontWeight = FontWeight.Bold)
                } else {
                    Icon(
                        imageVector = Icons.Default.Science,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Run AI Prediction", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun FeatureGroupCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable () -> Unit
) {
    var isExpanded by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "Collapse" else "Expand"
                    )
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun ClinicalInputField(
    label: String,
    placeholder: String,
    unit: String,
    helper: String,
    value: String,
    error: String?,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            trailingIcon = {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = unit,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            },
            isError = error != null,
            supportingText = {
                Text(
                    text = error ?: helper,
                    color = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp)
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_${label.replace(":", "_").replace("(", "_").replace(")", "_").replace("%", "pct")}")
        )
    }
}
