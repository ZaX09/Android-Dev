package com.sti.cabby.fragments;

import com.sti.cabby.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TrustedContactsFragment extends Fragment {
	
	public TrustedContactsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_tcontacts, container, false);
         
        return rootView;
    }
}
