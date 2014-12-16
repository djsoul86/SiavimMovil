package com.example.dsadsa;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.example.siavim.LoginBD;

public class Prueba extends Activity {
	
	TextView nombre;
	Button btModificar;
	Vector loguser = new Vector();
	String nombreUsuario,apellidos,email,telefono,password,cedula;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prueba);
		Bundle bolsa = getIntent().getExtras();
		nombreUsuario = bolsa.getString("NOMBRE");
		apellidos = bolsa.getString("APELLIDOS");
		telefono = bolsa.getString("TELEFONO");
		password = bolsa.getString("PASSWORD");
		email = bolsa.getString("EMAIL");
		cedula = bolsa.getString("CEDULA");
		LoginBD lbd = new LoginBD(Prueba.this);
		nombre = (TextView) findViewById(R.id.tvNombre);
		nombre.setText(nombreUsuario + " " + bolsa.getString("APELLIDOS"));
		btModificar = (Button) findViewById(R.id.btModificar);
		lbd.abrir();
		lbd.ModificarStatus(bolsa.getString("NOMBRE"),bolsa.getString("PASSWORD"),bolsa.getString("APELLIDOS"),bolsa.getString("EMAIL"),bolsa.getString("CARRERA"),bolsa.getString("TELEFONO"),bolsa.getString("CEDULA"));
		loguser = lbd.recibir();
		lbd.cerrar();
		
		btModificar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(getApplicationContext(),ModificarUser.class);
				intent.putExtra("NOMBRE", nombreUsuario);
				intent.putExtra("APELLIDOS", apellidos);
				intent.putExtra("TELEFONO", telefono);
				intent.putExtra("PASSWORD", password);
				intent.putExtra("EMAIL", email);
				intent.putExtra("CEDULA", cedula);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prueba, menu);
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
