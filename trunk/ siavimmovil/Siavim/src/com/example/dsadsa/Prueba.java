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
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.models.Alertas;
import com.example.models.Horarios;
import com.example.models.Tareas;
import com.example.siavim.Login;

import databaseModels.AlertasBD;
import databaseModels.CursosBD;
import databaseModels.LoginBD;
import databaseModels.TareasBD;

public class Prueba extends Activity {

	TextView nombre;
	Button btModificar,btCursos;
	Vector<Login> loguser = new Vector<Login>();
	String nombreUsuario,apellidos,email,telefono,password,cedula,nombreCursos;
	Handler hand = new Handler();
	NotificationManager nm;
	static final int unico = 773672;
	public static final String NAMESPACE = "http://sgoliver.net/";
	public static final String URL="http://10.0.2.2:52250/ValidarUsuario.asmx";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prueba);
		setTitle("INDEX");
		nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		nm.cancel(unico);
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
		ConsultarTareas tareas = new ConsultarTareas();
		tareas.execute();
		tarea.execute(cedula);
		info.execute(cedula);
		lbd.abrir();
		lbd.ModificarStatus(bolsa.getString("NOMBRE"),bolsa.getString("PASSWORD"),bolsa.getString("APELLIDOS"),bolsa.getString("EMAIL"),bolsa.getString("CARRERA"),bolsa.getString("TELEFONO"),bolsa.getString("CEDULA"));
		loguser = lbd.recibir();
		lbd.cerrar();
		hand.removeCallbacks(actualizar);
		hand.postDelayed(actualizar,1000);
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

	private Runnable actualizar = new Runnable() {

		@Override
		public void run() {
			correr();
			hand.postDelayed(this, 40000);
		}
	};

	public void correr(){
		AlertasBD ab = new AlertasBD(Prueba.this);			
		ab.abrir();
		Vector<Alertas> alert = ab.obtenerAlertas();
		String alerts = "";
		if(alert.size()>0){
			Alertas obj = new Alertas();
			for(int i=0;i<alert.size();i++){
				obj = alert.elementAt(i);
				alerts += obj.getIdAlerta();
			}
		}
		ab.cerrar();
		ConsultarAlertas info = new ConsultarAlertas();
		info.execute(cedula,alerts);
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
				Toast toast = Toast.makeText(Prueba.this, "ERROR ConsultarInfoCurso:" + e.getMessage(), Toast.LENGTH_SHORT);
				toast.show();
			} 
			return res;
		}

		protected void onPostExecute(String result) {
			nombreCursos = result;
		}
	}

	private class ConsultarTareas extends AsyncTask<String,Integer,String>
	{
		@Override
		protected String doInBackground(String... params) 
		{
			String res = "";

			final String METHOD_NAME = "ObtenerTareas";
			final String SOAP_ACTION = "http://sgoliver.net/ObtenerTareas";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
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
				Toast toast = Toast.makeText(Prueba.this, "ERROR ConsultarTareas:" + e.getMessage(), Toast.LENGTH_SHORT);
				toast.show();
			} 
			return res;
		}

		protected void onPostExecute(String result) {
			if(result!= ""){
				JSONArray jObj;
				ArrayList<Tareas> lista = new ArrayList<Tareas>();
				try {
					jObj = new JSONArray(result);
					for(int i=0;i<jObj.length();i++){
						Tareas t = new Tareas();
						JSONObject rec = jObj.getJSONObject(i);
						t.setIdTarea(rec.getInt("IdTarea"));
						t.setIdCurso(rec.getInt("IdCurso"));
						t.setDescripcionTarea(rec.getString("DescripcionTarea"));
						t.setNombreTarea(rec.getString("NombreTarea"));
						t.setFechaEntrega(rec.getString("FechaEntregaTarea").substring(0, 10));
						t.setFechaCreacion(rec.getString("FechaCreacion").substring(0,10));
						lista.add(t);
					}
					TareasBD bd = new TareasBD(getApplicationContext());
					bd.abrir();
					bd.crearEntrada(lista);
					bd.cerrar();

				} catch (JSONException e) {
					e.printStackTrace();
					Toast toast = Toast.makeText(Prueba.this, "ERROR ConsultarTareas:" + e.getMessage(), Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		}
	}

	private class ConsultarAlertas extends AsyncTask<String,Integer,String>
	{
		@Override
		protected String doInBackground(String... params) 
		{
			String res = "";
			final String METHOD_NAME = "ObtenerAlertas";
			final String SOAP_ACTION = "http://sgoliver.net/ObtenerAlertas";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("cedula", params[0]);
			request.addProperty("idAlertas",params[1]);
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
				Toast toast = Toast.makeText(Prueba.this, "ERROR ConsultarAlertas:" + e.getMessage(), Toast.LENGTH_SHORT);
				toast.show();
			} 
			return res;
		}

		protected void onPostExecute(String result) {
			try{
				if(result!= ""){
					JSONArray jObj;
					Alertas alertas = new Alertas();
					AlertasBD ab = new AlertasBD(Prueba.this);					
					ArrayList<Alertas> lista = new ArrayList<Alertas>();
					jObj = new JSONArray(result);
					for(int i=0;i<jObj.length();i++){
						ab.abrir();
						JSONObject rec = jObj.getJSONObject(i);
						alertas.setIdAlerta(rec.getInt("IdAlerta"));
						alertas.setIdCurso(rec.getInt("IdCurso"));
						alertas.setTipoAlerta(rec.getString("TipoAlerta"));
						alertas.setDetalleAlerta(rec.getString("DetalleAlerta"));
						alertas.setProcesadaOK("0");
						lista.add(alertas);
						GenerarNotificacionAlerta(alertas.getTipoAlerta(),alertas.getDetalleAlerta());
					}
					ab.crearEntrada(lista);
					ab.cerrar();
				}
			}catch (RuntimeException e){

				e.printStackTrace();
				Toast toast = Toast.makeText(Prueba.this, "ERROR ConsultarAlertas:" + e.getMessage(), Toast.LENGTH_SHORT);
				toast.show();
			} catch (JSONException e) {
				e.printStackTrace();
				Toast toast = Toast.makeText(Prueba.this, "ERROR ConsultarAlertas:" + e.getMessage(), Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}
	
	public void GenerarNotificacionAlerta(String tipoAlerta,String detalleAlerta){
		
		Intent intent = new Intent(Prueba.this,Principal_cursos.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pi = PendingIntent.getActivity(Prueba.this, 0, intent,0);
		String cuerpo = "Tienes Notificaciones";
		Notification n = new Notification(R.drawable.ic_launcher,cuerpo,System.currentTimeMillis());
		n.setLatestEventInfo(Prueba.this, tipoAlerta, detalleAlerta, pi);
		n.flags |= Notification.FLAG_AUTO_CANCEL;
		nm.notify(unico, n);
		int contador = 0;
		contador = contador + 1;
		
	}

	private class CargarInfoBDCurso extends AsyncTask<String,Integer,String>
	{
		@Override
		protected String doInBackground(String... params) 
		{
			String res = "";
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
				Toast toast = Toast.makeText(Prueba.this, "ERROR CargarInfoBDCurso:" + e.getMessage(), Toast.LENGTH_SHORT);
				toast.show();
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
						}else{
							hora.setLunes("");
						}
						if(jsonHor.getString("MartesInicio")!= "null"){
							hora.setMartes(jsonHor.getString("MartesInicio") + "-" + jsonHor.getString("MartesFin"));
						}else{
							hora.setMartes("");
						}
						if(jsonHor.getString("MiercolesInicio")!= "null"){
							hora.setMiercoles(jsonHor.getString("MiercolesInicio") + "-" + jsonHor.getString("MiercolesFin"));
						}else{
							hora.setMiercoles("");
						}
						if(jsonHor.getString("JuevesInicio")!= "null"){
							hora.setJueves(jsonHor.getString("JuevesInicio") + "-" + jsonHor.getString("JuevesFin"));
						}else{
							hora.setJueves("");
						}
						if(jsonHor.getString("ViernesInicio")!= "null"){
							hora.setViernes(jsonHor.getString("ViernesInicio") + "-" + jsonHor.getString("ViernesFin"));
						}else{
							hora.setViernes("");
						}
						if(jsonHor.getString("SabadoInicio")!= "null"){
							hora.setSabado(jsonHor.getString("SabadoInicio") + "-" + jsonHor.getString("SabadoFin"));
						}else{
							hora.setSabado("");
						}
						hor.add(hora);
					}
				}
				cbd.abrir();
				cbd.crearEntrada(hor);
				cbd.cerrar();

			} catch (JSONException e) {
				e.printStackTrace();
				Toast toast = Toast.makeText(Prueba.this, "ERROR CargarInfoBDCurso:" + e.getMessage(), Toast.LENGTH_SHORT);
				toast.show();
			}
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
