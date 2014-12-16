package com.example.dsadsa;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ModificarUser extends Activity {

	Button btModificar;
	EditText nombre,email,apellidos,telefono,password;
	String cedula;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modificar_user);
		nombre = (EditText)findViewById(R.id.tvNombreUsuario);
		email = (EditText)findViewById(R.id.tvEmailUsuario);
		apellidos = (EditText)findViewById(R.id.tvApellidoUsuario);
		telefono = (EditText)findViewById(R.id.tvTelefonoUsuario);
		password = (EditText)findViewById(R.id.tvPasswordUsuario);
		btModificar = (Button)findViewById(R.id.btGuardar);
		Bundle bolsa = getIntent().getExtras();
		nombre.setText(bolsa.getString("NOMBRE"));
		apellidos.setText(bolsa.getString("APELLIDOS"));
		email.setText(bolsa.getString("EMAIL"));
		telefono.setText(bolsa.getString("TELEFONO"));
		password.setText(bolsa.getString("PASSWORD"));
		cedula = bolsa.getString("CEDULA");
		
		btModificar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ModificarUsuario tarea = new ModificarUsuario();
				tarea.execute(nombre.getText().toString(),apellidos.getText().toString(),telefono.getText().toString(),email.getText().toString(),password.getText().toString(),cedula);
			}
		});
	}
	
	private class ModificarUsuario extends AsyncTask<String,Integer,String>
	{
		@Override
		protected String doInBackground(String... params) 
		{
			String res = "";
			boolean reg = false;
			final String NAMESPACE = "http://sgoliver.net/";
			final String URL="http://10.0.2.2:52250/ValidarUsuario.asmx";
			final String METHOD_NAME = "ModificarEstudiante";
			final String SOAP_ACTION = "http://sgoliver.net/ModificarEstudiante";
						
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			request.addProperty("nombre", params[0]); 
			request.addProperty("apellidos", params[1]); 
			request.addProperty("telefono",params[2]);
			request.addProperty("email",params[3]);
			request.addProperty("password",params[4]);
			request.addProperty("cedula",params[5]);

			SoapSerializationEnvelope envelope = 
					new SoapSerializationEnvelope(SoapEnvelope.VER11);

			envelope.dotNet = true; 
			envelope.setOutputSoapObject(request);

			HttpTransportSE transporte = new HttpTransportSE(URL);

			try 
			{
				transporte.call(SOAP_ACTION, envelope);
				SoapPrimitive resultado_xml =(SoapPrimitive)envelope.getResponse();
				res = resultado_xml.toString();
				
								
			} 
			catch (Exception e) 
			{
				String prueba = e.toString();
				String a = "aaa";
			} 

			return res;
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modificar_user, menu);
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
