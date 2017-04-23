package pt.joaomneto.titancompanion.adventure.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.Adventure;
import android.os.Bundle;
import android.view.Menu;

public class TWOFMAdventure extends Adventure {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);

			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.adventure, menu);
		return true;
	}
	
	@Override
	public void storeAdventureSpecificValuesInFile(BufferedWriter bw)
			throws IOException {
		bw.write("standardPotion=" + getStandardPotion() + "\n");
		bw.write("standardPotionValue=" + getStandardPotionValue()
				+ "\n");
		bw.write("gold=" + getGold() + "\n");
	}

	@Override
	protected void loadAdventureSpecificValuesFromFile() {
		setStandardPotion(Integer.valueOf(getSavedGame()
				.getProperty("standardPotion")));
		setStandardPotionValue(Integer.valueOf(getSavedGame()
				.getProperty("standardPotionValue")));
		setGold(Integer.valueOf(getSavedGame().getProperty("gold")));
		
	}
	

}
