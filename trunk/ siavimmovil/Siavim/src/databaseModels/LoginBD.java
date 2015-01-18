package databaseModels;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.example.siavim.Login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class LoginBD {

	public static final String ID_FILA = "_id";
	public static final String ID_USUARIO = "id_ususario";
	public static final String ID_FECHALOGIN = "fecha_login";
	public static final String ID_STATUSLOGIN = "status_login";
	public static final String ID_PASSWORD = "password_usuario";
	public static final String ID_APELLIDO = "apellido_usuario";
	public static final String ID_CEDULA = "cedula_usuario";
	public static final String ID_EMAIL = "email_usuario";
	public static final String ID_CARRERA = "carrera_usuario";
	public static final String ID_TELEFONO = "telefono_usuario";
	
	public static final String ID_ALERTA = "_idalerta";
	public static final String ID_TIPOALERTA = "id_tipoalerta";
	public static final String ID_DESCRIPCIONALERTA = "descripcion_alerta";
	public static final String ID_PROCESADAOK = "procesadoOk";
	public static final String ID_CURSO = "id_curso";
	
	public static final String ID_TAREA = "id_tarea";
	public static final String ID_NOMBRETAREA = "nombre_tarea";
	public static final String ID_DESCRIPCIONTAREA = "descripcion_tarea";
	public static final String ID_FECHAENTREGA = "fecha_entrega";
	public static final String ID_FECHACREACION = "fecha_creacion";
	
	private static final String N_BD = "SIAVIMDatabase";
	private static final String N_TABLA = "Login_Detail";
	private static final String N_TABLAALERTA = "Alertas_Detail";
	private static final String N_TABLATAREAS = "Tareas_Detail";
	private static final int VERSION_BD = 9;
	Vector<Login> loginvector = new Vector<Login>();

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
			//Crea tabla de login
			db.execSQL("CREATE TABLE " + N_TABLA + "(" + 
					ID_FILA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					ID_USUARIO + " TEXT NOT NULL, " +
					ID_STATUSLOGIN + " TEXT NOT NULL, " +
					ID_APELLIDO + " TEXT NOT NULL, " +
					ID_CEDULA + " TEXT NOT NULL, " +
					ID_EMAIL + " TEXT NOT NULL, " +
					ID_CARRERA + " TEXT NOT NULL, " +
					ID_TELEFONO + " TEXT NOT NULL, " +
					ID_PASSWORD + " TEXT NOT NULL, " +
					ID_FECHALOGIN + " TEXT NOT NULL);"

					);
			//Crea tabla de alertas
			db.execSQL("CREATE TABLE " + N_TABLAALERTA + "(" + 
					ID_ALERTA + " INTEGER PRIMARY KEY , " +
					ID_CURSO + " TEXT NOT NULL, " +
					ID_DESCRIPCIONALERTA + " TEXT NOT NULL, " +
					ID_TIPOALERTA + " TEXT NOT NULL, " +
					ID_PROCESADAOK + " TEXT NOT NULL );"
					);
			//Crea tabla de 
			db.execSQL("CREATE TABLE " + N_TABLATAREAS + "(" + 
					ID_TAREA + " INTEGER PRIMARY KEY , " +
					ID_CURSO + " TEXT NOT NULL, " +
					ID_NOMBRETAREA + " TEXT NOT NULL, " +
					ID_DESCRIPCIONTAREA + " TEXT NOT NULL, " +
					ID_FECHACREACION + " TEXT , " +
					ID_FECHAENTREGA + " TEXT);"
					);


		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + N_TABLA);
			db.execSQL("DROP TABLE IF EXISTS " + N_TABLAALERTA);
			db.execSQL("DROP TABLE IF EXISTS " + N_TABLATAREAS);
			onCreate(db);
		}

	}

	public LoginBD(Context c) {
		nContexto = c;
	}

	public LoginBD abrir(){
		nHelper = new BDHelper(nContexto);
		nBD = nHelper.getWritableDatabase();
		return this;
	}

	public void cerrar() {
		nHelper.close();
	}

	public long crearEntrada(String usuario,String fecha, int status,String password,String apellido, String email, String carrera, String telefono,String cedula) {
		ContentValues cv = new ContentValues();
		cv.put(ID_USUARIO, usuario);
		cv.put(ID_FECHALOGIN, fecha);
		cv.put(ID_PASSWORD,password);
		cv.put(ID_STATUSLOGIN, status);
		cv.put(ID_APELLIDO, apellido);
		cv.put(ID_CEDULA, cedula);
		cv.put(ID_EMAIL, email);
		cv.put(ID_CARRERA, carrera);
		cv.put(ID_TELEFONO, telefono);
		return nBD.insert(N_TABLA, null, cv);
	}

	public Vector<Login> recibir() {
		// TODO Auto-generated method stub
		String[] columnas = new String[]{ID_FILA,ID_USUARIO,ID_FECHALOGIN,ID_STATUSLOGIN,ID_PASSWORD,ID_APELLIDO,ID_CARRERA,ID_CEDULA,ID_EMAIL,ID_TELEFONO};
		Cursor c = nBD.query(N_TABLA, columnas,ID_STATUSLOGIN + "= 1", null, null, null, null);

		int iUsuario = c.getColumnIndex(ID_USUARIO);
		int iFecha = c.getColumnIndex(ID_FECHALOGIN);
		int iStatus = c.getColumnIndex(ID_STATUSLOGIN);
		int iPassword = c.getColumnIndex(ID_PASSWORD);
		int iApellido = c.getColumnIndex(ID_APELLIDO);
		int iCarrera = c.getColumnIndex(ID_CARRERA);
		int iCedula = c.getColumnIndex(ID_CEDULA);
		int iEmail = c.getColumnIndex(ID_EMAIL);
		int iTelefono = c.getColumnIndex(ID_TELEFONO);

		for(c.moveToFirst();! c.isAfterLast(); c.moveToNext()){
			Login logi = new Login();
			logi.setFecha(c.getString(iFecha));
			logi.setStatus(Integer.parseInt(c.getString(iStatus)));
			logi.setUsuario(c.getString(iUsuario));
			logi.setPassword(c.getString(iPassword));
			logi.setApellido(c.getString(iApellido));
			logi.setCarrera(c.getString(iCarrera));
			logi.setCedula(c.getString(iCedula));
			logi.setEmail(c.getString(iEmail));
			logi.setTelefono(c.getString(iTelefono));
			logi.setNombre(c.getString(iUsuario));
			loginvector.add(logi);

		}
		return loginvector;
	}

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


	public void borrar() throws SQLException {
		nBD.execSQL("delete from " + N_TABLA);
	}
}
