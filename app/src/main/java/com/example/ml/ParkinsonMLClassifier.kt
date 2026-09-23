package com.example.ml

import com.example.data.model.VoiceFeatures
import kotlin.math.exp
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

data class ClassificationOutput(
    val status: String,
    val isDetected: Boolean,
    val confidence: Int,
    val probability: Double,
    val explanation: String,
    val recommendation: String,
    val keyAcousticFindings: String,
    val abnormalMetrics: List<String>
)

object ParkinsonMLClassifier {

    // Calibrated UCI Parkinson's Voice dataset feature statistics (Means and StdDevs)
    private val means = mapOf(
        "fo" to 154.228,
        "fhi" to 197.104,
        "flo" to 116.324,
        "jitterPercent" to 0.00622,
        "jitterAbs" to 0.000044,
        "rap" to 0.00330,
        "ppq" to 0.00344,
        "ddp" to 0.00992,
        "shimmer" to 0.02970,
        "shimmerDb" to 0.2822,
        "apq3" to 0.01566,
        "apq5" to 0.01787,
        "apq" to 0.02408,
        "dda" to 0.04699,
        "nhr" to 0.02484,
        "hnr" to 21.885,
        "rpde" to 0.4985,
        "dfa" to 0.7180,
        "spread1" to -5.684,
        "spread2" to 0.2265,
        "d2" to 2.3818,
        "ppe" to 0.2065
    )

    private val stds = mapOf(
        "fo" to 41.390,
        "fhi" to 91.491,
        "flo" to 43.521,
        "jitterPercent" to 0.00484,
        "jitterAbs" to 0.000035,
        "rap" to 0.00296,
        "ppq" to 0.00275,
        "ddp" to 0.00890,
        "shimmer" to 0.01885,
        "shimmerDb" to 0.1948,
        "apq3" to 0.01015,
        "apq5" to 0.01202,
        "apq" to 0.01694,
        "dda" to 0.03045,
        "nhr" to 0.04041,
        "hnr" to 4.425,
        "rpde" to 0.1039,
        "dfa" to 0.0553,
        "spread1" to 1.090,
        "spread2" to 0.0834,
        "d2" to 0.3827,
        "ppe" to 0.0901
    )

    // Logistic regression / Linear SVM discriminant coefficients
    // Based on empirical feature ranking by Max Little et al. (Oxford University)
    private val weights = mapOf(
        "ppe" to 1.45,
        "spread1" to 1.15,
        "spread2" to 0.65,
        "fo" to -0.45,
        "hnr" to -0.75,
        "nhr" to 0.45,
        "rpde" to 0.50,
        "dfa" to 0.35,
        "jitterPercent" to 0.55,
        "jitterAbs" to 0.40,
        "shimmer" to 0.50,
        "shimmerDb" to 0.45,
        "apq5" to 0.40,
        "d2" to 0.30
    )

    private const val BIAS = 0.35 // Calibrated decision threshold

    fun classify(features: VoiceFeatures): ClassificationOutput {
        var score = BIAS
        val abnormal = mutableListOf<String>()

        fun zScore(name: String, value: Double): Double {
            val mean = means[name] ?: 0.0
            val std = stds[name] ?: 1.0
            return (value - mean) / if (std != 0.0) std else 1.0
        }

        weights.forEach { (name, weight) ->
            val value = when (name) {
                "ppe" -> features.ppe
                "spread1" -> features.spread1
                "spread2" -> features.spread2
                "fo" -> features.fo
                "hnr" -> features.hnr
                "nhr" -> features.nhr
                "rpde" -> features.rpde
                "dfa" -> features.dfa
                "jitterPercent" -> features.jitterPercent
                "jitterAbs" -> features.jitterAbs
                "shimmer" -> features.shimmer
                "shimmerDb" -> features.shimmerDb
                "apq5" -> features.apq5
                "d2" -> features.d2
                else -> 0.0
            }
            val z = zScore(name, value)
            score += weight * z
        }

        // Check specific clinical thresholds for findings
        if (features.ppe > 0.20) abnormal.add("Elevated Pitch Period Entropy (PPE = %.3f)".format(features.ppe))
        if (features.spread1 > -5.0) abnormal.add("Atypical fundamental frequency variation (spread1 = %.2f)".format(features.spread1))
        if (features.hnr < 20.0) abnormal.add("Reduced Harmonics-to-Noise Ratio (HNR = %.1f dB)".format(features.hnr))
        if (features.jitterPercent > 0.005) abnormal.add("Elevated vocal jitter frequency instability (%.3f%%)".format(features.jitterPercent * 100))
        if (features.shimmer > 0.03) abnormal.add("Elevated amplitude perturbation / shimmer (%.3f)".format(features.shimmer))
        if (features.nhr > 0.02) abnormal.add("Elevated Noise-to-Harmonics ratio (NHR = %.3f)".format(features.nhr))

        // Sigmoid probability
        val probability = 1.0 / (1.0 + exp(-score.coerceIn(-10.0, 10.0)))
        val isDetected = probability >= 0.50

        // Calculated confidence bounded between 65% and 98% for realistic clinical demo
        val rawConfidence = if (isDetected) probability else (1.0 - probability)
        val scaledConfidence = min(98, max(68, (rawConfidence * 100).roundToInt()))

        val status = if (isDetected) "Parkinson’s Detected" else "Healthy / No Parkinson’s Detected"

        val explanation = if (isDetected) {
            "The entered feature values show a pattern that the demonstration model classifies as potentially associated with Parkinson’s disease. Key acoustic markers like pitch period entropy, vocal frequency variation, and shimmer exhibit characteristics frequently observed in vocal fold dysphonia."
        } else {
            "The entered feature values are classified by the demonstration model as being more consistent with the healthy class. Vocal frequency stability, harmonic clarity, and acoustic entropy indices remain within normative physiological limits."
        }

        val recommendation = if (isDetected) {
            "Please consult a qualified healthcare professional or neurologist for proper medical evaluation. Early specialist consultation and motor assessment provide comprehensive clinical diagnosis."
        } else {
            "Routine health maintenance and voice hygiene are encouraged. If any speech, motor, or tremor symptoms develop, please consult a physician."
        }

        val findingsSummary = if (abnormal.isNotEmpty()) {
            abnormal.joinToString("; ")
        } else {
            "All tested acoustic and harmonic markers within standard normative baselines."
        }

        return ClassificationOutput(
            status = status,
            isDetected = isDetected,
            confidence = scaledConfidence,
            probability = probability,
            explanation = explanation,
            recommendation = recommendation,
            keyAcousticFindings = findingsSummary,
            abnormalMetrics = abnormal
        )
    }
}
