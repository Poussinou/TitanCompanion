package pt.joaomneto.ffgbutil.adventure.impl.fragments.ff;

import java.util.Arrays;
import java.util.List;

import pt.joaomneto.ffgbutil.R;
import pt.joaomneto.ffgbutil.adventure.Adventure;
import pt.joaomneto.ffgbutil.adventure.impl.fragments.AdventureCombatFragment;
import pt.joaomneto.ffgbutil.util.DiceRoller;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

public class FFAdventureCombatFragment extends AdventureCombatFragment{

	public static final String FF13_GUNFIGHT = "FF13_GUNFIGHT";

	private Spinner damageSpinner = null;
	private TextView damageText = null;

	private List<String> damageList = Arrays.asList(new String[] { "1", "2",
			"3", "4", "1D6" });

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_13ff_adventure_combat,
				container, false);

		damageSpinner = (Spinner) rootView.findViewById(R.id.damageSpinner);
		damageText = (TextView) rootView.findViewById(R.id.damageText);

		if (damageSpinner != null) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_list_item_1,
					android.R.id.text1, damageList);
			damageSpinner.setAdapter(adapter);
		}

		if (overrideDamage != null) {
			damageSpinner.setSelection(damageList.indexOf(overrideDamage));
		} else {
			damageSpinner.setSelection(0);
		}

		damageSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				overrideDamage = damageList.get(arg2);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				overrideDamage = null;

			}
		});

		init();

		return rootView;
	}

	String overrideDamage = null;

	protected void combatTurn() {
		if (combatPositions.size() == 0)
			return;

		if (combatStarted == false) {
			combatStarted = true;
			combatTypeSwitch.setClickable(false);
		}
		if (combatMode.equals(NORMAL)) {
			standardCombatTurn();
		} else {
			sequenceCombatTurn();
		}

	}

	@Override
	protected int getDamage() {
		if (combatMode.equals(NORMAL)) {
			if (overrideDamage == null) {
				return 1;
			} else
				return convertDamageStringToInteger(overrideDamage);
		} else if (combatMode.equals(FF13_GUNFIGHT)) {
			return DiceRoller.rollD6();
		}

		return 2;
	}

	protected String combatTypeSwitchBehaviour(boolean isChecked) {
		return combatMode = isChecked ? FF13_GUNFIGHT : NORMAL;
	}

	public String getOntext() {
		return "Gunfight";
	}

	@Override
	protected void resetCombat() {
		super.resetCombat();
	}

	protected void switchLayoutCombatStarted() {

		damageSpinner.setVisibility(View.INVISIBLE);
		damageText.setVisibility(View.INVISIBLE);

		addCombatButton.setVisibility(View.GONE);
		combatTypeSwitch.setVisibility(View.GONE);
		startCombatButton.setVisibility(View.GONE);
		// testLuckButton.setVisibility(View.VISIBLE);
		combatTurnButton.setVisibility(View.VISIBLE);

		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		p.addRule(RelativeLayout.ABOVE, R.id.attackButton);
		p.addRule(RelativeLayout.LEFT_OF, R.id.resetCombat);

		testLuckButton.setLayoutParams(p);

		p = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		p.addRule(RelativeLayout.ABOVE, R.id.attackButton);
		p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		//
		resetButton.setLayoutParams(p);
	}

	protected void switchLayoutReset() {
		damageSpinner.setVisibility(View.VISIBLE);
		damageText.setVisibility(View.VISIBLE);

		addCombatButton.setVisibility(View.VISIBLE);
		combatTypeSwitch.setVisibility(View.VISIBLE);
		startCombatButton.setVisibility(View.VISIBLE);
		// testLuckButton.setVisibility(View.GONE);
		combatTurnButton.setVisibility(View.GONE);

		LayoutParams p = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		p.addRule(RelativeLayout.BELOW, R.id.addCombatButton);
		p.addRule(RelativeLayout.ALIGN_RIGHT, R.id.combatType);
		p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		resetButton.setLayoutParams(p);
	}

	protected Integer getKnockoutStamina() {
		return 6;
	}

	protected void addCombatButtonOnClick() {

		Adventure adv = (Adventure) getActivity();

		final InputMethodManager mgr = (InputMethodManager) adv
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (combatStarted)
			return;

		if (row >= maxRows) {
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(adv);

		final View addCombatantView = adv
				.getLayoutInflater()
				.inflate(
						combatMode == null || combatMode.equals(FF13_GUNFIGHT) ? R.layout.component_add_combatant
								: R.layout.component_13ff_add_combatant, null);

		final EditText damageValue = (EditText) addCombatantView
				.findViewById(R.id.enemyDamage);

		builder.setTitle("Add Enemy")
				.setCancelable(false)
				.setNegativeButton("Close",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								mgr.hideSoftInputFromWindow(
										addCombatantView.getWindowToken(), 0);
								dialog.cancel();
							}
						});

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				int currentRow = getNextRow();

				mgr.hideSoftInputFromWindow(addCombatantView.getWindowToken(),
						0);

				EditText enemySkillValue = (EditText) addCombatantView
						.findViewById(R.id.enemySkillValue);
				EditText enemyStaminaValue = (EditText) addCombatantView
						.findViewById(R.id.enemyStaminaValue);
				EditText handicapValue = (EditText) addCombatantView
						.findViewById(R.id.handicapValue);

				Integer skill = Integer.valueOf(enemySkillValue.getText()
						.toString());
				Integer stamina = Integer.valueOf(enemyStaminaValue.getText()
						.toString());
				Integer handicap = Integer.valueOf(handicapValue.getText()
						.toString());

				addCombatant(rootView, currentRow, skill, stamina, handicap,
						damageValue == null ? null : damageValue.getText()
								.toString());

			}

		});

		AlertDialog alert = builder.create();

		EditText skillValue = (EditText) addCombatantView
				.findViewById(R.id.enemySkillValue);

		alert.setView(addCombatantView);

		mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
		skillValue.requestFocus();

		alert.show();
	}

	@Override
	protected void startCombat() {
		if (combatMode.equals(FF13_GUNFIGHT)) {
			for (int i = 0; i < combatPositions.size(); i++) {
				Combatant c = combatPositions.get(i);
				if (c != null)
					c.setDamage("1D6");
			}
		}
		super.startCombat();
	}

}
