package br.com.aldivio.estudos.instagram.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import br.com.aldivio.estudos.instagram.R;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        initComponents(view);
        setListeners();
        return view;
    }

    private void initComponents(View view){
        searchView = view.findViewById(R.id.fragment_search_search_view);
        recyclerView = view.findViewById(R.id.fragment_search_recycler_view);

        searchView.setQueryHint("Pesquisar Usuários");
    }
    private void setListeners(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String name) {
                searchUsers(name);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
    }

    private void searchUsers(String name){

    }
}