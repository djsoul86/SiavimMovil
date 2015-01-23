package databaseModels;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.models.Notas;


public class NotasBD extends BaseDatabase {
	public static final String ID_NOTA = "id_nota";
	public static final String ID_FECHANOTA = "fecha_nota";
	public static final String ID_NOMBRENOTA = "nombre_nota";
	public static final String ID_VALORNOTA = "valor_nota";
	public static final String ID_CORTE = "corte_nota";
	public static final String ID_PORCENTAJE = "porcentaje_nota";
	public static final String ID_CEDULA = "cedula_usuario";
	public static final String ID_CURSO_NOTAS = "id_curso_nota";
	private static final String N_BD = DatabaseName;
	private static final int VERSION_BD = VersionDatabase;
	private static final String N_TABLANOTAS = "Notas_Detail";
	
	ArrayList<Notas> notasArray = new ArrayList<Notas>();

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
			db.execSQL("CREATE TABLE " + N_TABLANOTAS + "(" + 
					ID_NOTA + " INTEGER PRIMARY KEY , " +
					ID_FECHANOTA + " TEXT NOT NULL, " +
					ID_NOMBRENOTA + " TEXT NOT NULL, " +
					ID_VALORNOTA + " TEXT NOT NULL, " +
					ID_CORTE + " INTEGER , " +
					ID_CEDULA + " TEXT , " +
					ID_CURSO_NOTAS + " INTEGER , " +
					ID_PORCENTAJE + " INTEGER);"
					);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + N_TABLANOTAS);
			onCreate(db);
		}
	}

	public NotasBD(Context c) {
		nContexto = c;
	}

	public NotasBD abrir(){
		nHelper = new BDHelper(nContexto);
		nBD = nHelper.getWritableDatabase();
		return this;
	}

	public void cerrar() {
		nHelper.close();
	}

	public void crearEntrada(ArrayList<Notas> model) {
		borrar();
		
		for(int i=0;i<model.size();i++){
			Notas n = new Notas();
			ContentValues cv = new ContentValues();
			n = model.get(i);
			cv.put(ID_NOTA, n.getIdNota());
			cv.put(ID_NOMBRENOTA, n.getNombreNota());
			cv.put(ID_FECHANOTA,n.getFechaNota());
			cv.put(ID_PORCENTAJE, n.getPorcentajeNota());
			cv.put(ID_VALORNOTA, n.getNota());
			cv.put(ID_CEDULA, n.getCedula());
			cv.put(ID_CURSO_NOTAS, n.getIdCurso());
			cv.put(ID_CORTE, n.getCorte());
			nBD.insert(N_TABLANOTAS, null, cv);
		}
	}

	public ArrayList<Notas> recibir(String cedula) {
		// TODO Auto-generated method stub
		//String[] columnas = new String[]{ID_TAREA,ID_CURSO,ID_NOMBRETAREA,ID_DESCRIPCIONTAREA,ID_FECHACREACION,ID_FECHAENTREGA};
		Cursor c = nBD.rawQuery("Select * from " + N_TABLANOTAS + " where " + ID_CEDULA + " ='" + cedula + "'", null);

		int iIdCurso = c.getColumnIndex(ID_CURSO_NOTAS);
		int iIdCedula = c.getColumnIndex(ID_CEDULA);
		int iCorte = c.getColumnIndex(ID_CORTE);
		int iFecha = c.getColumnIndex(ID_FECHANOTA);
		int iNombre = c.getColumnIndex(ID_NOMBRENOTA);
		int iNota = c.getColumnIndex(ID_NOTA);
		int iPorcentaje = c.getColumnIndex(ID_PORCENTAJE);
		int iValor = c.getColumnIndex(ID_VALORNOTA);

		for(c.moveToFirst();! c.isAfterLast(); c.moveToNext()){
			Notas not = new Notas();
			not.setIdCurso(c.getInt(iIdCurso));
			not.setIdNota(c.getInt(iNota));
			not.setCedula(c.getString(iIdCedula));
			not.setCorte(c.getInt(iCorte));
			not.setNombreNota(c.getString(iNombre));
			not.setPorcentajeNota(iPorcentaje);
			not.setNota(c.getString(iValor));
			not.setFechaNota(c.getString(iFecha));
			notasArray.add(not);
		}
		return notasArray;
	}



	public void borrar() throws SQLException {
		nBD.execSQL("delete from " + N_TABLANOTAS);
	}
}
