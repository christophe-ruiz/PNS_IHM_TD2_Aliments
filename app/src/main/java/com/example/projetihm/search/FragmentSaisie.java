package com.example.projetihm.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projetihm.R;

public class FragmentSaisie extends Fragment {

    Fragment frag;
    FragmentTransaction fragTransaction;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recherche, container, false);
        final EditText valeur = (EditText) rootView.findViewById(R.id.saisie);


        valeur.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int longueur = valeur.getText().length();
                if (longueur != 0) {
                    String ValeurSaisie = valeur.getText().toString();
                    frag = new FragmentAffiche();
                    Bundle args = new Bundle();
                    args.putString("valeur", ValeurSaisie);
                    frag.setArguments(args);

                    fragTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.valeurpassee, frag);
                    fragTransaction.commit();
                }
            }
        });

        return rootView;
    }

}
