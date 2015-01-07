package com.example.dsadsa;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.example.models.*;

public class MostrarCursoActivity extends Activity {

	private static Context context;
	TextView labelCurso,labelIntensidad;
	EditText etCurso,etIntensidad;
	LinearLayout myLinearLayout;
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
		myLinearLayout = (LinearLayout)findViewById(R.id.layoutDinamic);
		tarea.execute(bolsa.getString("CEDULA"));
	}

	public static Context getAppContext() {
		return MostrarCursoActivity.context;
	}

	private class ConsultarInfoCurso extends AsyncTask<String,Integer,String>
	{
		@Override
		protected String doInBackground(String... params) 
		{
			String res = "";
			final String NAMESPACE = "http://sgoliver.net/";
			final String URL="http://10.0.2.2:52250/ValidarUsuario.asmx";
			final String METHOD_NAME = "ObtenerCursoPorIdUsuario";
			final String SOAP_ACTION = "http://sgoliver.net/ObtenerCursoPorIdUsuario";
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
				Toast toast = Toast.makeText(MostrarCursoActivity.this, "ERROR:" + e.getMessage(), Toast.LENGTH_SHORT);
				toast.show();
			} 
			return res;
		}

		protected void onPostExecute(String result) {
			if(!result.equals("false")){
				try {
					int N = 0;
					ArrayList<Horarios> dias = new ArrayList<Horarios>();
					JSONArray jObj = new JSONArray(result);
					JSONObject rec = jObj.getJSONObject(0);
					String horario = rec.getString("Horarios");
					if(!horario.equals("[]")){
					JSONArray hor = new JSONArray(horario);
					JSONObject jsonHor = hor.getJSONObject(0);
					if(jsonHor.getString("LunesInicio")!= "null"){
						Horarios dia = new Horarios(); 
						dia.Lunes = jsonHor.getString("LunesInicio") + "-" + jsonHor.getString("LunesFin");
						N++;
						dias.add(dia);
					}
					if(jsonHor.getString("MartesInicio")!= "null"){
						Horarios dia = new Horarios();
						dia.Martes = jsonHor.getString("MartesInicio") + "-" + jsonHor.getString("MartesFin");
						N++;
						dias.add(dia);
					}
					if(jsonHor.getString("MiercolesInicio")!= "null"){
						Horarios dia = new Horarios();
						dia.Miercoles = jsonHor.getString("MiercolesInicio") + "-" + jsonHor.getString("MiercolesFin");
						N++;
						dias.add(dia);
					}
					if(jsonHor.getString("JuevesInicio")!= "null"){
						Horarios dia = new Horarios();
						dia.Jueves = jsonHor.getString("JuevesInicio") + "-" + jsonHor.getString("JuevesFin");
						N++;
						dias.add(dia);
					}
					if(jsonHor.getString("ViernesInicio")!= "null"){
						Horarios dia = new Horarios();
						dia.Viernes = jsonHor.getString("ViernesInicio") + "-" + jsonHor.getString("ViernesFin");
						N++;
						dias.add(dia);
					}
					if(jsonHor.getString("SabadoInicio")!= "null"){
						Horarios dia = new Horarios();
						dia.Sabado = jsonHor.getString("SabadoInicio") + "-" + jsonHor.getString("SabadoFin");
						N++;
						dias.add(dia);
					}
					etCurso.setText(rec.getString("NombreCurso"));
					etIntensidad.setText(rec.getString("IntensidadHoraria"));

					final TextView[] myTextViews = new TextView[N]; 
					for (int i = 0; i < N; i++) {
						final TextView rowTextView = new TextView(MostrarCursoActivity.this);
						if(dias.get(i)!= null){
							Horarios d = new Horarios();
							d = dias.get(i);
							if(d.Lunes != null)
							rowTextView.setText("Lunes:" + "\t" + dias.get(i).Lunes);
							if(d.Martes != null)
								rowTextView.setText("Martes:" + "\t" + dias.get(i).Martes);
							if(d.Miercoles != null)
								rowTextView.setText("Miercoles:" + "\t" + dias.get(i).Miercoles);
							if(d.Jueves  != null)
								rowTextView.setText("Jueves:" + "\t" + dias.get(i).Jueves);
							if(d.Viernes != null)
								rowTextView.setText("Viernes:" + "\t" + dias.get(i).Viernes);
							if(d.Sabado != null)
								rowTextView.setText("Sabado:" + "\t" + dias.get(i).Sabado);
						}
						
						LinearLayout lLayour = new LinearLayout(MostrarCursoActivity.this);
						LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT,1.0f);
						lLayour.setLayoutParams(param);
						myLinearLayout.setLayoutParams(param);
						lLayour.setId(i);
						myLinearLayout.addView(lLayour);
						lLayour.addView(rowTextView);
						myTextViews[i] = rowTextView;
					}
					
					}else{
						etCurso.setText(rec.getString("NombreCurso"));
						etIntensidad.setText(rec.getString("IntensidadHoraria"));
						Toast toast = Toast.makeText(MostrarCursoActivity.this, "EL CURSO NO TIENE HORARIOS ASOCIADOS", Toast.LENGTH_SHORT);
						toast.show();
					}

				} catch (JSONException e) {

					e.printStackTrace();
					Toast toast = Toast.makeText(MostrarCursoActivity.this, "ERROR:" + e.getMessage(), Toast.LENGTH_SHORT);
					toast.show();
				}
			}else{
				Toast toast = Toast.makeText(MostrarCursoActivity.this, "NO TIENE CURSOS ASOCIADOS", Toast.LENGTH_SHORT);
				toast.show();
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
