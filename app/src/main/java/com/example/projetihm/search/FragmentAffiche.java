package com.example.projetihm.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projetihm.R;

public class FragmentAffiche extends Fragment {
    View rootView;
    int valeur;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //récupération de la valeur de la table
        Bundle b = getArguments();
        //Log.d("FragmentAffichee()",".onCreate()=>   getArguments()="+getArguments());
        if(b!=null)
        {
            valeur = getArguments().getInt("valeur");
        }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recherche_result, null);
        int nb = valeur;

        ListCalcul listeD = new ListCalcul(nb);
        listeD.construireListe();

        //Récupération du composant ListView
        ListView list = (ListView)rootView.findViewById(R.id.List);

        return rootView;
    }

}
