package pt.joaomneto.titancompanion

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withSpinnerText
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.containsString
import org.junit.Test
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.adventure.Adventure
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.THE_WARLOCK_OF_FIRETOP_MOUNTAIN

@LargeTest
@RunWith(AndroidJUnit4::class)
open class TestTWOFM : TCBaseTest() {

    override val gamebook = THE_WARLOCK_OF_FIRETOP_MOUNTAIN

    @Test
    fun testSuccessfulCreation() {
        performStartAdventure()
        performFillSavegameName()
        performVitalStatisticsRoll()
        performSwipeLeft()
        performChoosePotion()
        assertCorrectPotionDosage()
        performSaveAdventureFromCreationScreen()
        assertAdventureLoaded()
        testVitalStatisticsFragment()
    }

    override fun testVitalStatisticsFragment() {
        super.testVitalStatisticsFragment()
        testProvisions(activityInstance as Adventure)
        testPotion(activityInstance as Adventure)
    }

    private fun testProvisions(adventure: Adventure) {
        testProvisionStat(adventure)
        performClickOnButton(R.id.minusStaminaButton, 4)
        performClickOnButton(R.id.plusProvisionsButton, 2)
        performClickOnButton(R.id.buttonConsumeProvisions)
        performClickOnButton(android.R.id.button2)
        //TODO
    }

    private fun testPotion(adventure: Adventure) {
        performClickOnButton(R.id.minusStaminaButton)
        performClickOnButton(R.id.minusSkillButton)
        performClickOnButton(R.id.minusLuckButton)
        performClickOnButton(R.id.usePotionButton)
        performClickOnButton(android.R.id.button2)
        //TODO
    }



    protected open fun assertCorrectPotionDosage() {
        onView(withId(R.id.potionDosesSpinner)).check(matches(withSpinnerText(containsString(getString(R.string.potionTwoDoses)))))
    }

    @Test
    fun testCreationWithoutPotion() {

        performStartAdventure()
        performFillSavegameName()
        performVitalStatisticsRoll()
        performSwipeLeft()
        assertCorrectPotionDosage()
        performSaveAdventureFromCreationScreen()
        assertInvalidAdventureCreation()
    }

    @Test
    fun testCreationWithoutRoll() {

        performStartAdventure()
        performFillSavegameName()
        performSwipeLeft()
        performChoosePotion()
        assertCorrectPotionDosage()
        performSaveAdventureFromCreationScreen()
        assertInvalidAdventureCreation()
    }

    @Test
    fun testCreationWithoutSavegame() {

        performStartAdventure()
        performVitalStatisticsRoll()
        performSwipeLeft()
        performChoosePotion()
        assertCorrectPotionDosage()
        performSaveAdventureFromCreationScreen()
        assertInvalidAdventureCreation()
    }

    fun testProvisionStat(adventure: Adventure){
        testIncrementalStat(
            adventure,
            R.id.minusProvisionsButton,
            R.id.plusProvisionsButton,
            R.id.provisionsValue,
            Adventure::provisions
        )
    }
}
