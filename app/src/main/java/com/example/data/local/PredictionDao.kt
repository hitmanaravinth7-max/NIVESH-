package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.PredictionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PredictionDao {
    @Query("SELECT * FROM predictions ORDER BY timestamp DESC")
    fun getAllPredictions(): Flow<List<PredictionEntity>>

    @Query("SELECT * FROM predictions WHERE id = :id LIMIT 1")
    suspend fun getPredictionById(id: Long): PredictionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrediction(prediction: PredictionEntity): Long

    @Query("DELETE FROM predictions WHERE id = :id")
    suspend fun deletePredictionById(id: Long)

    @Query("DELETE FROM predictions")
    suspend fun clearAllPredictions()

    @Query("SELECT COUNT(*) FROM predictions")
    fun getPredictionCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM predictions WHERE isParkinsonsDetected = 1")
    fun getDetectedCount(): Flow<Int>
}
