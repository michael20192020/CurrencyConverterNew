package com.hansoft.currencyconverter

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Rule @JvmField
    var rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.hansoft.currencyconverter", appContext.packageName)
    }



    @Test
    @Throws(InterruptedException::class)
    fun testInputAmount() {
        Thread.sleep(2000)
        onView(withId(R.id.editTextAmount)).perform(ViewActions.clearText())
        onView(withId(R.id.editTextAmount))
            .perform(ViewActions.typeText("30"), ViewActions.closeSoftKeyboard())
        Thread.sleep(2000)
    }

    @Test
    @Throws(InterruptedException::class)
    fun testChangeCurrency() {
        Thread.sleep(2000)

        onView(withId(R.id.spinnerCurrency)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("CNH"))).perform(click())
        onView(withId(R.id.spinnerCurrency)).check(matches(withSpinnerText(containsString("CNH"))))

        Thread.sleep(2000)

    }


    @Test
    @Throws(InterruptedException::class)
    fun testConvertButtonWithUSD() {
        Thread.sleep(2000)
        onView(withId(R.id.editTextAmount)).perform(ViewActions.clearText())
        onView(withId(R.id.editTextAmount))
                .perform(ViewActions.typeText("30"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.buttonConvert))
                .perform(ViewActions.click())
        Thread.sleep(2000)

    }

    @Test
    @Throws(InterruptedException::class)
    fun testConvertButtonWithCNH() {
        Thread.sleep(2000)

        onView(withId(R.id.spinnerCurrency)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("CNH"))).perform(click())
        onView(withId(R.id.spinnerCurrency)).check(matches(withSpinnerText(containsString("CNH"))))

        onView(withId(R.id.editTextAmount)).perform(ViewActions.clearText())
        onView(withId(R.id.editTextAmount))
                .perform(ViewActions.typeText("30"), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.buttonConvert))
                .perform(ViewActions.click())
        Thread.sleep(2000)

    }
}