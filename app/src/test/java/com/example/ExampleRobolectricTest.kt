package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.content.PlacementTestQuestions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
    assertEquals("EnglishMate", appName)
  }

  @Test
  fun `verify 40 placement questions exist and calculate score`() {
    assertEquals(40, PlacementTestQuestions.questions.size)

    // Simulate answering all questions correctly
    val perfectAnswers = PlacementTestQuestions.questions.associate { it.id to it.correctOptionIndex }
    val result = PlacementTestQuestions.calculateAssessment(perfectAnswers)

    assertEquals(100, result.overallScore)
    assertEquals("C2", result.estimatedCefrLevel)
    assertNotNull(result.strongestSkill)
  }
}
