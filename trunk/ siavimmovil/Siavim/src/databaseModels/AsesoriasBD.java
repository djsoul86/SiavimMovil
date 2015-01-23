package databaseModels;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.models.Asesorias;

public class AsesoriasBD extends BaseDatabase {
	public static final String ID_ASESORIA = "id_asesoria";
	public static final String ID_PREGUNTA = "pregunta";
	public static final String ID_RESUELTAOK = "resultadook";
	public static final String ID_FECHACREACIONASESORIA = "fecha_creacion";
	public static final String ID_FECHARESPUESTA = "fecha_respuesta";
	public static final String ID_CURSO_ASESORIA = "curso_asesoria";	
	public static final String ID_RESPUESTA = "respuesta";

	private static final String N_BD = DatabaseName;
	private static final int VERSION_BD = VersionDatabase;
	private static final String N_TABLAASESORIAS = "Asesorias_Detail";

	ArrayList<Asesorias> asesoriasArray = new ArrayList<Asesorias>();

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
			db.execSQL("CREATE TABLE " + N_TABLAASESORIAS + "(" + 
					ID_ASESORIA + " INTEGER PRIMARY KEY , " +
					ID_PREGUNTA + " TEXT , " +
					ID_RESUELTAOK + " INTEGER , " +
					ID_FECHACREACIONASESORIA + " TEXT , " +
					ID_CURSO_ASESORIA + " TEXT , " +
					ID_RESPUESTA + " TEXT , " +
					ID_FECHARESPUESTA + " TEXT);"
					);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + N_TABLAASESORIAS);
			onCreate(db);
		}
	}

	public AsesoriasBD(Context c) {
		nContexto = c;
	}

	public AsesoriasBD abrir(){
		nHelper = new BDHelper(nContexto);
		nBD = nHelper.getWritableDatabase();
		return this;
	}

	public void cerrar() {
		nHelper.close();
	}

	public void crearEntrada(Asesorias model) {
		ContentValues cv = new ContentValues();
		cv.put(ID_ASESORIA, model.getAsesoria());
		cv.put(ID_PREGUNTA, model.getPregunta());
		cv.put(ID_RESUELTAOK, model.getResueltaOk());
		cv.put(ID_FECHACREACIONASESORIA , model.getFechaCreacion());
		cv.put(ID_FECHARESPUESTA, model.getFechaRespuesta());
		cv.put(ID_CURSO_ASESORIA, model.getIdCurso());
		cv.put(ID_RESPUESTA, model.getRespuesta());
		nBD.insert(N_TABLAASESORIAS, null, cv);
	}

	public void crearEntradaLote(ArrayList<Asesorias> obj) {
		
		for(int i=0;i<obj.size();i++){
			ContentValues cv = new ContentValues();
			Asesorias model = obj.get(i);
			cv.put(ID_ASESORIA, model.getAsesoria());
			cv.put(ID_PREGUNTA, model.getPregunta());
			cv.put(ID_RESUELTAOK, model.getResueltaOk());
			cv.put(ID_FECHACREACIONASESORIA , model.getFechaCreacion());
			cv.put(ID_FECHARESPUESTA, model.getFechaRespuesta());
			cv.put(ID_CURSO_ASESORIA, model.getIdCurso());
			cv.put(ID_RESPUESTA, model.getRespuesta());
			nBD.insert(N_TABLAASESORIAS, null, cv);
		}
	}

	public ArrayList<Asesorias> recibir() {

		Cursor c = nBD.rawQuery("Select * from " + N_TABLAASESORIAS, null);
		int iIdCurso = c.getColumnIndex(ID_CURSO_ASESORIA);
		int iAsesoria = c.getColumnIndex(ID_ASESORIA);
		int iFecha = c.getColumnIndex(ID_FECHACREACIONASESORIA);
		int iFechaRespuesta = c.getColumnIndex(ID_FECHARESPUESTA);
		int iPregunta = c.getColumnIndex(ID_PREGUNTA);
		int iResuelta = c.getColumnIndex(ID_RESUELTAOK);
		int iRespuesta = c.getColumnIndex(ID_RESPUESTA);

		for(c.moveToFirst();! c.isAfterLast(); c.moveToNext()){
			Asesorias ase = new Asesorias();
			ase.setAsesoria(c.getInt(iAsesoria));
			ase.setFechaCreacion(c.getString(iFecha));
			ase.setFechaRespuesta(c.getString(iFechaRespuesta));
			ase.setIdCurso(c.getString(iIdCurso));
			ase.setPregunta(c.getString(iPregunta));
			ase.setResueltaOk(c.getInt(iResuelta));
			ase.setRespuesta(c.getString(iRespuesta));
			asesoriasArray.add(ase);
		}
		return asesoriasArray;
	}



	public void borrar() throws SQLException {
		nBD.execSQL("delete from " + N_TABLAASESORIAS);
	}
}
