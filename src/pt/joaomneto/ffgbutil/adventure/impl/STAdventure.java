package pt.joaomneto.ffgbutil.adventure.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import android.os.Bundle;
import android.view.Menu;

public class STAdventure extends Adventure {

	protected static final int FRAGMENT_VITAL_STATS = 0;
	protected static final int FRAGMENT_CREW_STATS = 1;
	protected static final int FRAGMENT_PHASER_COMBAT = 2;
	protected static final int FRAGMENT_SHIP_COMBAT = 3;
	protected static final int FRAGMENT_NOTES = 4;

	private int initialScienceOfficerSkill = -1;
	private int initialMedicalOfficerSkill = -1;
	private int initialEngineeringOfficerSkill = -1;
	private int initialSecurityOfficerSkill = -1;
	private int initialSecurityGuard1Skill = -1;
	private int initialSecurityGuard2Skill = -1;
	private int initialShipWeapons = -1;

	private int initialScienceOfficerStamina = -1;
	private int initialMedicalOfficerStamina = -1;
	private int initialEngineeringOfficerStamina = -1;
	private int initialSecurityOfficerStamina = -1;
	private int initialSecurityGuard1Stamina = -1;
	private int initialSecurityGuard2Stamina = -1;
	private int initialShipShields = -1;

	private int currentScienceOfficerSkill = -1;
	private int currentMedicalOfficerSkill = -1;
	private int currentEngineeringOfficerSkill = -1;
	private int currentSecurityOfficerSkill = -1;
	private int currentSecurityGuard1Skill = -1;
	private int currentSecurityGuard2Skill = -1;
	private int currentShipWeapons = -1;

	private int currentScienceOfficerStamina = -1;
	private int currentMedicalOfficerStamina = -1;
	private int currentEngineeringOfficerStamina = -1;
	private int currentSecurityOfficerStamina = -1;
	private int currentSecurityGuard1Stamina = -1;
	private int currentSecurityGuard2Stamina = -1;
	private int currentShipShields = -1;

	private boolean landingPartyScienceOfficer = false;
	private boolean landingPartyMedicalOfficer = false;
	private boolean landingPartyEngineeringOfficer = false;
	private boolean landingPartySecurityOfficer = false;
	private boolean landingPartySecurityGuard1 = false;
	private boolean landingPartySecurityGuard2 = false;

	private boolean deadScienceOfficer = false;
	private boolean deadMedicalOfficer = false;
	private boolean deadEngineeringOfficer = false;
	private boolean deadSecurityOfficer = false;
	private boolean deadSecurityGuard1 = false;
	private boolean deadSecurityGuard2 = false;

	public STAdventure() {
		super();
		fragmentConfiguration.clear();
		fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats,
				"pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureVitalStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_CREW_STATS, new AdventureFragmentRunner(R.string.shipCrewStats,
				"pt.joaomneto.ffgbutil.adventure.impl.fragments.st.STCrewStatsFragment"));
		fragmentConfiguration.put(FRAGMENT_PHASER_COMBAT, new AdventureFragmentRunner(R.string.combat,
				"pt.joaomneto.ffgbutil.adventure.impl.fragments.st.STCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_SHIP_COMBAT, new AdventureFragmentRunner(R.string.shipCombat,
				"pt.joaomneto.ffgbutil.adventure.impl.fragments.st.STStarshipCombatFragment"));
		fragmentConfiguration.put(FRAGMENT_NOTES, new AdventureFragmentRunner(R.string.notes,
				"pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureNotesFragment"));
	}

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
		getMenuInflater().inflate(R.menu.adventure, menu);
		return true;
	}

	@Override
	public void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException {

		bw.write("scienceOfficerSkill=" + currentScienceOfficerSkill + "\n");
		bw.write("scienceOfficerStamina=" + currentScienceOfficerStamina + "\n");
		bw.write("medicalOfficerSkill=" + currentMedicalOfficerSkill + "\n");
		bw.write("medicalOfficerStamina=" + currentMedicalOfficerStamina + "\n");
		bw.write("engineeringOfficerSkill=" + currentEngineeringOfficerSkill + "\n");
		bw.write("engineeringOfficerStamina=" + currentEngineeringOfficerStamina + "\n");
		bw.write("securityOfficerSkill=" + currentSecurityOfficerSkill + "\n");
		bw.write("securityOfficerStamina=" + currentSecurityOfficerStamina + "\n");
		bw.write("securityGuard1Skill=" + currentSecurityGuard1Skill + "\n");
		bw.write("securityGuard1Stamina=" + currentSecurityGuard1Stamina + "\n");
		bw.write("securityGuard2Skill=" + currentSecurityGuard2Skill + "\n");
		bw.write("securityGuard2Stamina=" + currentSecurityGuard2Stamina + "\n");
		bw.write("shipWeapons=" + currentShipWeapons + "\n");
		bw.write("shipShields=" + currentShipShields + "\n");

		bw.write("scienceOfficerInitialSkill=" + initialScienceOfficerSkill + "\n");
		bw.write("scienceOfficerInitialStamina=" + initialScienceOfficerStamina + "\n");
		bw.write("medicalOfficerInitialSkill=" + initialMedicalOfficerSkill + "\n");
		bw.write("medicalOfficerInitialStamina=" + initialMedicalOfficerStamina + "\n");
		bw.write("engineeringOfficerInitialSkill=" + initialEngineeringOfficerSkill + "\n");
		bw.write("engineeringOfficerInitialStamina=" + initialEngineeringOfficerStamina + "\n");
		bw.write("securityOfficerInitialSkill=" + initialSecurityOfficerSkill + "\n");
		bw.write("securityOfficerInitialStamina=" + initialSecurityOfficerStamina + "\n");
		bw.write("securityGuard1InitialSkill=" + initialSecurityGuard1Skill + "\n");
		bw.write("securityGuard1InitialStamina=" + initialSecurityGuard1Stamina + "\n");
		bw.write("securityGuard2InitialSkill=" + initialSecurityGuard2Skill + "\n");
		bw.write("securityGuard2InitialStamina=" + initialSecurityGuard2Stamina + "\n");
		bw.write("shipInitialWeapons=" + initialShipWeapons + "\n");
		bw.write("shipInitialShields=" + initialShipShields + "\n");

		bw.write("landingPartyScienceOfficer=" + landingPartyScienceOfficer + "\n");
		bw.write("landingPartyMedicalOfficer=" + landingPartyMedicalOfficer + "\n");
		bw.write("landingPartyEngineeringOfficer=" + landingPartyEngineeringOfficer + "\n");
		bw.write("landingPartySecurityOfficer=" + landingPartySecurityOfficer + "\n");
		bw.write("landingPartySecurityGuard1=" + landingPartySecurityGuard1 + "\n");
		bw.write("landingPartySecurityGuard2=" + landingPartySecurityGuard2 + "\n");

		bw.write("deadPartyScienceOfficer=" + deadScienceOfficer + "\n");
		bw.write("deadMedicalOfficer=" + deadMedicalOfficer + "\n");
		bw.write("deadEngineeringOfficer=" + deadEngineeringOfficer + "\n");
		bw.write("deadSecurityOfficer=" + deadSecurityOfficer + "\n");
		bw.write("deadSecurityGuard1=" + deadSecurityGuard1 + "\n");
		bw.write("deadSecurityGuard2=" + deadSecurityGuard2 + "\n");

	}

	public int getInitialScienceOfficerSkill() {
		return initialScienceOfficerSkill;
	}

	public int getInitialMedicalOfficerSkill() {
		return initialMedicalOfficerSkill;
	}

	public int getInitialEngineeringOfficerSkill() {
		return initialEngineeringOfficerSkill;
	}

	public int getInitialSecurityOfficerSkill() {
		return initialSecurityOfficerSkill;
	}

	public int getInitialSecurityGuard1Skill() {
		return initialSecurityGuard1Skill;
	}

	public int getInitialSecurityGuard2Skill() {
		return initialSecurityGuard2Skill;
	}

	public int getInitialShipWeapons() {
		return initialShipWeapons;
	}

	public int getInitialScienceOfficerStamina() {
		return initialScienceOfficerStamina;
	}

	public int getInitialMedicalOfficerStamina() {
		return initialMedicalOfficerStamina;
	}

	public int getInitialEngineeringOfficerStamina() {
		return initialEngineeringOfficerStamina;
	}

	public int getInitialSecurityOfficerStamina() {
		return initialSecurityOfficerStamina;
	}

	public int getInitialSecurityGuard1Stamina() {
		return initialSecurityGuard1Stamina;
	}

	public int getInitialSecurityGuard2Stamina() {
		return initialSecurityGuard2Stamina;
	}

	public int getInitialShipShields() {
		return initialShipShields;
	}

	public int getCurrentScienceOfficerSkill() {
		return currentScienceOfficerSkill;
	}

	public int getCurrentMedicalOfficerSkill() {
		return currentMedicalOfficerSkill;
	}

	public int getCurrentEngineeringOfficerSkill() {
		return currentEngineeringOfficerSkill;
	}

	public int getCurrentSecurityOfficerSkill() {
		return currentSecurityOfficerSkill;
	}

	public int getCurrentSecurityGuard1Skill() {
		return currentSecurityGuard1Skill;
	}

	public int getCurrentSecurityGuard2Skill() {
		return currentSecurityGuard2Skill;
	}

	public int getCurrentShipWeapons() {
		return currentShipWeapons;
	}

	public int getCurrentScienceOfficerStamina() {
		return currentScienceOfficerStamina;
	}

	public int getCurrentMedicalOfficerStamina() {
		return currentMedicalOfficerStamina;
	}

	public int getCurrentEngineeringOfficerStamina() {
		return currentEngineeringOfficerStamina;
	}

	public int getCurrentSecurityOfficerStamina() {
		return currentSecurityOfficerStamina;
	}

	public int getCurrentSecurityGuard1Stamina() {
		return currentSecurityGuard1Stamina;
	}

	public int getCurrentSecurityGuard2Stamina() {
		return currentSecurityGuard2Stamina;
	}

	public int getCurrentShipShields() {
		return currentShipShields;
	}

	public void setInitialScienceOfficerSkill(int initialScienceOfficerSkill) {
		this.initialScienceOfficerSkill = initialScienceOfficerSkill;
	}

	public void setInitialMedicalOfficerSkill(int initialMedicalOfficerSkill) {
		this.initialMedicalOfficerSkill = initialMedicalOfficerSkill;
	}

	public void setInitialEngineeringOfficerSkill(int initialEngineeringOfficerSkill) {
		this.initialEngineeringOfficerSkill = initialEngineeringOfficerSkill;
	}

	public void setInitialSecurityOfficerSkill(int initialSecurityOfficerSkill) {
		this.initialSecurityOfficerSkill = initialSecurityOfficerSkill;
	}

	public void setInitialSecurityGuard1Skill(int initialSecurityGuard1Skill) {
		this.initialSecurityGuard1Skill = initialSecurityGuard1Skill;
	}

	public void setInitialSecurityGuard2Skill(int initialSecurityGuard2Skill) {
		this.initialSecurityGuard2Skill = initialSecurityGuard2Skill;
	}

	public void setInitialShipWeapons(int initialShipWeapons) {
		this.initialShipWeapons = initialShipWeapons;
	}

	public void setInitialScienceOfficerStamina(int initialScienceOfficerStamina) {
		this.initialScienceOfficerStamina = initialScienceOfficerStamina;
	}

	public void setInitialMedicalOfficerStamina(int initialMedicalOfficerStamina) {
		this.initialMedicalOfficerStamina = initialMedicalOfficerStamina;
	}

	public void setInitialEngineeringOfficerStamina(int initialEngineeringOfficerStamina) {
		this.initialEngineeringOfficerStamina = initialEngineeringOfficerStamina;
	}

	public void setInitialSecurityOfficerStamina(int initialSecurityOfficerStamina) {
		this.initialSecurityOfficerStamina = initialSecurityOfficerStamina;
	}

	public void setInitialSecurityGuard1Stamina(int initialSecurityGuard1Stamina) {
		this.initialSecurityGuard1Stamina = initialSecurityGuard1Stamina;
	}

	public void setInitialSecurityGuard2Stamina(int initialSecurityGuard2Stamina) {
		this.initialSecurityGuard2Stamina = initialSecurityGuard2Stamina;
	}

	public void setInitialShipShields(int initialShipShields) {
		this.initialShipShields = initialShipShields;
	}

	public void setCurrentScienceOfficerSkill(int currentScienceOfficerSkill) {
		this.currentScienceOfficerSkill = currentScienceOfficerSkill;
	}

	public void setCurrentMedicalOfficerSkill(int currentMedicalOfficerSkill) {
		this.currentMedicalOfficerSkill = currentMedicalOfficerSkill;
	}

	public void setCurrentEngineeringOfficerSkill(int currentEngineeringOfficerSkill) {
		this.currentEngineeringOfficerSkill = currentEngineeringOfficerSkill;
	}

	public void setCurrentSecurityOfficerSkill(int currentSecurityOfficerSkill) {
		this.currentSecurityOfficerSkill = currentSecurityOfficerSkill;
	}

	public void setCurrentSecurityGuard1Skill(int currentSecurityGuard1Skill) {
		this.currentSecurityGuard1Skill = currentSecurityGuard1Skill;
	}

	public void setCurrentSecurityGuard2Skill(int currentSecurityGuard2Skill) {
		this.currentSecurityGuard2Skill = currentSecurityGuard2Skill;
	}

	public void setCurrentShipWeapons(int currentShipWeapons) {
		this.currentShipWeapons = currentShipWeapons;
	}

	public void setCurrentScienceOfficerStamina(int currentScienceOfficerStamina) {
		this.currentScienceOfficerStamina = currentScienceOfficerStamina;
	}

	public void setCurrentMedicalOfficerStamina(int currentMedicalOfficerStamina) {
		this.currentMedicalOfficerStamina = currentMedicalOfficerStamina;
	}

	public void setCurrentEngineeringOfficerStamina(int currentEngineeringOfficerStamina) {
		this.currentEngineeringOfficerStamina = currentEngineeringOfficerStamina;
	}

	public void setCurrentSecurityOfficerStamina(int currentSecurityOfficerStamina) {
		this.currentSecurityOfficerStamina = currentSecurityOfficerStamina;
	}

	public void setCurrentSecurityGuard1Stamina(int currentSecurityGuard1Stamina) {
		this.currentSecurityGuard1Stamina = currentSecurityGuard1Stamina;
	}

	public void setCurrentSecurityGuard2Stamina(int currentSecurityGuard2Stamina) {
		this.currentSecurityGuard2Stamina = currentSecurityGuard2Stamina;
	}

	public void setCurrentShipShields(int currentShipShields) {
		this.currentShipShields = currentShipShields;
	}

	@Override
	protected void loadAdventureSpecificValuesFromFile() {
		currentScienceOfficerSkill = Integer.valueOf(getSavedGame().getProperty("scienceOfficerSkill"));
		currentScienceOfficerStamina = Integer.valueOf(getSavedGame().getProperty("scienceOfficerStamina"));
		currentMedicalOfficerSkill = Integer.valueOf(getSavedGame().getProperty("medicalOfficerSkill"));
		currentMedicalOfficerStamina = Integer.valueOf(getSavedGame().getProperty("medicalOfficerStamina"));
		currentEngineeringOfficerSkill = Integer.valueOf(getSavedGame().getProperty("engineeringOfficerSkill"));
		currentEngineeringOfficerStamina = Integer.valueOf(getSavedGame().getProperty("engineeringOfficerStamina"));
		currentSecurityOfficerSkill = Integer.valueOf(getSavedGame().getProperty("securityOfficerSkill"));
		currentSecurityOfficerStamina = Integer.valueOf(getSavedGame().getProperty("securityOfficerStamina"));
		currentSecurityGuard1Skill = Integer.valueOf(getSavedGame().getProperty("securityGuard1Skill"));
		currentSecurityGuard1Stamina = Integer.valueOf(getSavedGame().getProperty("securityGuard1Stamina"));
		currentSecurityGuard2Skill = Integer.valueOf(getSavedGame().getProperty("securityGuard2Skill"));
		currentSecurityGuard2Stamina = Integer.valueOf(getSavedGame().getProperty("securityGuard2Stamina"));
		currentShipWeapons = Integer.valueOf(getSavedGame().getProperty("shipWeapons"));
		currentShipShields = Integer.valueOf(getSavedGame().getProperty("shipShields"));

		initialScienceOfficerSkill = Integer.valueOf(getSavedGame().getProperty("scienceOfficerInitialSkill"));
		initialScienceOfficerStamina = Integer.valueOf(getSavedGame().getProperty("scienceOfficerInitialStamina"));
		initialMedicalOfficerSkill = Integer.valueOf(getSavedGame().getProperty("medicalOfficerInitialSkill"));
		initialMedicalOfficerStamina = Integer.valueOf(getSavedGame().getProperty("medicalOfficerInitialStamina"));
		initialEngineeringOfficerSkill = Integer.valueOf(getSavedGame().getProperty("engineeringOfficerInitialSkill"));
		initialEngineeringOfficerStamina = Integer.valueOf(getSavedGame().getProperty(
				"engineeringOfficerInitialStamina"));
		initialSecurityOfficerSkill = Integer.valueOf(getSavedGame().getProperty("securityOfficerInitialSkill"));
		initialSecurityOfficerStamina = Integer.valueOf(getSavedGame().getProperty("securityOfficerInitialStamina"));
		initialSecurityGuard1Skill = Integer.valueOf(getSavedGame().getProperty("securityGuard1InitialSkill"));
		initialSecurityGuard1Stamina = Integer.valueOf(getSavedGame().getProperty("securityGuard1InitialStamina"));
		initialSecurityGuard2Skill = Integer.valueOf(getSavedGame().getProperty("securityGuard2InitialSkill"));
		initialSecurityGuard2Stamina = Integer.valueOf(getSavedGame().getProperty("securityGuard2InitialStamina"));
		initialShipWeapons = Integer.valueOf(getSavedGame().getProperty("shipInitialWeapons"));
		initialShipShields = Integer.valueOf(getSavedGame().getProperty("shipInitialShields"));

		landingPartyScienceOfficer = Boolean.valueOf(getSavedGame().getProperty("landingPartyScienceOfficer"));
		landingPartyMedicalOfficer = Boolean.valueOf(getSavedGame().getProperty("landingPartyMedicalOfficer"));
		landingPartyEngineeringOfficer = Boolean.valueOf(getSavedGame().getProperty("landingPartyEngineeringOfficer"));
		landingPartySecurityOfficer = Boolean.valueOf(getSavedGame().getProperty("landingPartySecurityOfficer"));
		landingPartySecurityGuard1 = Boolean.valueOf(getSavedGame().getProperty("landingPartySecurityGuard1"));
		landingPartySecurityGuard2 = Boolean.valueOf(getSavedGame().getProperty("landingPartySecurityGuard2"));

		deadScienceOfficer = Boolean.valueOf(getSavedGame().getProperty("deadScienceOfficer"));
		deadMedicalOfficer = Boolean.valueOf(getSavedGame().getProperty("deadMedicalOfficer"));
		deadEngineeringOfficer = Boolean.valueOf(getSavedGame().getProperty("deadEngineeringOfficer"));
		deadSecurityOfficer = Boolean.valueOf(getSavedGame().getProperty("deadSecurityOfficer"));
		deadSecurityGuard1 = Boolean.valueOf(getSavedGame().getProperty("deadSecurityGuard1"));
		deadSecurityGuard2 = Boolean.valueOf(getSavedGame().getProperty("deadSecurityGuard2"));

	}

	public synchronized boolean isLandingPartyScienceOfficer() {
		return landingPartyScienceOfficer;
	}

	public synchronized boolean isLandingPartyMedicalOfficer() {
		return landingPartyMedicalOfficer;
	}

	public synchronized boolean isLandingPartyEngineeringOfficer() {
		return landingPartyEngineeringOfficer;
	}

	public synchronized boolean isLandingPartySecurityOfficer() {
		return landingPartySecurityOfficer;
	}

	public synchronized boolean isLandingPartySecurityGuard1() {
		return landingPartySecurityGuard1;
	}

	public synchronized boolean isLandingPartySecurityGuard2() {
		return landingPartySecurityGuard2;
	}

	public synchronized boolean isDeadScienceOfficer() {
		return deadScienceOfficer;
	}

	public synchronized boolean isDeadMedicalOfficer() {
		return deadMedicalOfficer;
	}

	public synchronized boolean isDeadEngineeringOfficer() {
		return deadEngineeringOfficer;
	}

	public synchronized boolean isDeadSecurityOfficer() {
		return deadSecurityOfficer;
	}

	public synchronized boolean isDeadSecurityGuard1() {
		return deadSecurityGuard1;
	}

	public synchronized boolean isDeadSecurityGuard2() {
		return deadSecurityGuard2;
	}

	public synchronized void setLandingPartyScienceOfficer(boolean landingPartyScienceOfficer) {
		this.landingPartyScienceOfficer = landingPartyScienceOfficer;
	}

	public synchronized void setLandingPartyMedicalOfficer(boolean landingPartyMedicalOfficer) {
		this.landingPartyMedicalOfficer = landingPartyMedicalOfficer;
	}

	public synchronized void setLandingPartyEngineeringOfficer(boolean landingPartyEngineeringOfficer) {
		this.landingPartyEngineeringOfficer = landingPartyEngineeringOfficer;
	}

	public synchronized void setLandingPartySecurityOfficer(boolean landingPartySecurityOfficer) {
		this.landingPartySecurityOfficer = landingPartySecurityOfficer;
	}

	public synchronized void setLandingPartySecurityGuard1(boolean landingPartySecurityGuard1) {
		this.landingPartySecurityGuard1 = landingPartySecurityGuard1;
	}

	public synchronized void setLandingPartySecurityGuard2(boolean landingPartySecurityGuard2) {
		this.landingPartySecurityGuard2 = landingPartySecurityGuard2;
	}

	public synchronized void setDeadScienceOfficer(boolean deadScienceOfficer) {
		this.deadScienceOfficer = deadScienceOfficer;
	}

	public synchronized void setDeadMedicalOfficer(boolean deadMedicalOfficer) {
		this.deadMedicalOfficer = deadMedicalOfficer;
	}

	public synchronized void setDeadEngineeringOfficer(boolean deadEngineeringOfficer) {
		this.deadEngineeringOfficer = deadEngineeringOfficer;
	}

	public synchronized void setDeadSecurityOfficer(boolean deadSecurityOfficer) {
		this.deadSecurityOfficer = deadSecurityOfficer;
	}

	public synchronized void setDeadSecurityGuard1(boolean deadSecurityGuard1) {
		this.deadSecurityGuard1 = deadSecurityGuard1;
	}

	public synchronized void setDeadSecurityGuard2(boolean deadSecurityGuard2) {
		this.deadSecurityGuard2 = deadSecurityGuard2;
	}

}
