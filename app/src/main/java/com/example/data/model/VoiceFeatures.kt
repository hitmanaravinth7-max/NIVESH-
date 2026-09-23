package com.example.data.model

data class VoiceFeatures(
    val fo: Double,          // MDVP:Fo(Hz) - Average vocal fundamental frequency
    val fhi: Double,         // MDVP:Fhi(Hz) - Maximum vocal fundamental frequency
    val flo: Double,         // MDVP:Flo(Hz) - Minimum vocal fundamental frequency
    val jitterPercent: Double,// MDVP:Jitter(%)
    val jitterAbs: Double,   // MDVP:Jitter(Abs)
    val rap: Double,         // MDVP:RAP
    val ppq: Double,         // MDVP:PPQ
    val ddp: Double,         // Jitter:DDP
    val shimmer: Double,     // MDVP:Shimmer
    val shimmerDb: Double,   // MDVP:Shimmer(dB)
    val apq3: Double,        // Shimmer:APQ3
    val apq5: Double,        // Shimmer:APQ5
    val apq: Double,         // MDVP:APQ
    val dda: Double,         // Shimmer:DDA
    val nhr: Double,         // NHR - Noise to Harmonic ratio
    val hnr: Double,         // HNR - Harmonic to Noise ratio
    val rpde: Double,        // RPDE - Recurrence Period Density Entropy
    val dfa: Double,         // DFA - Detrended Fluctuation Analysis
    val spread1: Double,     // spread1 - Nonlinear fundamental frequency variation
    val spread2: Double,     // spread2
    val d2: Double,          // D2 - Correlation dimension
    val ppe: Double          // PPE - Pitch Period Entropy
) {
    companion object {
        // Standard healthy benchmark sample from UCI dataset (Subject healthy)
        val HealthySample = VoiceFeatures(
            fo = 197.076,
            fhi = 206.896,
            flo = 192.055,
            jitterPercent = 0.00289,
            jitterAbs = 0.00001,
            rap = 0.00166,
            ppq = 0.00168,
            ddp = 0.00498,
            shimmer = 0.01098,
            shimmerDb = 0.097,
            apq3 = 0.00563,
            apq5 = 0.00680,
            apq = 0.00802,
            dda = 0.01689,
            nhr = 0.00339,
            hnr = 26.775,
            rpde = 0.422229,
            dfa = 0.741367,
            spread1 = -7.348300,
            spread2 = 0.177551,
            d2 = 1.743867,
            ppe = 0.085569
        )

        // Standard Parkinson's detected benchmark sample from UCI dataset (Subject with confirmed vocal tremor)
        val ParkinsonsSample = VoiceFeatures(
            fo = 119.992,
            fhi = 157.302,
            flo = 74.997,
            jitterPercent = 0.00784,
            jitterAbs = 0.00007,
            rap = 0.00370,
            ppq = 0.00554,
            ddp = 0.01109,
            shimmer = 0.04374,
            shimmerDb = 0.426,
            apq3 = 0.02182,
            apq5 = 0.03130,
            apq = 0.02971,
            dda = 0.06545,
            nhr = 0.02211,
            hnr = 21.033,
            rpde = 0.414783,
            dfa = 0.815285,
            spread1 = -4.813031,
            spread2 = 0.266482,
            d2 = 2.301442,
            ppe = 0.284654
        )
    }
}
