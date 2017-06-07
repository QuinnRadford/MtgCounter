package tech.fluff.mtgcounter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;


public class PlayerFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int life = 20;
    private int energy = 0;
    private int storm = 0;
    private int poison = 0;

    public PlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerFragment newInstance(String param1, String param2) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_player, container, false);
        TextView lifeText = (TextView) v.findViewById(R.id.lifeText);
        TextView energyText = (TextView) v.findViewById(R.id.energyText);
        energyText.setOnClickListener(this);
        TextView plusFive = (TextView) v.findViewById(R.id.plusFive);
        plusFive.setOnClickListener(this);
        TextView minusLife = (TextView) v.findViewById(R.id.minusLife);
        minusLife.setOnClickListener(this);
        TextView minusEnergy = (TextView) v.findViewById(R.id.minusEnergy);
        minusEnergy.setOnClickListener(this);
        TextView minusFive = (TextView) v.findViewById(R.id.minusFive);
        minusFive.setOnClickListener(this);
        TextView minusPoison = (TextView) v.findViewById(R.id.plusLife);
        minusPoison.setOnClickListener(this);
        Bundle extras = getActivity().getIntent().getExtras();
        String lifeValue = extras.getString("EXTRA_LIFE");
        lifeText.setText(lifeValue);
        Log.w("Counter Value", lifeValue);

        //v.getRootView().setBackgroundColor(randomColor());
        return v;
    }

    public int randomColor() {
        int red;
        int green;
        int blue;
        int alpha = 80;

        Random r = new Random();
        int seed = r.nextInt(6 - 1);
        if (seed == 1) {
            blue = r.nextInt(240 - 180) + 180;
            red = r.nextInt(140 - 80) + 80;
            green = r.nextInt(140 - 80) + 80;
        } else if (seed == 2) {
            green = r.nextInt(240 - 180) + 180;
            blue = r.nextInt(140 - 50) + 50;
            red = r.nextInt(140 - 80) + 80;
        } else if (seed == 3) {
            green = r.nextInt(240 - 180) + 180;
            blue = r.nextInt(240 - 180) + 180;
            red = r.nextInt(140 - 80) + 80;
        } else if (seed == 4) {
            green = r.nextInt(240 - 180) + 180;
            blue = r.nextInt(140 - 80) + 80;
            red = r.nextInt(240 - 180) + 180;
        } else {
            red = r.nextInt(240 - 180) + 180;
            green = r.nextInt(140 - 80) + 80;
            blue = r.nextInt(140 - 80) + 80;
        }
        Log.w("Red is:", Integer.toString(red));
        Log.w("Blue is:", Integer.toString(blue));
        Log.w("Green is:", Integer.toString(green));
        int num = (((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF));
        Log.w("Background is:", Integer.toHexString(num));
        return num;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        String lifeValue;
        String energyValue;
        String poisonValue;
        LinearLayout baseLayout = null;
        View layoutStep = v;
        int getout = 0;
        while (baseLayout == null && getout < 10) {
            layoutStep = (LinearLayout) layoutStep.getParent();
            Log.w("found layout:", String.valueOf(layoutStep.getId()));
            baseLayout = (LinearLayout) layoutStep.findViewById(R.id.baseLinear);
            getout++;
        }

        TextView lifeText = (TextView) baseLayout.findViewById(R.id.lifeText);
        TextView energyText = (TextView) baseLayout.findViewById(R.id.energyText);
        switch (v.getId()) {
            case R.id.energyText:
                energyValue = energyText.getText().toString();
                energyText.setText(String.valueOf(Integer.valueOf(energyValue) + 1));
                break;
            case R.id.plusFive:
                lifeValue = lifeText.getText().toString();
                lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 5));
                break;
            case R.id.minusLife:
                lifeValue = lifeText.getText().toString();
                lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 1));
                break;
            case R.id.minusEnergy:
                energyValue = energyText.getText().toString();
                energyText.setText(String.valueOf(Integer.valueOf(energyValue) - 1));
                break;
            case R.id.minusFive:
                lifeValue = lifeText.getText().toString();
                lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) - 5));
                break;
            case R.id.plusLife:
                lifeValue = lifeText.getText().toString();
                lifeText.setText(String.valueOf(Integer.valueOf(lifeValue) + 1));
                break;
        }
    }
}
