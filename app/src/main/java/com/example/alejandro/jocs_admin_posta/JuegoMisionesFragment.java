package com.example.alejandro.jocs_admin_posta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alejandro.jocs_admin_posta.db_utils.DatabaseManager;
import com.example.alejandro.jocs_admin_posta.model.Mision;

import java.util.List;

/**
 * Created by Alejandro on 2/6/2016.
 */

public class JuegoMisionesFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_JUEGO = "juego_id";
    private MisionAdapter misionAdapter;
    private long juego_id;
    private RecyclerView recList;

    public JuegoMisionesFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static JuegoMisionesFragment newInstance(long juego_id) {
        JuegoMisionesFragment fragment = new JuegoMisionesFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_JUEGO, juego_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_juego_misiones, container, false);
        juego_id = (long) getArguments().getLong(ARG_JUEGO);

        /*FLOATING ACTION BUTTON*/
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_agregar_mision);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, MisionAgregarActivity.class);
                intent.putExtra(ARG_JUEGO, juego_id);
                context.startActivity(intent);
            }
        });

        /*CARDS*/
        recList = (RecyclerView) v.findViewById(R.id.misiones_texts);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(v.getContext()); //nro de columnas
        recList.setLayoutManager(llm);

        List<Mision> misions = DatabaseManager.getInstance().getAllMisionesFromJuego(juego_id);
        misionAdapter = new MisionAdapter(getActivity(), misions);
        recList.setAdapter(misionAdapter);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("JUEGO MISIONES FRAG", "ON RESUMEEEE");
        List<Mision> misions = DatabaseManager.getInstance().getAllMisionesFromJuego(juego_id);
        misionAdapter = new MisionAdapter(getActivity(), misions);
        recList.setAdapter(misionAdapter);
    }
}