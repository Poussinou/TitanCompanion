package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.TRIAL_OF_CHAMPIONS;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestTOC extends TestST {

    @Override
    protected FightingFantasyGamebook getGamebook() {
        return TRIAL_OF_CHAMPIONS;
    }


}