package com.example.dsadsa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MostrarCursoActivity extends Activity {

	TextView labelCurso,labelIntensidad;
	EditText etCurso,etIntensidad;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostrar_curso);
		Bundle bolsa = getIntent().getExtras();
		ConsultarInfoCurso tarea = new ConsultarInfoCurso();
		labelCurso = (TextView)findViewById(R.id.LabelNombreCurso);
		labelIntensidad = (TextView)findViewById(R.id.LabelIntensidadHoraria);
		etCurso = (EditText)findViewById(R.id.tvNombreCurso);
		etIntensidad = (EditText)findViewById(R.id.tvIntensidadHoraria);
		tarea.execute(bolsa.getString("CEDULA"));
		
	}
	
	private class ConsultarInfoCurso extends AsyncTask<String,Integer,String>
	{
		@Override
		protected String doInBackground(String... params) 
		{
			String res = "";
			boolean reg = false;
			final String NAMESPACE = "http://sgoliver.net/";
			final String URL="http://10.0.2.2:52250/ValidarUsuario.asmx";
			final String METHOD_NAME = "ObtenerCursoPorIdUsuario";
			final String SOAP_ACTION = "http://sgoliver.net/ObtenerCursoPorIdUsuario";
						
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			request.addProperty("cedula", params[0]); 
			
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
		
		protected void onPostExecute(String result) {
			if(!result.equals("false")){
				JSONObject j;
						
				try {
					
					JSONArray jObj = new JSONArray(result);
					for (int i = 0; i < jObj.length(); ++i) {
					    JSONObject rec = jObj.getJSONObject(i);
					    String loc = rec.getString("NombreCurso");
					    
					}
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				
				
			}else{
				
			}
		}
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostrar_curso, menu);
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
