package com.example.siavim;

import java.util.Vector;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.example.dsadsa.Prueba;
import com.example.dsadsa.R;


public class MainActivity extends Activity implements OnClickListener {

	private EditText usuario;
	private EditText clave;
	private Button btIngresar;
	Vector loguser = new Vector();

	private static Context context;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MainActivity.context = getApplicationContext();

		usuario = (EditText) findViewById(R.id.etUsuario);
		clave = (EditText) findViewById(R.id.etContrasena);
		btIngresar = (Button) findViewById(R.id.btIngresar);
		btIngresar.setOnClickListener(this);
	}

	public static Context getAppContext() {
		return MainActivity.context;
	}

	public void onClick(View v) {
		TareaRegistroGCM tarea = new TareaRegistroGCM();
		tarea.execute(usuario.getText().toString(),clave.getText().toString());

		//boolean validar = new TareaRegistroGCM().execute(usuario.getText().toString(),clave.getText().toString());
		/*
		if(true == true){

			Intent intent = new Intent("com.example.scanlogisflow.IndexActivity");
			Bundle bolsa = new Bundle();
			bolsa.putString("NOMBRE", usuario.getText().toString());
			bolsa.putString("PASSWORD", clave.getText().toString());
			intent.putExtras(bolsa);
			startActivity(intent);
			//overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
		}else{
			Toast toast = Toast.makeText(this, "La contrase�a o el usuario es incorrecto", Toast.LENGTH_SHORT);
			toast.show();
		}
		 */
	}

	public void IngresarUsuario(String resultado){
		if(resultado.equals("true")){

			startActivity(new Intent(getApplicationContext(),Prueba.class));
			//context.startActivity(new Intent(context,Prueba.class));
			//Intent intent = new Intent("com.example.dsadsa.Prueba");
			//Bundle bolsa = new Bundle();
			//bolsa.putString("NOMBRE", usuario.getText().toString());
			//bolsa.putString("PASSWORD", clave.getText().toString());
			//intent.putExtras(bolsa);
			//startActivity(intent);
			//overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
		}else{
			Toast toast = Toast.makeText(context, "USUARIO O CONTRASE�A INVALIDA", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	private class TareaRegistroGCM extends AsyncTask<String,Integer,String>
	{
		@Override
		protected String doInBackground(String... params) 
		{
			String res = "";
			boolean reg = false;
			final String NAMESPACE = "http://sgoliver.net/";
			final String URL="http://10.0.2.2:52250/ValidarUsuario.asmx";
			final String METHOD_NAME = "ValidarEstudiante";
			final String SOAP_ACTION = "http://sgoliver.net/ValidarEstudiante";
			HttpClient httpClient = new DefaultHttpClient();

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			request.addProperty("cedula", params[0]); 
			request.addProperty("password", params[1]); 

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

				if(res.equals("true"))
				{
					reg = true;
				}
			} 
			catch (Exception e) 
			{
				String prueba = e.toString();
				String a = "aaa";
			} 

			return res;
		}

		protected void onPostExecute(String result) {
			if(result.equals("true")){
				startActivity(new Intent(getApplicationContext(),Prueba.class));
			}else{
				Toast toast = Toast.makeText(context, "USUARIO O CONTRASE�A INVALIDA", Toast.LENGTH_SHORT);
				toast.show();
			}
			startActivity(new Intent(getApplicationContext(),Prueba.class));
		}
	}
}
