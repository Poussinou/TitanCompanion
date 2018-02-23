package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook
import pt.joaomneto.titancompanion.util.LocaleHelper

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withSpinnerText
import org.hamcrest.Matchers.containsString
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_FOREST_OF_DOOM

@LargeTest
@RunWith(AndroidJUnit4::class)
open class TestTFOD : TestTWOFM() {

    override val gamebook = THE_FOREST_OF_DOOM

    override fun assertCorrectPotionDosage() {
        val language = LocaleHelper.getLanguage(mActivityTestRule.activity)
        val string = getString(if ("fr" == language) R.string.potionTwoDoses else R.string.potionOneDose)
        onView(withId(R.id.potionDosesSpinner)).check(matches(withSpinnerText(containsString(string))))
    }

}
