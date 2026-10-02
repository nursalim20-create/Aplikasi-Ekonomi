package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.db.DefaultEconomicsData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("SMA Negeri 1 Belitang II", appName)
    }

    @Test
    fun `default curriculum data contains 4 chapters and 8 topics`() {
        assertEquals(8, DefaultEconomicsData.initialTopics.size)
        val chapters = DefaultEconomicsData.initialTopics.map { it.chapterNumber }.distinct()
        assertEquals(listOf(1, 2, 3, 4), chapters)
    }

    @Test
    fun `all 8 topics have 10 multiple choice questions each`() {
        assertEquals(80, DefaultEconomicsData.initialQuestions.size)
        for (topicId in 1..8) {
            val count = DefaultEconomicsData.initialQuestions.count { it.topicId == topicId }
            assertEquals(10, count)
        }
    }
}
