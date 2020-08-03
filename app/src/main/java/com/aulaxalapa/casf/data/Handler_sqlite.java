package com.aulaxalapa.casf.data;
//2836075738
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aulaxalapa.casf.common.Constantes;

import static android.provider.BaseColumns._ID;

public class Handler_sqlite extends SQLiteOpenHelper {
	private String TAG = "Handler_sqlite";
	private  String query5;
	private String SQLUpdateV1 = "ALTER TABLE " + Constantes.UNIVERSOS + " ADD COLUMN " + Constantes.MES + " text";
	private String SQLUpdateV3 = "ALTER TABLE " + Constantes.UNIVERSOS + " ADD COLUMN " + Constantes.ANIO + " text"
			;
	private String SQLUpdateV2 = "ALTER TABLE " + Constantes.CASF + " ADD COLUMN " + Constantes.MES + " text";
	private String SQLUpdateV4 = "ALTER TABLE " + Constantes.CASF + " ADD COLUMN " + Constantes.ANIO + " text";

	public Handler_sqlite(Context ctx) {
		super(ctx, "mibase", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String query = "CREATE TABLE " + Constantes.CASF + "("+
				_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				Constantes.FOLIOCONT + " text," +
				Constantes.LECTURA1 + " text," +
				Constantes.LECTURA2 + " text," +
				Constantes.LATITUD + " text," +
				Constantes.LONGITUD + " text," +
				Constantes.FOTOMEDIDOR + " text," +
				Constantes.FOTOINCONSISTENCIA1 + " text," +
				Constantes.FOTOINCONSISTENCIA2 + " text," +
				Constantes.FOTOINCONSISTENCIA3 + " text," +
				Constantes.IDINCONSISTENCIA1 + " text," +
				Constantes.IDINCONSISTENCIA2 + " text," +
				Constantes.IDINCONSISTENCIA3 + " text," +
				Constantes.FECHA + " TIMESTAMP default (datetime('now', 'localtime')), " +
				Constantes.GUARDADO + " INTEGER DEFAULT 1, " +
				Constantes.ESTATUS + " INTEGER DEFAULT 1, " +
				Constantes.ANIO + " INTEGER DEFAULT 1, " +
				Constantes.MES + " text)";
		db.execSQL(query);

		String query3 = "CREATE TABLE " + Constantes.IMAGENES + "("+
				_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				Constantes.IMAGEN + " text," +
				Constantes.IDIMAGEN + " text," +
				Constantes.FECHA + " TIMESTAMP default (datetime('now', 'localtime')), " +
				Constantes.ESTATUS + " INTEGER DEFAULT 0)";
		db.execSQL(query3);

		query5 = "CREATE TABLE " + Constantes.UNIVERSOS + "("+
				_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				Constantes.FOLIOCONT  + " text," +
				Constantes.PATERNO  + " text," +
				Constantes.MATERNO  + " text," +
				Constantes.NOMBRE  + " text," +
				Constantes.CALLE  + " text," +
				Constantes.NUM_EXT  + " text," +
				Constantes.RUTA  + " text," +
				Constantes.SECTOR  + " text," +
				Constantes.FOLIOREPARTO  + " text," +
				Constantes.COLONIA  + " text," +
				Constantes.IDMEDIDOR  + " text," +
				Constantes.MEDIDOR  + " text," +
				Constantes.DIAMETRO  + " text," +
				Constantes.MARCA  + " text," +
				Constantes.LECTANT  + " text," +
				Constantes.ANIO + " INTEGER DEFAULT 1, " +
				Constantes.MES + " text)";
		db.execSQL(query5);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int version_ant, int version_nue)
	{
		//db.execSQL("DROP TABLE IF EXISTS USUARIOS");
		//onCreate(db);
		if(version_ant == 1 && version_nue == 2){
			db.execSQL(SQLUpdateV2);
			db.execSQL(SQLUpdateV4);
		}

	}

	/////////////////////////////////    _ID Registro     ////////////////////////////////////////
	public String getId( String guardado ){
		String result = "0";
		String columnas[] = {_ID};
		try{
			Cursor c = this.getReadableDatabase().query(Constantes.CASF, columnas,  Constantes.GUARDADO + "=?", new String[] {guardado}, null, null, null);
			c.moveToLast();
			if ( c.getCount() > 0) {
				result = c.getString(0);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result = "0";
			}

		}
		return result;
	}

	public void insertarCasf(String folioCont, String lectura1, String lectura2, String periodo, String anio)
	{
		ContentValues valores = new ContentValues();

		valores.put(Constantes.FOLIOCONT, folioCont);
		valores.put(Constantes.LECTURA1, lectura1);
		valores.put(Constantes.LECTURA2, lectura2);
		valores.put(Constantes.IDINCONSISTENCIA1, "0");
		valores.put(Constantes.IDINCONSISTENCIA2, "0");
		valores.put(Constantes.IDINCONSISTENCIA3, "0");
		valores.put(Constantes.MES, periodo);
		valores.put(Constantes.ANIO, anio);


		if ( (folioCont != null) && (!folioCont.equals("")) ) {
			this.getWritableDatabase().insert(Constantes.CASF, null, valores);
		}
	}

	public void insertarRegistro(String folioCont, String paterno, String materno, String nombre, String calle,
                               String numExt, String sector, String ruta, String folioReparto, String colonia,
                               String idMedidor, String medidor, String diametro, String marca, String lectAnt, String anio, String periodo)
	{
		ContentValues valores = new ContentValues();

		valores.put(Constantes.FOLIOCONT, folioCont);
		valores.put(Constantes.PATERNO, paterno);
		valores.put(Constantes.MATERNO, materno);
		valores.put(Constantes.NOMBRE, nombre);
		valores.put(Constantes.CALLE, calle);
		valores.put(Constantes.NUM_EXT, numExt);
		valores.put(Constantes.SECTOR, sector);
		valores.put(Constantes.RUTA, ruta);
		valores.put(Constantes.FOLIOREPARTO, folioReparto);
		valores.put(Constantes.COLONIA, colonia);
		valores.put(Constantes.IDMEDIDOR, idMedidor);
		valores.put(Constantes.MEDIDOR, medidor);
		valores.put(Constantes.DIAMETRO, diametro);
		valores.put(Constantes.MARCA, marca);
		valores.put(Constantes.LECTANT, lectAnt);
		valores.put(Constantes.ANIO, anio);
		valores.put(Constantes.MES, periodo);

		if ( (folioCont != null) && (!folioCont.equals("")) ) {
			this.getWritableDatabase().insert(Constantes.UNIVERSOS, null, valores);
		}
	}

	public void actualizaLectura(String id, String folioCont, String lectura1, String lectura2){
		ContentValues valores = new ContentValues();
		valores.put(Constantes.FOLIOCONT, folioCont);
		valores.put(Constantes.LECTURA1, lectura1);
		valores.put(Constantes.LECTURA2, lectura2);
		valores.put(Constantes.GUARDADO, "1");

		this.getWritableDatabase().update(Constantes.CASF, valores, _ID + "=?", new String[] {id});
	}

	public void setCampo (String id, String campo, String dato, String guardado){
		ContentValues valores = new ContentValues();
		valores.put(campo, dato);
		valores.put(Constantes.GUARDADO, guardado);

		this.getWritableDatabase().update(Constantes.CASF, valores, _ID + "=?", new String[] {id});
	}


	public void actualizaGps(String id, String latitud, String longitud){
		ContentValues valores = new ContentValues();
		valores.put(Constantes.LATITUD, latitud);
		valores.put(Constantes.LONGITUD, longitud);
		valores.put(Constantes.GUARDADO, "2");

		this.getWritableDatabase().update(Constantes.CASF, valores, _ID + "=?", new String[] {id});
	}

	public void eliminarRegistro( String id ){
		this.getWritableDatabase().delete( Constantes.UNIVERSOS, _ID + "=?", new String[] {id} );
	}

	public void  eliminarTabla(){
		this.getWritableDatabase().execSQL("DROP TABLE IF EXISTS "+ Constantes.UNIVERSOS);

	}

	public void  crearTabla(){
		query5 = "CREATE TABLE " + Constantes.UNIVERSOS + "("+
				_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				Constantes.FOLIOCONT  + " text," +
				Constantes.PATERNO  + " text," +
				Constantes.MATERNO  + " text," +
				Constantes.NOMBRE  + " text," +
				Constantes.CALLE  + " text," +
				Constantes.NUM_EXT  + " text," +
				Constantes.RUTA  + " text," +
				Constantes.SECTOR  + " text," +
				Constantes.FOLIOREPARTO  + " text," +
				Constantes.COLONIA  + " text," +
				Constantes.IDMEDIDOR  + " text," +
				Constantes.MEDIDOR  + " text," +
				Constantes.DIAMETRO  + " text," +
				Constantes.MARCA  + " text," +
				Constantes.LECTANT  + " text," +
				Constantes.MES  + " text," +
				Constantes.ANIO + " text)";
		this.getWritableDatabase().execSQL(query5);
	}

	public Cursor getRegistros( ){
		String query = "SELECT C."+ _ID+ ", C." + Constantes.FOLIOCONT+ ", " + Constantes.PATERNO+ ", " + Constantes.MATERNO+ ", " +
				Constantes.NOMBRE + ", " + Constantes.CALLE+ ", " + Constantes.NUM_EXT+ ", " + Constantes.SECTOR+ ", " +
				Constantes.RUTA + ", " + Constantes.FOLIOREPARTO+ ", " + Constantes.COLONIA+ ", " + Constantes.IDMEDIDOR+ ", " +
				Constantes.MEDIDOR + ", " + Constantes.DIAMETRO+ ", " + Constantes.MARCA+ ", " +
				Constantes.LECTANT+ ", C." +
				Constantes.ESTATUS +
				" FROM UNIVERSOS U LEFT JOIN CASF C ON U.FOLIOCONT = C.FOLIOCONT ";
		Log.e(TAG, "getRegistros: Registros query 2 " + query );
		Cursor c = this.getWritableDatabase().rawQuery(query, null);
		return c;
	}

	public Cursor getActu( String periodo, String anio ){
		String query = "SELECT "+ _ID+ " FROM CASF WHERE "+Constantes.MES +" = '" + periodo + "' AND "+
				Constantes.ANIO+" = '" + anio + "'" ;
		Log.e(TAG, "getRegistros: Registros query 2 " + query );
		Cursor c = this.getWritableDatabase().rawQuery(query, null);
		return c;
	}
	public Cursor getUniversos( String datos){
		String[] ident= {"%" + datos, "%" + datos};
		String query = "SELECT U."+ _ID+ ", U." + Constantes.FOLIOCONT+ ", " + Constantes.PATERNO+ ", " + Constantes.MATERNO+ ", " +
		Constantes.NOMBRE+ ", " + Constantes.CALLE+ ", " + Constantes.NUM_EXT+ ", " + Constantes.SECTOR+ ", " +
		Constantes.RUTA+ ", " + Constantes.FOLIOREPARTO+ ", " + Constantes.COLONIA+ ", " + Constantes.IDMEDIDOR+ ", " +
		Constantes.MEDIDOR+ ", " + Constantes.DIAMETRO+ ", " + Constantes.MARCA+ ", " + Constantes.LECTANT+ ", " + Constantes.ESTATUS + ", C." +
				_ID+
				" FROM UNIVERSOS U LEFT JOIN CASF C ON U.FOLIOCONT = C.FOLIOCONT AND  U.MES = C.MES AND  U.ANIO = C.ANIO "+
				 " WHERE PATERNO LIKE '"+datos+"%';";
		Cursor c = this.getWritableDatabase().rawQuery(query, null);
		return c;
	}

	public Cursor getUniversos( ){
		String query = "SELECT U."+ _ID+ ", U." + Constantes.FOLIOCONT+ ", " + Constantes.PATERNO+ ", " + Constantes.MATERNO+ ", " +
			Constantes.NOMBRE+ ", " + Constantes.CALLE+ ", " + Constantes.NUM_EXT+ ", " + Constantes.SECTOR+ ", " +
			Constantes.RUTA+ ", " + Constantes.FOLIOREPARTO+ ", " + Constantes.COLONIA+ ", " + Constantes.IDMEDIDOR+ ", " +
			Constantes.MEDIDOR+ ", " + Constantes.DIAMETRO+ ", " + Constantes.MARCA + ", " + Constantes.LECTANT+ ", " +
				Constantes.ESTATUS + ", C." + _ID+
			" FROM UNIVERSOS U LEFT JOIN CASF C ON U.FOLIOCONT = C.FOLIOCONT AND  U.MES = C.MES AND  U.ANIO = C.ANIO ";
		Cursor c = this.getWritableDatabase().rawQuery(query, null);
		return c;
	}

	public Cursor getDatos( String id){
		String columnas[] = { _ID , Constantes.FOLIOCONT, Constantes.PATERNO, Constantes.MATERNO, Constantes.NOMBRE, Constantes.CALLE,
				Constantes.NUM_EXT, Constantes.SECTOR, Constantes.RUTA, Constantes.FOLIOREPARTO, Constantes.COLONIA,
				Constantes.IDMEDIDOR, Constantes.MEDIDOR, Constantes.DIAMETRO, Constantes.MARCA, Constantes.LECTANT,
				Constantes.MES, Constantes.ANIO};
		String[] ident= {id};

		Cursor c = this.getReadableDatabase().query(Constantes.UNIVERSOS, columnas, "_ID=?", ident, null, null, null);
		return c;
	}

	public String getGuardado( ){
		String[] ident= {"0"};
		String result="0";
		String columnas[] = {Constantes.GUARDADO};
		try{
			Cursor c = this.getReadableDatabase().query(Constantes.CASF, columnas,  "guardado!=?", ident, null, null, null);
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex(Constantes.GUARDADO);
				result = c.getString(iu);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="0";
			}

		}
		return result;
	}

	public void setGuardado(String id, String guardado) {
		ContentValues valores = new ContentValues();
		valores.put(Constantes.GUARDADO, guardado);

		this.getWritableDatabase().update(Constantes.CASF, valores, _ID + "=?", new String[] {id});
	}
	public void setEstado(String id) {
		ContentValues valores = new ContentValues();
		valores.put(Constantes.ESTATUS, "1");

		this.getWritableDatabase().update(Constantes.CASF, valores, _ID + "=?", new String[] {id});
	}

	public String fecha(String tabla, String id){
		String result="00-00-0000 00:00:00";
		String columnas[] = {Constantes.FECHA};
		String[] ident= {id};
		try{
			Cursor c = this.getReadableDatabase().query(tabla, columnas,  "_ID=?", ident, null, null, null);
			//c.moveToFirst();
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex(Constantes.FECHA);
				result = c.getString(iu);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="00-00-0000 00:00:00";
			}

		}
		return result;
	}

	public Cursor getEnvio( String ide ){
		String query = "SELECT C."+ _ID+ ", C." + Constantes.FOLIOCONT+ ", " + Constantes.LECTURA1+ ", " + Constantes.LATITUD+ ", " +
				Constantes.LONGITUD+ ", " + Constantes.FOTOMEDIDOR+ ", " + Constantes.FOTOINCONSISTENCIA1+ ", " + Constantes.FOTOINCONSISTENCIA2+ ", " +
				Constantes.FOTOINCONSISTENCIA3+ ", " + Constantes.IDINCONSISTENCIA1+ ", " + Constantes.IDINCONSISTENCIA2+ ", " + Constantes.IDINCONSISTENCIA3+ ", " +
				Constantes.FECHA+ ", " + Constantes.LECTANT+", C." + Constantes.MES+", C." + Constantes.ANIO+" FROM UNIVERSOS U INNER JOIN CASF C ON U.FOLIOCONT = C.FOLIOCONT WHERE ESTATUS = '" + ide +"';";
		Log.e(TAG, "getRegistros: Registros query 2 " + query );
		Cursor c = this.getWritableDatabase().rawQuery(query, null);
		return c;
	}

	public void setEstado(String id, String campo, String estado, String tabla) {
		ContentValues valores = new ContentValues();
		valores.put(Constantes.ESTATUS, estado);
		this.getWritableDatabase().update(tabla, valores, campo+"=?", new String[] {id});
	}

	public void getTablas(String queryUp, String nombreClase ){
		Cursor c = this.getWritableDatabase().rawQuery(queryUp, null);
		showData(c, nombreClase);
	}
	public void showData(Cursor c, String nombreClase){
		TAG = nombreClase;
		String query;
		String queryCol;
		int n = 0;
		while(n<c.getColumnCount()){
			Log.e("UNI", " | " + c.getColumnName(n));
			n++;
		}
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
	public void abrir() {
		// abrir base
		this.getWritableDatabase();
	}

	public void cerrar() {
		this.close();
	}
}
