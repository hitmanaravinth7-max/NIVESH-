package com.example.data.repository

import com.example.data.local.PredictionDao
import com.example.data.model.PredictionEntity
import kotlinx.coroutines.flow.Flow

class PredictionRepository(private val predictionDao: PredictionDao) {
    val allPredictions: Flow<List<PredictionEntity>> = predictionDao.getAllPredictions()
    val totalCount: Flow<Int> = predictionDao.getPredictionCount()
    val detectedCount: Flow<Int> = predictionDao.getDetectedCount()

    suspend fun insertPrediction(prediction: PredictionEntity): Long {
        return predictionDao.insertPrediction(prediction)
    }

    suspend fun getPredictionById(id: Long): PredictionEntity? {
        return predictionDao.getPredictionById(id)
    }

    suspend fun deletePredictionById(id: Long) {
        predictionDao.deletePredictionById(id)
    }

    suspend fun clearAll() {
        predictionDao.clearAllPredictions()
    }
}
