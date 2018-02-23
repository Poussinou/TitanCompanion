package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.sa;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SAVitalStatisticsFragment extends VitalStatisticsFragment {
	

	public SAVitalStatisticsFragment(){
		
	}
	
	TextView armorValue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.fragment_12sa_adventurecreation_vital_statistics, container, false);
		armorValue = rootView.findViewById(R.id.armorValue);

		return rootView;
	}

	public TextView getArmorValue() {
		return armorValue;
	}

	
	
	

}
