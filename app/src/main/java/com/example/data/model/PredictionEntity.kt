package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "predictions")
data class PredictionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val resultStatus: String,
    val isParkinsonsDetected: Boolean,
    val confidence: Int,
    val probability: Double,
    val explanation: String,
    val recommendation: String,
    val keyAcousticFindings: String,
    
    // Stored 22 features
    val fo: Double,
    val fhi: Double,
    val flo: Double,
    val jitterPercent: Double,
    val jitterAbs: Double,
    val rap: Double,
    val ppq: Double,
    val ddp: Double,
    val shimmer: Double,
    val shimmerDb: Double,
    val apq3: Double,
    val apq5: Double,
    val apq: Double,
    val dda: Double,
    val nhr: Double,
    val hnr: Double,
    val rpde: Double,
    val dfa: Double,
    val spread1: Double,
    val spread2: Double,
    val d2: Double,
    val ppe: Double
) {
    fun toVoiceFeatures(): VoiceFeatures = VoiceFeatures(
        fo = fo, fhi = fhi, flo = flo,
        jitterPercent = jitterPercent, jitterAbs = jitterAbs, rap = rap, ppq = ppq, ddp = ddp,
        shimmer = shimmer, shimmerDb = shimmerDb, apq3 = apq3, apq5 = apq5, apq = apq, dda = dda,
        nhr = nhr, hnr = hnr, rpde = rpde, dfa = dfa, spread1 = spread1, spread2 = spread2,
        d2 = d2, ppe = ppe
    )
}
