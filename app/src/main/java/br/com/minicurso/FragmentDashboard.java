/*
	Copyright (c) 2015 Luiz Carlos <luiz04nl@gmail.com>
*/

package br.com.minicurso;

import android.app.*;
import android.os.*;
import android.view.*;

public class FragmentDashboard extends Fragment
{
    protected String userName;

    public FragmentDashboard(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup accountiner,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, accountiner, false);
        SharedPreferencesMinicurso userName = new SharedPreferencesMinicurso(getActivity());
        this.userName = userName.getUserName();

        return rootView;
    }
}