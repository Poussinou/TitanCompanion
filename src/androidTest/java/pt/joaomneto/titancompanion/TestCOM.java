package pt.joaomneto.titancompanion;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import pt.joaomneto.titancompanion.consts.FightingFantasyGamebook;

import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.CHASMS_OF_MALICE;
import static pt.joaomneto.titancompanion.consts.FightingFantasyGamebook.SCORPION_SWAMP;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestCOM extends TestST{

    @Override
    protected FightingFantasyGamebook getGamebook(){
        return CHASMS_OF_MALICE;
    }



}