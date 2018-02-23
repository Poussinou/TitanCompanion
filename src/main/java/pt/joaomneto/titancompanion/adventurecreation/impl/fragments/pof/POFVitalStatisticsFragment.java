package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.pof;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment;

public class POFVitalStatisticsFragment extends VitalStatisticsFragment {


    public POFVitalStatisticsFragment() {

    }

    TextView powerValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_28pof_adventurecreation_vital_statistics, container, false);
        powerValue = rootView.findViewById(R.id.powerValue);

        return rootView;
    }

    public TextView getPowerValue() {
        return powerValue;
    }


}
