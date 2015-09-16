/*
	Copyright (c) 2015 Luiz Carlos <luiz04nl@gmail.com>
*/

package br.com.minicurso;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class FragmentMyProfile extends Fragment
{
    protected Button btnSignOut;
    protected android.support.v7.app.AlertDialog alertDialog;
    protected de.hdodenhof.circleimageview.CircleImageView profile_image_myprofile;

    public FragmentMyProfile(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup accountiner, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_myprofile, accountiner, false);

        final SharedPreferencesMinicurso sharedPreferencesMinicurso = new SharedPreferencesMinicurso(getActivity());

        this.btnSignOut = (Button) rootView.findViewById(R.id.btn_sign_out);
        this.btnSignOut.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                SignOut();
            }
        });

        return rootView;
    }

    public void SignOut()
    {
        final SharedPreferencesMinicurso sharedPreferencesMinicurso = new SharedPreferencesMinicurso(getActivity());
        sharedPreferencesMinicurso.setUserName(null);

        final Intent it = new Intent(getActivity(), LoginActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(it);
        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}