package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.auth.AuthManager
import com.example.data.auth.UserSession
import com.example.data.local.AppDatabase
import com.example.data.model.PredictionEntity
import com.example.data.model.VoiceFeatures
import com.example.data.repository.PredictionRepository
import com.example.ml.ClassificationOutput
import com.example.ml.ParkinsonMLClassifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppScreen {
    LOGIN,
    DASHBOARD
}

enum class DashboardSection {
    OVERVIEW,
    SCREENING,
    RESULT,
    HISTORY,
    ABOUT,
    HOW_IT_WORKS
}

data class FormFields(
    val fo: String = "",
    val fhi: String = "",
    val flo: String = "",
    val jitterPercent: String = "",
    val jitterAbs: String = "",
    val rap: String = "",
    val ppq: String = "",
    val ddp: String = "",
    val shimmer: String = "",
    val shimmerDb: String = "",
    val apq3: String = "",
    val apq5: String = "",
    val apq: String = "",
    val dda: String = "",
    val nhr: String = "",
    val hnr: String = "",
    val rpde: String = "",
    val dfa: String = "",
    val spread1: String = "",
    val spread2: String = "",
    val d2: String = "",
    val ppe: String = ""
)

class ParkinsonViewModel(application: Application) : AndroidViewModel(application) {

    private val authManager = AuthManager(application)
    private val database = AppDatabase.getDatabase(application)
    private val repository = PredictionRepository(database.predictionDao())

    val session: StateFlow<UserSession> = authManager.sessionState

    private val _currentScreen = MutableStateFlow(
        if (authManager.sessionState.value.isLoggedIn) AppScreen.DASHBOARD else AppScreen.LOGIN
    )
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _currentSection = MutableStateFlow(DashboardSection.OVERVIEW)
    val currentSection: StateFlow<DashboardSection> = _currentSection.asStateFlow()

    private val _formState = MutableStateFlow(FormFields())
    val formState: StateFlow<FormFields> = _formState.asStateFlow()

    private val _formErrorMessage = MutableStateFlow<String?>(null)
    val formErrorMessage: StateFlow<String?> = _formErrorMessage.asStateFlow()

    private val _fieldErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val fieldErrors: StateFlow<Map<String, String>> = _fieldErrors.asStateFlow()

    private val _isPredicting = MutableStateFlow(false)
    val isPredicting: StateFlow<Boolean> = _isPredicting.asStateFlow()

    private val _currentResult = MutableStateFlow<PredictionEntity?>(null)
    val currentResult: StateFlow<PredictionEntity?> = _currentResult.asStateFlow()

    val historyList: StateFlow<List<PredictionEntity>> = repository.allPredictions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalCount: StateFlow<Int> = repository.totalCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val detectedCount: StateFlow<Int> = repository.detectedCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Prepopulate healthy sample for academic demonstration
    fun loadSample(isHealthy: Boolean) {
        val sample = if (isHealthy) VoiceFeatures.HealthySample else VoiceFeatures.ParkinsonsSample
        _formState.value = FormFields(
            fo = sample.fo.toString(),
            fhi = sample.fhi.toString(),
            flo = sample.flo.toString(),
            jitterPercent = sample.jitterPercent.toString(),
            jitterAbs = sample.jitterAbs.toString(),
            rap = sample.rap.toString(),
            ppq = sample.ppq.toString(),
            ddp = sample.ddp.toString(),
            shimmer = sample.shimmer.toString(),
            shimmerDb = sample.shimmerDb.toString(),
            apq3 = sample.apq3.toString(),
            apq5 = sample.apq5.toString(),
            apq = sample.apq.toString(),
            dda = sample.dda.toString(),
            nhr = sample.nhr.toString(),
            hnr = sample.hnr.toString(),
            rpde = sample.rpde.toString(),
            dfa = sample.dfa.toString(),
            spread1 = sample.spread1.toString(),
            spread2 = sample.spread2.toString(),
            d2 = sample.d2.toString(),
            ppe = sample.ppe.toString()
        )
        _fieldErrors.value = emptyMap()
        _formErrorMessage.value = null
    }

    fun updateField(key: String, value: String) {
        val current = _formState.value
        _formState.value = when (key) {
            "fo" -> current.copy(fo = value)
            "fhi" -> current.copy(fhi = value)
            "flo" -> current.copy(flo = value)
            "jitterPercent" -> current.copy(jitterPercent = value)
            "jitterAbs" -> current.copy(jitterAbs = value)
            "rap" -> current.copy(rap = value)
            "ppq" -> current.copy(ppq = value)
            "ddp" -> current.copy(ddp = value)
            "shimmer" -> current.copy(shimmer = value)
            "shimmerDb" -> current.copy(shimmerDb = value)
            "apq3" -> current.copy(apq3 = value)
            "apq5" -> current.copy(apq5 = value)
            "apq" -> current.copy(apq = value)
            "dda" -> current.copy(dda = value)
            "nhr" -> current.copy(nhr = value)
            "hnr" -> current.copy(hnr = value)
            "rpde" -> current.copy(rpde = value)
            "dfa" -> current.copy(dfa = value)
            "spread1" -> current.copy(spread1 = value)
            "spread2" -> current.copy(spread2 = value)
            "d2" -> current.copy(d2 = value)
            "ppe" -> current.copy(ppe = value)
            else -> current
        }
        if (_fieldErrors.value.containsKey(key)) {
            _fieldErrors.value = _fieldErrors.value - key
        }
    }

    fun clearForm() {
        _formState.value = FormFields()
        _fieldErrors.value = emptyMap()
        _formErrorMessage.value = null
    }

    fun predict() {
        val form = _formState.value
        val errors = mutableMapOf<String, String>()

        fun parseDouble(field: String, value: String, min: Double? = null, max: Double? = null): Double? {
            if (value.trim().isEmpty()) {
                errors[field] = "Field is required"
                return null
            }
            val num = value.toDoubleOrNull()
            if (num == null) {
                errors[field] = "Enter a valid number"
                return null
            }
            if (min != null && num < min) {
                errors[field] = "Must be ≥ $min"
                return null
            }
            if (max != null && num > max) {
                errors[field] = "Must be ≤ $max"
                return null
            }
            return num
        }

        val fo = parseDouble("fo", form.fo, 20.0, 600.0)
        val fhi = parseDouble("fhi", form.fhi, 20.0, 800.0)
        val flo = parseDouble("flo", form.flo, 20.0, 600.0)
        val jitterPercent = parseDouble("jitterPercent", form.jitterPercent, 0.0, 1.0)
        val jitterAbs = parseDouble("jitterAbs", form.jitterAbs, 0.0, 0.01)
        val rap = parseDouble("rap", form.rap, 0.0, 1.0)
        val ppq = parseDouble("ppq", form.ppq, 0.0, 1.0)
        val ddp = parseDouble("ddp", form.ddp, 0.0, 1.0)
        val shimmer = parseDouble("shimmer", form.shimmer, 0.0, 1.0)
        val shimmerDb = parseDouble("shimmerDb", form.shimmerDb, 0.0, 10.0)
        val apq3 = parseDouble("apq3", form.apq3, 0.0, 1.0)
        val apq5 = parseDouble("apq5", form.apq5, 0.0, 1.0)
        val apq = parseDouble("apq", form.apq, 0.0, 1.0)
        val dda = parseDouble("dda", form.dda, 0.0, 1.0)
        val nhr = parseDouble("nhr", form.nhr, 0.0, 2.0)
        val hnr = parseDouble("hnr", form.hnr, -10.0, 50.0)
        val rpde = parseDouble("rpde", form.rpde, 0.0, 2.0)
        val dfa = parseDouble("dfa", form.dfa, 0.0, 2.0)
        val spread1 = parseDouble("spread1", form.spread1, -15.0, 5.0)
        val spread2 = parseDouble("spread2", form.spread2, 0.0, 5.0)
        val d2 = parseDouble("d2", form.d2, 0.0, 10.0)
        val ppe = parseDouble("ppe", form.ppe, 0.0, 2.0)

        if (errors.isNotEmpty()) {
            _fieldErrors.value = errors
            _formErrorMessage.value = "Please complete all 22 required clinical features with valid numerical values."
            return
        }

        _fieldErrors.value = emptyMap()
        _formErrorMessage.value = null

        val features = VoiceFeatures(
            fo = fo!!, fhi = fhi!!, flo = flo!!,
            jitterPercent = jitterPercent!!, jitterAbs = jitterAbs!!, rap = rap!!, ppq = ppq!!, ddp = ddp!!,
            shimmer = shimmer!!, shimmerDb = shimmerDb!!, apq3 = apq3!!, apq5 = apq5!!, apq = apq!!, dda = dda!!,
            nhr = nhr!!, hnr = hnr!!, rpde = rpde!!, dfa = dfa!!, spread1 = spread1!!, spread2 = spread2!!,
            d2 = d2!!, ppe = ppe!!
        )

        viewModelScope.launch {
            _isPredicting.value = true
            delay(500) // Brief processing delay for responsive UX
            val output = ParkinsonMLClassifier.classify(features)

            val entity = PredictionEntity(
                resultStatus = output.status,
                isParkinsonsDetected = output.isDetected,
                confidence = output.confidence,
                probability = output.probability,
                explanation = output.explanation,
                recommendation = output.recommendation,
                keyAcousticFindings = output.keyAcousticFindings,
                fo = features.fo, fhi = features.fhi, flo = features.flo,
                jitterPercent = features.jitterPercent, jitterAbs = features.jitterAbs, rap = features.rap,
                ppq = features.ppq, ddp = features.ddp, shimmer = features.shimmer,
                shimmerDb = features.shimmerDb, apq3 = features.apq3, apq5 = features.apq5,
                apq = features.apq, dda = features.dda, nhr = features.nhr, hnr = features.hnr,
                rpde = features.rpde, dfa = features.dfa, spread1 = features.spread1,
                spread2 = features.spread2, d2 = features.d2, ppe = features.ppe
            )

            val id = repository.insertPrediction(entity)
            val savedEntity = entity.copy(id = id)
            _currentResult.value = savedEntity
            _isPredicting.value = false
            _currentSection.value = DashboardSection.RESULT
        }
    }

    fun viewResult(prediction: PredictionEntity) {
        _currentResult.value = prediction
        _currentSection.value = DashboardSection.RESULT
    }

    fun deletePrediction(id: Long) {
        viewModelScope.launch {
            repository.deletePredictionById(id)
            if (_currentResult.value?.id == id) {
                _currentResult.value = null
            }
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            repository.clearAll()
            _currentResult.value = null
        }
    }

    fun setSection(section: DashboardSection) {
        _currentSection.value = section
    }

    fun loginUser(emailOrUsername: String, rememberMe: Boolean) {
        authManager.login(emailOrUsername, rememberMe)
        _currentScreen.value = AppScreen.DASHBOARD
        _currentSection.value = DashboardSection.OVERVIEW
    }

    fun logoutUser() {
        authManager.logout()
        _currentScreen.value = AppScreen.LOGIN
        _currentResult.value = null
    }
}
