package com.example.dsadsa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuCursos extends Activity {

	ListView lista;
	String curso = "";
	String[] opciones = new String[]{"Tareas","Foros","Notas","Asesoria"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_cursos);
		Bundle bolsa = getIntent().getExtras();
		setTitle(bolsa.getString("CURSO"));
		curso = bolsa.getString("CURSO");
		lista = (ListView) findViewById(R.id.lvCursosMenu);
		final ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,opciones);
		lista.setAdapter(aa);

		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				String data=(String)parent.getItemAtPosition(position);
				if(data == "Tareas"){
					Intent intent = new Intent(getApplicationContext(),Tareas_Principal.class);
					Bundle bolsa = new Bundle();
					bolsa.putString("CURSO", curso);
					intent.putExtras(bolsa);
					startActivity(intent);
				}
				if(data == "Notas"){
					Intent intent = new Intent(getApplicationContext(),NotasPrincipal.class);
					Bundle bolsa = new Bundle();
					bolsa.putString("CURSO", curso);
					intent.putExtras(bolsa);
					startActivity(intent);
				}
				if(data == "Asesoria"){
					Intent intent = new Intent(getApplicationContext(),AsesoriaPrincipal.class);
					Bundle bolsa = new Bundle();
					bolsa.putString("CURSO", curso);
					intent.putExtras(bolsa);
					startActivity(intent);
				}
			}
		}); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_cursos, menu);
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
