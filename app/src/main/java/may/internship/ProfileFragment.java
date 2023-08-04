package may.internship;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class ProfileFragment extends Fragment {

    Button logout,changePassword,myOrder;
    ImageView profile;
    SharedPreferences sp;


    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sp = getActivity().getSharedPreferences(constantdata.PREF, Context.MODE_PRIVATE);

//        myOrder = view.findViewById(R.id.fragment_profile_order_hsitory);
//        myOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new CommonMethod(getActivity(),OrderHistoryActivity.class);
//            }
//        });

        changePassword = view.findViewById(R.id.fragment_profile_change_password);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new common(getActivity(), Change_password.class);
            }
        });

        profile = view.findViewById(R.id.fragment_profile_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new common(getActivity(), ProfileActivity.class);
            }
        });

        logout = view.findViewById(R.id.fragment_profile_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //using remove u can remove single data
                // sp.edit().remove(constantdata.name).commit();
                //using clear you can remove all data
                sp.edit().clear().commit();
                new common(getActivity(), MainActivity.class);
            }
        });
        return view;

    }
}