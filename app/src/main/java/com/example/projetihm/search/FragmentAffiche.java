package com.example.projetihm.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projetihm.R;
import com.example.projetihm.SearchActivity;
import com.example.projetihm.adapaters.SearchAdapter;

public class FragmentAffiche extends Fragment {
    View rootView;
    String valeur;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //récupération de la valeur de la table
        Bundle b = getArguments();
        //Log.d("FragmentAffichee()",".onCreate()=>   getArguments()="+getArguments());
        if(b!=null)
        {
            valeur = getArguments().getString("valeur");
        }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recherche_result, null);
        String produit = valeur;

        ListProduct listeP = new ListProduct(produit);
        listeP.construireListe();
        SearchAdapter sa = new SearchAdapter(this.getContext(), listeP);

        //Récupération du composant ListView
        ListView list = (ListView)rootView.findViewById(R.id.List);

        list.setAdapter(sa);

        return rootView;
    }

}
