package databaseModels;

import java.util.ArrayList;
import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.models.Alertas;
import com.example.models.Horarios;



public class AlertasBD {
	public static final String ID_ALERTA = "_idalerta";
	public static final String ID_TIPOALERTA = "id_tipoalerta";
	public static final String ID_DESCRIPCIONALERTA = "descripcion_alerta";
	public static final String ID_PROCESADAOK = "procesadoOk";
	public static final String ID_CURSO = "id_curso";
	
	Vector<Alertas> alertasVector = new Vector<Alertas>();

	private static final String N_BD = "SIAVIMDatabase";
	private static final String N_TABLA = "Alertas_Detail";
	private static final int VERSION_BD = 5;


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
					ID_ALERTA + " INTEGER PRIMARY KEY , " +
					ID_CURSO + " TEXT NOT NULL, " +
					ID_DESCRIPCIONALERTA + " TEXT NOT NULL, " +
					ID_TIPOALERTA + " TEXT NOT NULL, " +
					ID_PROCESADAOK + " TEXT );"
					);

		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + N_TABLA);
			onCreate(db);
		}
	}

	public AlertasBD(Context c) {
		nContexto = c;
	}

	public AlertasBD abrir(){
		nHelper = new BDHelper(nContexto);
		nBD = nHelper.getWritableDatabase();
		return this;
	}

	public void cerrar() {
		nHelper.close();
	}

	public void crearEntrada(ArrayList<Horarios> model) {
		ContentValues cv = new ContentValues();
		borrar();
		for(int i=0;i<model.size();i++){
			Horarios h = new Horarios();
			h = model.get(i);
			cv.put(ID_ALERTA, h.getIDCurso());
			cv.put(ID_CURSO, h.getNombreCurso());
			cv.put(ID_DESCRIPCIONALERTA,h.getIntensidad());
			cv.put(ID_PROCESADAOK, h.getIdProfesor());
			cv.put(ID_TIPOALERTA,h.getLunes());
			nBD.insert(N_TABLA, null, cv);
		}
	}

	public Vector<Alertas> recibir() {
		// TODO Auto-generated method stub
		String[] columnas = new String[]{ID_ALERTA,ID_CURSO,ID_DESCRIPCIONALERTA,ID_PROCESADAOK,ID_TIPOALERTA};
		Cursor c = nBD.rawQuery("Select * from " + N_TABLA, null);

		int iIdCurso = c.getColumnIndex(ID_CURSO);
		int iIdAlerta = c.getColumnIndex(ID_ALERTA);
		int iDescripcion = c.getColumnIndex(ID_DESCRIPCIONALERTA);
		int iProcesada = c.getColumnIndex(ID_PROCESADAOK);
		int iTipoAlerta = c.getColumnIndex(ID_TIPOALERTA);
		
		for(c.moveToFirst();! c.isAfterLast(); c.moveToNext()){
			Alertas al = new Alertas();
			al.setIdAlerta(c.getString(iIdAlerta));
			al.setIdCurso(c.getString(iIdCurso));
			al.setDetalleAlerta(c.getString(iDescripcion));
			al.setProcesadaOK(c.getString(iProcesada));
			al.setTipoAlerta(c.getString(iTipoAlerta));
			alertasVector.add(al);
		}
		return alertasVector;
	}

	public void borrar() throws SQLException {
		nBD.execSQL("delete from " + N_TABLA);
	}
}