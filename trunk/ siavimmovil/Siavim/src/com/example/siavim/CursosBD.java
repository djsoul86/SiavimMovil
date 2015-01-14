package com.example.siavim;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.models.Horarios;


public class CursosBD {
	public static final String ID_FILA = "_id";
	public static final String ID_CURSO = "id_curso";
	public static final String ID_NOMBRECURSO = "nombre_curso";
	public static final String ID_INTENSIDADH = "intensidad_horaria";
	public static final String ID_PROFESOR = "profesor_curso";
	public static final String ID_LUNES = "horario_lunes";
	public static final String ID_MARTES = "horario_martes";
	public static final String ID_MIERCOLES = "horario_miercoles";
	public static final String ID_JUEVES = "horario_jueves";
	public static final String ID_VIERNES = "horario_viernes";
	public static final String ID_SABADO = "horario_sabado";
	Vector cursosVector = new Vector();

	private static final String N_BD = "SIAVIMDatabase";
	private static final String N_TABLA = "Cursos_Detail";
	private static final int VERSION_BD = 4;


	private BDHelper nHelper;
	private final Context nContexto;
	private SQLiteDatabase nBD;



	private static class BDHelper extends SQLiteOpenHelper{

		public BDHelper(Context context) {
			super(context, N_BD, null, VERSION_BD);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + N_TABLA + "(" + 
					ID_FILA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					ID_CURSO + " TEXT NOT NULL, " +
					ID_NOMBRECURSO + " TEXT NOT NULL, " +
					ID_INTENSIDADH + " TEXT NOT NULL, " +
					ID_LUNES + " TEXT NOT NULL, " +
					ID_MARTES + " TEXT NOT NULL, " +
					ID_MIERCOLES + " TEXT NOT NULL, " +
					ID_JUEVES + " TEXT NOT NULL, " +
					ID_VIERNES + " TEXT NOT NULL, " +
					ID_SABADO + " TEXT NOT NULL, " +
					ID_PROFESOR + " TEXT NOT NULL);"

					);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + N_TABLA);
			onCreate(db);
		}

	}

	public CursosBD(Context c) {
		nContexto = c;
	}

	public CursosBD abrir(){
		nHelper = new BDHelper(nContexto);
		nBD = nHelper.getWritableDatabase();
		return this;
	}

	public void cerrar() {
		nHelper.close();
	}

	public long crearEntrada(ArrayList<Horarios> model) {
		ContentValues cv = new ContentValues();
		for(int i=0;i<model.size();i++){
			Horarios h = new Horarios();
			h = model.get(i);
			cv.put(ID_CURSO, h.getIDCurso());
			cv.put(ID_NOMBRECURSO, h.getNombreCurso());
			cv.put(ID_INTENSIDADH,h.getIntensidad());
			cv.put(ID_PROFESOR, h.getIdProfesor());
			cv.put(ID_LUNES,h.getLunes());
			cv.put(ID_MARTES, h.getMartes());
			cv.put(ID_MIERCOLES, h.getMiercoles());
			cv.put(ID_JUEVES, h.getJueves());
			cv.put(ID_VIERNES, h.getViernes());
			cv.put(ID_SABADO, h.getSabado());
		}
		return nBD.insert(N_TABLA, null, cv);
	}

	public Vector recibir() {
		// TODO Auto-generated method stub
		String[] columnas = new String[]{ID_FILA,ID_CURSO,ID_NOMBRECURSO,ID_INTENSIDADH,ID_PROFESOR,ID_LUNES,ID_MARTES,ID_MIERCOLES,ID_JUEVES,ID_VIERNES,ID_SABADO};
		Cursor c = nBD.query(N_TABLA, columnas,null, null, null, null, null);

		int iIdCurso = c.getColumnIndex(ID_CURSO);
		int iNombreCurso = c.getColumnIndex(ID_NOMBRECURSO);
		int iIntensidad = c.getColumnIndex(ID_INTENSIDADH);
		int iProfesor = c.getColumnIndex(ID_PROFESOR);
		int iLunes = c.getColumnIndex(ID_LUNES);
		int iMartes = c.getColumnIndex(ID_MARTES);
		int iMiercoles = c.getColumnIndex(ID_MIERCOLES);
		int iJueves = c.getColumnIndex(ID_JUEVES);
		int iViernes= c.getColumnIndex(ID_VIERNES);
		int iSabado = c.getColumnIndex(ID_SABADO);

		for(c.moveToFirst();! c.isAfterLast(); c.moveToNext()){

			Horarios hor = new Horarios();
			hor.setIDCurso(c.getString(iIdCurso));
			hor.setNombreCurso(c.getString(iNombreCurso));
			hor.setIntensidad(c.getInt(iIntensidad));
			hor.setIdProfesor(c.getString(iProfesor));
			hor.setLunes(c.getString(iLunes));
			hor.setMartes(c.getString(iMartes));
			hor.setMiercoles(c.getString(iMiercoles));
			hor.setJueves(c.getString(iJueves));
			hor.setViernes(c.getString(iViernes));
			hor.setSabado(c.getString(iSabado));
			cursosVector.add(hor);

		}
		return cursosVector;
	}
	/*
	public void ModificarStatus(String user, String password,String apellido, String email, String carrera, String telefono,String cedula) {
		try{

			Cursor cd = nBD.rawQuery("SELECT * FROM "+ N_TABLA + " where "+ ID_USUARIO + " = " + "\""+user+"\"", null);
			Date date = new Date();; // your date
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			SimpleDateFormat sdf = new SimpleDateFormat("k:mm a");
			String formatedTime = sdf.format(date);
			String fecha = day +"/"+month+"/"+year+"-"+formatedTime;
			if(cd == null || cd.getCount()<1){
				crearEntrada(user, fecha, 1, password,apellido,email,carrera,telefono,cedula);
			}else{
				ContentValues cvEditar = new ContentValues();
				cvEditar.put(ID_STATUSLOGIN, 1);
				nBD.update(N_TABLA, cvEditar, ID_USUARIO + "=" + "\""+user+"\"", null);
			}
		}catch(Exception exc){

		}

	}

	public void ModificarStatusLogout(String user) {
		try{
			ContentValues cvEditar = new ContentValues();
			cvEditar.put(ID_STATUSLOGIN, 0);
			nBD.update(N_TABLA, cvEditar, ID_USUARIO + "=" + "\""+user+"\"", null);

		}catch(Exception exc){

		}

	}

	 */
	public void borrar() throws SQLException {
		nBD.execSQL("delete from " + N_TABLA);
	}
}
