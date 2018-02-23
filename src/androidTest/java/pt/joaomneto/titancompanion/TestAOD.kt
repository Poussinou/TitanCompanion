package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4

import org.junit.runner.RunWith

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.ARMIES_OF_DEATH
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.CREATURE_OF_HAVOC

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestAOD : TestST() {
    override val gamebook = ARMIES_OF_DEATH
}