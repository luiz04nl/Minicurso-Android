/*
Copyright (c) 2015 Luiz Carlos <luiz04nl@gmail.com>
*/
package br.com.minicurso;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.graphics.drawable.*;


public class FragmentMyProfile extends Fragment
{
    protected Button btnSignOut;
    protected android.support.v7.app.AlertDialog alertDialog;
    protected de.hdodenhof.circleimageview.CircleImageView profile_image_myprofile;

    public FragmentMyProfile(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup accountiner, Bundle
            savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_myprofile, accountiner,
                false);

        this.btnSignOut = (Button) rootView.findViewById(R.id.btn_sign_out);
        this.btnSignOut.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                SignOut();
            }
        });

        this.profile_image_myprofile = (de.hdodenhof.circleimageview.CircleImageView)
                rootView.findViewById(R.id.profile_image_myprofile);
        ProfileImage profileImage = new ProfileImage();

        if (profileImage.getDrawableProfileImage() != null)
        {
            Drawable drawableImgPessoa = profileImage.getDrawableProfileImage();
            this.profile_image_myprofile.setImageDrawable(drawableImgPessoa);
        }

        final android.support.v7.app.AlertDialog.Builder builder = new
                android.support.v7.app.AlertDialog.Builder(getActivity());
        this.profile_image_myprofile.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                builder.setTitle(R.string.selectPicture);
                builder.setPositiveButton(R.string.gallery, new
                        DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface arg0, int arg1)
                            {
                                ((NavigationActivity) getActivity()).getIntentGaleria();
                            }
                        });
                builder.setNegativeButton(R.string.camera, new
                        DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface arg0, int arg1)
                            {
                                ((NavigationActivity) getActivity()).getIntentCamera();
                            }
                        });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });



        return rootView;
    }

    public void SignOut()
    {
        final SharedPreferencesMinicurso sharedPreferencesMinicurso = new
                SharedPreferencesMinicurso(getActivity());

        sharedPreferencesMinicurso.setUserName(null);

        final Intent it = new Intent(getActivity(), LoginActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(it);
        getActivity().overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }
}