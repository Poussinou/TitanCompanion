package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.ff;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FFVitalStatisticsFragment extends VitalStatisticsFragment {


    public FFVitalStatisticsFragment() {

    }

    TextView armorValue;
    TextView firepowerValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_13ff_adventurecreation_vital_statistics, container, false);
        armorValue = (TextView) rootView.findViewById(R.id.armorValue);
        firepowerValue = (TextView) rootView.findViewById(R.id.firepowerValue);

        return rootView;
    }

    public TextView getArmorValue() {
        return armorValue;
    }

    public TextView getFirepowerValue() {
        return firepowerValue;
    }
}
