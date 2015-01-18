package com.example.dsadsa;


import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.models.Horarios;

import databaseModels.CursosBD;

public class Principal_cursos extends Activity {

	ListView lista;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal_cursos);
		setTitle("CURSOS ASOCIADOS");
		CursosBD curso = new CursosBD(Principal_cursos.this);
		curso.abrir();
		ArrayList<Horarios> cursos = curso.recibir();
		curso.cerrar();
		ArrayList<String> listaCursos = new ArrayList<String>();
		for(int i=0;i<cursos.size();i++){
			Horarios h = new Horarios();
			h = cursos.get(i);
			listaCursos.add(h.NombreCurso);
		}
		lista = (ListView) findViewById(R.id.lvLista);
		final ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaCursos);
		lista.setAdapter(aa);
		
		lista.setOnItemClickListener(new OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				  String data=(String)parent.getItemAtPosition(position);
				  Intent intent = new Intent(getApplicationContext(),MenuCursos.class);
				  Bundle bolsa = new Bundle();
				  bolsa.putString("CURSO", data);
				  intent.putExtras(bolsa);
				  startActivity(intent);
			  }
			}); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal_cursos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
