package mx.com.aulaxalapa.capturaelec.data;
//2836075738
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import mx.com.aulaxalapa.capturaelec.common.Constantes;

import static android.provider.BaseColumns._ID;

public class Handler_sqliteU extends SQLiteOpenHelper {
	private String TAG = "Handler_sqlite";
	private String query5;
	private String query4;

	public Handler_sqliteU(Context ctx) {

		super(ctx, "universo", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		query4 = "CREATE TABLE " + Constantes.SECCIONES + "("+
				_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				Constantes.SECCION  + " text," +
				Constantes.CASILLA  + " text," +
				Constantes.IDSECCION + " text)";
		db.execSQL(query4);

		query5 = "CREATE TABLE " + Constantes.UNIVERSOS + "("+
				_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				Constantes.CLAVEELECTOR  + " text," +
				Constantes.PATERNO  + " text," +
				Constantes.MATERNO  + " text," +
				Constantes.NOMBRE  + " text," +
				Constantes.CALLE  + " text," +
				Constantes.SECCION  + " text," +
				Constantes.NUM  + " text," +
				Constantes.COLONIA + " text)";
		db.execSQL(query5);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int version_ant, int version_nue)
	{
		//db.execSQL("DROP TABLE IF EXISTS USUARIOS");
		//onCreate(db);

	}
	public Cursor getIne( String clave){
		String columnas[] = {_ID, Constantes.NOMBRE, Constantes.PATERNO, Constantes.MATERNO, Constantes.SECCION,
				Constantes.CALLE, Constantes.NUM, Constantes.COLONIA};
		String[] ident= { clave };
		Log.e(TAG, "getIne: " + clave );
		Cursor c = this.getReadableDatabase().query(Constantes.UNIVERSOS, columnas, Constantes.CLAVEELECTOR+"=?", ident, null, null, null);
		return c;
	}

	public Cursor getCasillas( String seccion){
		String columnas[] = {_ID, Constantes.SECCION, Constantes.CASILLA, Constantes.IDSECCION};
		String[] ident= { seccion };
		Cursor c = this.getReadableDatabase().query(Constantes.SECCIONES, columnas, Constantes.SECCION+"=?", ident, null, null, null);
		return c;
	}

	public int getRegistros(String queryUp ){
		Cursor c = this.getWritableDatabase().rawQuery(queryUp, null);
		if(c.getCount()>0){
			return c.getCount();
		}else{
			return 0;
		}
	}

	public void abrir() {
		// abrir base
		this.getWritableDatabase();
	}

	public void cerrar() {
		this.close();
	}

	public void getTablas(String queryUp, String nombreClase ){
		Cursor c = this.getWritableDatabase().rawQuery(queryUp, null);
		showData(c, nombreClase);
	}

	public void showData(Cursor c, String nombreClase){
		TAG = nombreClase;
		String query;
		String queryCol;
		if(c.getCount()>0){
			while(c.moveToNext()){
				query = "";
				queryCol = "";
				int num = c.getColumnCount();
				int i=0;
				while( i<num){
					queryCol += c.getColumnName(i)+" | ";
					i++;
				}
				i=0;
				while( i<num){
					query += c.getString(i) + " | ";
					i++;
				}
				Log.e(TAG, queryCol  );
				Log.e(TAG, query  );
			}
		}else{
			Log.e(TAG, "CERO REGISTROS"  );
		}
	}
}
