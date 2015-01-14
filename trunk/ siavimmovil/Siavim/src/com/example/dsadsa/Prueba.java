package com.example.dsadsa;

import java.util.ArrayList;
import java.util.Vector;

import org.json.JSONArray;
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
import android.widget.TextView;

import com.example.models.Horarios;
import com.example.siavim.CursosBD;
import com.example.siavim.LoginBD;

public class Prueba extends Activity {

	TextView nombre;
	Button btModificar,btCursos;
	Vector loguser = new Vector();
	String nombreUsuario,apellidos,email,telefono,password,cedula,nombreCursos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prueba);
		setTitle("INDEX");
		Bundle bolsa = getIntent().getExtras();
		nombreUsuario = bolsa.getString("NOMBRE");
		apellidos = bolsa.getString("APELLIDOS");
		telefono = bolsa.getString("TELEFONO");
		password = bolsa.getString("PASSWORD");
		email = bolsa.getString("EMAIL");
		cedula = bolsa.getString("CEDULA");
		btCursos = (Button) findViewById(R.id.btCursos);
		LoginBD lbd = new LoginBD(Prueba.this);
		nombre = (TextView) findViewById(R.id.tvNombre);
		nombre.setText(nombreUsuario + " " + bolsa.getString("APELLIDOS"));
		btModificar = (Button) findViewById(R.id.btModificar);
		ConsultarInfoCurso tarea = new ConsultarInfoCurso();
		CargarInfoBDCurso info = new CargarInfoBDCurso();
		tarea.execute(cedula);
		info.execute(cedula);
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
				intent.putExtra("NOMBRECURSO", nombreCursos);
				startActivity(intent);
			}
		});

		btCursos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(getApplicationContext(),Principal_cursos.class);
				startActivity(intent);
			}
		});
	}

	private class ConsultarInfoCurso extends AsyncTask<String,Integer,String>
	{
		@Override
		protected String doInBackground(String... params) 
		{
			String res = "";
			final String NAMESPACE = "http://sgoliver.net/";
			final String URL="http://10.0.2.2:52250/ValidarUsuario.asmx";
			final String METHOD_NAME = "ObtenerNombreCurso";
			final String SOAP_ACTION = "http://sgoliver.net/ObtenerNombreCurso";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("cedula", params[0]); 
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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
				e.printStackTrace();
				//Toast toast = Toast.makeText(MostrarCursoActivity.this, "ERROR:" + e.getMessage(), Toast.LENGTH_SHORT);
				//toast.show();
			} 
			return res;
		}

		protected void onPostExecute(String result) {
			nombreCursos = result;
		}
	}

	private class CargarInfoBDCurso extends AsyncTask<String,Integer,String>
	{
		@Override
		protected String doInBackground(String... params) 
		{
			String res = "";
			final String NAMESPACE = "http://sgoliver.net/";
			final String URL="http://10.0.2.2:52250/ValidarUsuario.asmx";
			final String METHOD_NAME = "ObtenerDatosCursoPorCedula";
			final String SOAP_ACTION = "http://sgoliver.net/ObtenerDatosCursoPorCedula";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("cedula", params[0]); 
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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
				e.printStackTrace();
				//Toast toast = Toast.makeText(MostrarCursoActivity.this, "ERROR:" + e.getMessage(), Toast.LENGTH_SHORT);
				//toast.show();
			} 
			return res;
		}

		protected void onPostExecute(String result) {
			CursosBD cbd = new CursosBD(Prueba.this);
			JSONArray jObj;

			try {
				ArrayList<Horarios> hor = new ArrayList<Horarios>();
				jObj = new JSONArray(result);
				for(int i=0;i<jObj.length();i++){
					Horarios hora = new Horarios();
					hora.setIDCurso(jObj.getJSONObject(i).getString("IdCurso"));
					hora.setIntensidad(jObj.getJSONObject(i).getInt("IntensidadHoraria"));
					hora.setIdProfesor(jObj.getJSONObject(i).getString("IdProfesor"));
					hora.setNombreCurso(jObj.getJSONObject(i).getString("NombreCurso"));
					String horario = jObj.getJSONObject(i).getString("Horarios");
					if(!horario.equals("[]")){
						JSONArray ho = new JSONArray(horario);
						JSONObject jsonHor = ho.getJSONObject(0);
						if(jsonHor.getString("LunesInicio")!= "null"){
							hora.setLunes(jsonHor.getString("LunesInicio") + "-" + jsonHor.getString("LunesFin"));
						}
						if(jsonHor.getString("MartesInicio")!= "null"){
							hora.setMartes(jsonHor.getString("MartesInicio") + "-" + jsonHor.getString("MartesFin"));
						}
						if(jsonHor.getString("MiercolesInicio")!= "null"){
							hora.setMiercoles(jsonHor.getString("MiercolesInicio") + "-" + jsonHor.getString("MiercolesFin"));
						}
						if(jsonHor.getString("JuevesInicio")!= "null"){
							hora.setJueves(jsonHor.getString("JuevesInicio") + "-" + jsonHor.getString("JuevesFin"));
						}
						if(jsonHor.getString("ViernesInicio")!= "null"){
							hora.setViernes(jsonHor.getString("ViernesInicio") + "-" + jsonHor.getString("ViernesFin"));
						}
						if(jsonHor.getString("SabadoInicio")!= "null"){
							hora.setSabado(jsonHor.getString("SabadoInicio") + "-" + jsonHor.getString("SabadoFin"));
						}
						hor.add(hora);
					}
				}
				cbd.abrir();
				cbd.crearEntrada(hor);
				cbd.cerrar();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//cbd.crearEntrada(idCurso, nombreCurso, intensidad, idProfesor, lunes, martes, miercoles, jueves, viernes, sabado)
		}
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
