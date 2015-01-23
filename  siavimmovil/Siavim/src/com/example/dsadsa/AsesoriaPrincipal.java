package com.example.dsadsa;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.models.Asesorias;
import com.example.models.Horarios;
import com.example.siavim.Login;

import databaseModels.AsesoriasBD;
import databaseModels.BaseDatabase;
import databaseModels.CursosBD;
import databaseModels.LoginBD;

public class AsesoriaPrincipal extends Activity {

	TextView text;
	EditText pregunta;
	Button guardarPregunta;
	String curso;
	String idCurso;
	String cedula;
	String cedulaBase;
	public static BaseDatabase claseDB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asesoria_principal);
		text = (TextView)findViewById(R.id.txDigitePregunta);
		pregunta = (EditText)findViewById(R.id.Pregunta);
		guardarPregunta = (Button)findViewById(R.id.btGuardarPregunta);
		Bundle bolsa = getIntent().getExtras();
		setTitle(bolsa.getString("CURSO"));
		curso = bolsa.getString("CURSO");
		cedulaBase = BaseDatabase.getCedulaUser();
		ArrayList<Horarios> hor = new ArrayList<Horarios>();
		CursosBD cur = new CursosBD(AsesoriaPrincipal.this);
		LoginBD log = new LoginBD(AsesoriaPrincipal.this);
		log.abrir();
		Vector<Login> vect = log.recibir();
		Login l = vect.elementAt(0);
		log.cerrar();
        cur.abrir();
        hor = cur.ObtenerCursoPorID(curso);
        Horarios h = new Horarios();
        h = hor.get(0);
        cedula = l.getCedula();
        idCurso = h.getIDCurso();
        cur.cerrar();
        
        guardarPregunta.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GuardarPreguntaAsesoria ga = new GuardarPreguntaAsesoria();
				ga.execute(pregunta.getText().toString(),idCurso,cedulaBase);
			}
		});
		
	}
	
	private class GuardarPreguntaAsesoria extends AsyncTask<String,Integer,String>
	{
		@Override
		protected String doInBackground(String... params) 
		{
			String res = "";
			final String NAMESPACE = "http://sgoliver.net/";
			final String URL="http://10.0.2.2:52250/ValidarUsuario.asmx";
			final String METHOD_NAME = "GuardarAsesorias";
			final String SOAP_ACTION = "http://sgoliver.net/GuardarAsesorias";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("pregunta", params[0]); 
			request.addProperty("idcurso", params[1]);
			request.addProperty("cedula", params[2]);
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
				Toast toast = Toast.makeText(AsesoriaPrincipal.this, "ERROR GuardarPreguntaAsesoria:" + e.getMessage(), Toast.LENGTH_SHORT);
				toast.show();
			}
			//Toast toast = Toast.makeText(AsesoriaPrincipal.this, "Pregunta Guardada:", Toast.LENGTH_SHORT);
			//toast.show();			
			return res;
		}
		
		protected void onPostExecute(String result) {
			Date d = new Date();
			AsesoriasBD ase = new AsesoriasBD(AsesoriaPrincipal.this);
			ase.abrir();
			Asesorias as = new Asesorias();
			as.setAsesoria(Integer.parseInt(result));
			as.setFechaCreacion(d.getYear()+"/"+d.getMonth()+"/" +d.getDay());
			as.setFechaRespuesta("");
			as.setIdCurso(curso);
			as.setPregunta(pregunta.getText().toString());
			as.setResueltaOk(0);
			as.setRespuesta("");
			ase.crearEntrada(as);
			ase.cerrar();
		}
		
	}
	
	
}
