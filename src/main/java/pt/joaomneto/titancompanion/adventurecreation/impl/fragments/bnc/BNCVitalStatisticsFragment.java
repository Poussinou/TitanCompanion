package pt.joaomneto.titancompanion.adventurecreation.impl.fragments.bnc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventurecreation.impl.fragments.VitalStatisticsFragment;

public class BNCVitalStatisticsFragment extends VitalStatisticsFragment {


    public BNCVitalStatisticsFragment() {

    }

    TextView willpowerValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_25bnc_adventurecreation_vital_statistics, container, false);
        willpowerValue = (TextView) rootView.findViewById(R.id.willpowerValue);

        return rootView;
    }

    public TextView getWillpowerValue() {
        return willpowerValue;
    }


}
