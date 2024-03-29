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

public class AlertasBD extends BaseDatabase {
	public static final String ID_ALERTA = "_idalerta";
	public static final String ID_TIPOALERTA = "id_tipoalerta";
	public static final String ID_DESCRIPCIONALERTA = "descripcion_alerta";
	public static final String ID_PROCESADAOK = "procesadoOk";
	public static final String ID_CURSO = "id_curso";

	Vector<Alertas> alertasVector = new Vector<Alertas>();

	private static final String N_BD = DatabaseName;
	private static final int VERSION_BD = VersionDatabase;
	private static final String N_TABLA = "Alertas_Detail";
	


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
					ID_CURSO + " TEXT , " +
					ID_DESCRIPCIONALERTA + " TEXT , " +
					ID_TIPOALERTA + " TEXT , " +
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

	public void crearEntrada(ArrayList<Alertas> model) {
		
		try{
			for(int i=0;i<model.size();i++){
				ContentValues cv = new ContentValues();
				Alertas h = new Alertas();
				h = model.get(i);
				cv.put(ID_ALERTA, h.getIdAlerta());
				cv.put(ID_CURSO, h.getIdCurso());
				cv.put(ID_DESCRIPCIONALERTA,h.getDetalleAlerta());
				cv.put(ID_PROCESADAOK, h.getProcesadaOK());
				cv.put(ID_TIPOALERTA,h.getTipoAlerta());
				nBD.insert(N_TABLA, null, cv);
			}
		}catch(SQLException sq){
			sq.getMessage();
		}
	}

	public Vector<Alertas> obtenerAlertas() {
		Cursor c = nBD.rawQuery("Select * from " + N_TABLA, null);
		int iIdCurso = c.getColumnIndex(ID_CURSO);
		int iIdAlerta = c.getColumnIndex(ID_ALERTA);
		int iDescripcion = c.getColumnIndex(ID_DESCRIPCIONALERTA);
		int iProcesada = c.getColumnIndex(ID_PROCESADAOK);
		int iTipoAlerta = c.getColumnIndex(ID_TIPOALERTA);

		for(c.moveToFirst();! c.isAfterLast(); c.moveToNext()){
			Alertas al = new Alertas();
			al.setIdAlerta(c.getInt(iIdAlerta));
			al.setIdCurso(c.getInt(iIdCurso));
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