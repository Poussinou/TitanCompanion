package pt.joaomneto.titancompanion

import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.BENEATH_NIGHTMARE_CASTLE
import pt.joaomneto.titancompanion.TestST

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestBNC : TestST() {

    override val gamebook = BENEATH_NIGHTMARE_CASTLE
}
