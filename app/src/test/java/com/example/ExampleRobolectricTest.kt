package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.VoiceFeatures
import com.example.ml.ParkinsonMLClassifier
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Parkinson's Detection", appName)
  }

  @Test
  fun `test healthy voice sample classification`() {
    val output = ParkinsonMLClassifier.classify(VoiceFeatures.HealthySample)
    assertFalse("Healthy sample should not be flagged as detected", output.isDetected)
    assertEquals("Healthy / No Parkinson’s Detected", output.status)
    assertTrue("Confidence should be above 70%", output.confidence >= 70)
  }

  @Test
  fun `test parkinsons voice sample classification`() {
    val output = ParkinsonMLClassifier.classify(VoiceFeatures.ParkinsonsSample)
    assertTrue("Parkinson's sample should be flagged as detected", output.isDetected)
    assertEquals("Parkinson’s Detected", output.status)
    assertTrue("Confidence should be above 70%", output.confidence >= 70)
  }
}
