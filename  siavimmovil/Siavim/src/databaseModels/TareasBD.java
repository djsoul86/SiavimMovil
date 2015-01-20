package databaseModels;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.models.Tareas;

public class TareasBD {
	public static final String ID_TAREA = "id_tarea";
	public static final String ID_NOMBRETAREA = "nombre_tarea";
	public static final String ID_DESCRIPCIONTAREA = "descripcion_tarea";
	public static final String ID_FECHAENTREGA = "fecha_entrega";
	public static final String ID_FECHACREACION = "fecha_creacion";
	public static final String ID_CURSO = "id_curso";

	ArrayList<Tareas> TareasVector = new ArrayList<Tareas>();

	private static final String N_BD = "SIAVIMDatabase";
	private static final String N_TABLA = "Tareas_Detail";
	private static final int VERSION_BD = 13;


	private BDHelper nHelper;
	private final Context nContexto;
	private SQLiteDatabase nBD;



	private static class BDHelper extends SQLiteOpenHelper{

		public BDHelper(Context context) {
			super(context, N_BD, null, VERSION_BD);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + N_TABLA + "(" + 
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
			onCreate(db);
		}
	}

	public TareasBD(Context c) {
		nContexto = c;
	}

	public TareasBD abrir(){
		nHelper = new BDHelper(nContexto);
		nBD = nHelper.getWritableDatabase();
		
		return this;
	}

	public void cerrar() {
		nHelper.close();
	}

	public void crearEntrada(ArrayList<Tareas> model) {
		ContentValues cv = new ContentValues();
		borrar();
		for(int i=0;i<model.size();i++){
			Tareas h = new Tareas();
			h = model.get(i);
			cv.put(ID_TAREA, h.getIdTarea());
			cv.put(ID_CURSO, h.getIdCurso());
			cv.put(ID_NOMBRETAREA, h.getNombreTarea());
			cv.put(ID_DESCRIPCIONTAREA,h.getDescripcionTarea());
			cv.put(ID_FECHACREACION, h.getFechaCreacion());
			cv.put(ID_FECHAENTREGA,h.getFechaEntrega());
			nBD.insert(N_TABLA, null, cv);
		}
	}

	public ArrayList<Tareas> recibir() {
		// TODO Auto-generated method stub
		//String[] columnas = new String[]{ID_TAREA,ID_CURSO,ID_NOMBRETAREA,ID_DESCRIPCIONTAREA,ID_FECHACREACION,ID_FECHAENTREGA};
		Cursor c = nBD.rawQuery("Select * from " + N_TABLA, null);

		int iIdCurso = c.getColumnIndex(ID_CURSO);
		int iIdTarea = c.getColumnIndex(ID_TAREA);
		int iNombreTarea = c.getColumnIndex(ID_NOMBRETAREA);
		int iDescripcion = c.getColumnIndex(ID_DESCRIPCIONTAREA);
		int iFechaCreacion = c.getColumnIndex(ID_FECHACREACION);
		int iFechaEntrega = c.getColumnIndex(ID_FECHAENTREGA);

		for(c.moveToFirst();! c.isAfterLast(); c.moveToNext()){
			Tareas tar = new Tareas();
			tar.setIdCurso(c.getInt(iIdCurso));
			tar.setIdTarea(c.getInt(iIdTarea));
			tar.setNombreTarea(c.getString(iNombreTarea));
			tar.setDescripcionTarea(c.getString(iDescripcion));
			tar.setFechaEntrega(c.getString(iFechaEntrega));
			tar.setFechaCreacion(c.getString(iFechaCreacion));
			TareasVector.add(tar);
		}
		return TareasVector;
	}
	
	public ArrayList<Tareas> obtenerTareasPorIDCurso(int curso) {
		// TODO Auto-generated method stub
		//String[] columnas = new String[]{ID_TAREA,ID_CURSO,ID_NOMBRETAREA,ID_DESCRIPCIONTAREA,ID_FECHACREACION,ID_FECHAENTREGA};
		Cursor c = nBD.rawQuery("Select * from " + N_TABLA + " where " + ID_CURSO + " = " + curso, null);

		int iIdCurso = c.getColumnIndex(ID_CURSO);
		int iIdTarea = c.getColumnIndex(ID_TAREA);
		int iNombreTarea = c.getColumnIndex(ID_NOMBRETAREA);
		int iDescripcion = c.getColumnIndex(ID_DESCRIPCIONTAREA);
		int iFechaCreacion = c.getColumnIndex(ID_FECHACREACION);
		int iFechaEntrega = c.getColumnIndex(ID_FECHAENTREGA);

		for(c.moveToFirst();! c.isAfterLast(); c.moveToNext()){
			Tareas tar = new Tareas();
			tar.setIdCurso(c.getInt(iIdCurso));
			tar.setIdTarea(c.getInt(iIdTarea));
			tar.setNombreTarea(c.getString(iNombreTarea));
			tar.setDescripcionTarea(c.getString(iDescripcion));
			tar.setFechaEntrega(c.getString(iFechaEntrega));
			tar.setFechaCreacion(c.getString(iFechaCreacion));

			TareasVector.add(tar);
		}
		return TareasVector;
	}

	public void borrar() throws SQLException {
		nBD.execSQL("delete from " + N_TABLA);
	}
}
