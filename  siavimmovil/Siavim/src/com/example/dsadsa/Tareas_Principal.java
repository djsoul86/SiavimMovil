package com.example.dsadsa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.example.models.Horarios;
import com.example.models.Tareas;

import databaseModels.CursosBD;
import databaseModels.TareasBD;
 
public class Tareas_Principal extends Activity {
 
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    String curso = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas__principal);
        Bundle bolsa = getIntent().getExtras();
		setTitle(bolsa.getString("CURSO"));
		curso = bolsa.getString("CURSO");
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
 
        // preparing list data
        prepareListData();
 
        listAdapter = new ExpandableListTareasAdapter(this, listDataHeader, listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
 
        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {
 
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });
 
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
 
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });
 
        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
 
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
 
            }
        });
 
        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {
 
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                        listDataHeader.get(groupPosition)).get(
                                        childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }
 
    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
 
        ArrayList<Tareas> t = new ArrayList<Tareas>();
        ArrayList<Horarios> hor = new ArrayList<Horarios>();
        TareasBD tar = new TareasBD(Tareas_Principal.this);
        CursosBD cur = new CursosBD(Tareas_Principal.this);
        cur.abrir();
        tar.abrir();
        hor = cur.ObtenerCursoPorID(curso);
        Horarios h = new Horarios();
        h = hor.get(0);
        cur.cerrar();
        t = tar.obtenerTareasPorIDCurso(Integer.parseInt(h.getIDCurso()));
        tar.cerrar();
        // Adding child data
        for(int i=0;i<t.size();i++){
        	Tareas ta = t.get(i);
        	listDataHeader.add(ta.getNombreTarea());
        }
        for(int i=0;i<t.size();i++){
        	Tareas ta = t.get(i);
        	ArrayList<String> ad = new ArrayList<String>();
        	ad.add("Descripcion: " + ta.getDescripcionTarea());
        	ad.add("Fecha Entrega Tarea:" + ta.getFechaEntrega());
        	ad.add("Fecha Creacion: " + ta.getFechaCreacion());
        	listDataChild.put(listDataHeader.get(i),ad); // Header, Child data
        }

    }
}
