package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.SalonAssetProvider
import com.example.data.local.AppDatabase
import com.example.data.model.ChairSession
import com.example.ui.viewmodel.KimeraSessionViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
        assertEquals("Kimera AI Style Preview", appName)
    }

    @Test
    fun `verify style variations count and metadata`() {
        val variations = SalonAssetProvider.defaultVariations
        assertEquals(3, variations.size)
        assertTrue(variations.any { it.title.contains("French Crop", ignoreCase = true) })
        assertTrue(variations.any { it.title.contains("Drop Taper", ignoreCase = true) })
        assertTrue(variations.any { it.title.contains("Scissor", ignoreCase = true) })
    }

    @Test
    fun `verify pilot metrics calculation with mock data`() {
        val app = ApplicationProvider.getApplicationContext<android.app.Application>()
        val viewModel = KimeraSessionViewModel(app)

        val sessions = listOf(
            ChairSession(
                stage = "COMPLETED",
                photoConsentGranted = true,
                outcomeMatchStatus = "MATCHED",
                outcomeRealismRating = 5,
                barberAdoptedTool = true,
                renderSpeedMetTarget = true
            ),
            ChairSession(
                stage = "COMPLETED",
                photoConsentGranted = true,
                outcomeMatchStatus = "MATCHED",
                outcomeRealismRating = 4,
                barberAdoptedTool = true,
                renderSpeedMetTarget = true
            ),
            ChairSession(
                stage = "COMPLETED",
                photoConsentGranted = true,
                outcomeMatchStatus = "NOT_MATCHED",
                outcomeRealismRating = 3,
                barberAdoptedTool = false,
                renderSpeedMetTarget = true
            )
        )

        val metrics = viewModel.computePilotMetrics(sessions)
        // 2 out of 3 matched = 66%
        assertEquals(66, metrics.matchRatePercent)
        // 2 out of 3 barber adopted = 66%
        assertEquals(66, metrics.barberAdoptionPercent)
        // 3 out of 3 consent = 100%
        assertEquals(100, metrics.consentRatePercent)
        // 3 out of 3 speed met = 100%
        assertEquals(100, metrics.speedCompliancePercent)
        // 2 out of 3 rating >= 4 = 66%
        assertEquals(66, metrics.realismRatePercent)
    }

    @Test
    fun `verify database insert and read`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AppDatabase.getInstance(context)
        val dao = db.sessionDao()

        val session = ChairSession(
            clientName = "Vikram Test",
            barberName = "Rahul",
            pricingModel = "fee_100",
            stage = "START"
        )
        val id = dao.insertSession(session)
        assertTrue(id > 0)

        val retrieved = dao.getSessionById(id)
        assertNotNull(retrieved)
        assertEquals("Vikram Test", retrieved?.clientName)
    }
}
