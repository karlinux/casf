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

public class Handler_sqlite extends SQLiteOpenHelper {
	private String TAG = "Handler_sqlite";
	private String query5;
	private String query4;

	public Handler_sqlite(Context ctx) {

		super(ctx, "mibase", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String query = "CREATE TABLE " + Constantes.INE + "("+
				_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				Constantes.IDCAPTURISTA + " INTEGER, " +
				Constantes.NOMBRE + " text," +
				Constantes.PATERNO + " text," +
				Constantes.MATERNO + " text," +
				Constantes.DOMICILIO + " text," +
				Constantes.COLONIA + " text," +
				Constantes.LOCALIDAD + " text," +
				Constantes.SECCION + " text," +
				Constantes.CLAVE + " text," +
				Constantes.OCUPACION + " text," +
				Constantes.CURP + " text," +
				Constantes.TELEFONO + " text," +
				Constantes.CORREO + " text," +
				Constantes.FACEBOOK + " text," +
				Constantes.TWITTER + " text," +
				Constantes.EMISION + " text," +
				Constantes.VIGENCIA + " text," +
				Constantes.CASILLA + " text," +
				Constantes.SEXO + " text," +
				Constantes.LATITUD + " text," +
				Constantes.LONGITUD + " text," +
				Constantes.ESCOLARIDAD + " text," +
				Constantes.TIPO + " text," +
				Constantes.PROMOTOR + " text," +
				Constantes.IDCOORDINADOR + " text DEFAULT '1'," +
				Constantes.IDLIDER + " text DEFAULT '1'," +
				Constantes.IDJEFE + " text DEFAULT '1'," +
				Constantes.FECHA + " TIMESTAMP default (datetime('now', 'localtime')), " +
				Constantes.GUARDADO + " INTEGER DEFAULT 1, " +
				Constantes.ACTUALIZADO + " INTEGER DEFAULT 1, " +
				Constantes.ESTATUS + " INTEGER DEFAULT 0)";
		db.execSQL(query);
		String query3 = "CREATE TABLE " + Constantes.IMAGENES + "("+
				_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				Constantes.IMAGEN + " text," +
				Constantes.IDIMAGEN + " text," +
				Constantes.FECHA + " TIMESTAMP default (datetime('now', 'localtime')), " +
				Constantes.ACTUALIZADO + " INTEGER DEFAULT 1, " +
				Constantes.ESTATUS + " INTEGER DEFAULT 0)";
		db.execSQL(query3);

		query4 = "CREATE TABLE " + Constantes.USUARIOS + "("+
				_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				Constantes.CLAVE + " text," +
				Constantes.USUARIO + " text," +
				Constantes.PASSWORD + " text," +
				Constantes.FECHA + " TIMESTAMP default (datetime('now', 'localtime')), " +
				Constantes.ACTUALIZADO + " INTEGER DEFAULT 1, " +
				Constantes.ESTATUS + " INTEGER DEFAULT 0)";
		db.execSQL(query4);

		query5 = "CREATE TABLE " + Constantes.UNIVERSOS + "("+
				_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				Constantes.CLAVEELECTOR  + " text," +
				Constantes.PATERNO  + " text," +
				Constantes.MATERNO  + " text," +
				Constantes.NOMBRE  + " text," +
				Constantes.CALLE  + " text," +
				Constantes.SECCION  + " text," +
				Constantes.NUM_EXT  + " text," +
				Constantes.COLONIA + " text)";
		db.execSQL(query5);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int version_ant, int version_nue)
	{
		//db.execSQL("DROP TABLE IF EXISTS USUARIOS");
		//onCreate(db);

	}

	public void insertarRegistro(String nombre, String paterno, String materno, String seccion, String clave_elector)
	{
		ContentValues valores = new ContentValues();
		valores.put(Constantes.NOMBRE, nombre);
		valores.put(Constantes.PATERNO, paterno);
		valores.put(Constantes.MATERNO, materno);
		valores.put(Constantes.SECCION, seccion);
		valores.put(Constantes.CLAVEELECTOR, clave_elector);

		if ( (nombre != null) && (!nombre.equals("")) ) {
			this.getWritableDatabase().insert(Constantes.UNIVERSOS , null, valores);
		}
	}
	public void borrar(String id, String tabla) {
		// TODO Auto-generated method stub
		ContentValues valores = new ContentValues();
		String ide[] = {id};
		this.getWritableDatabase().delete(tabla,"_ID=?", ide);
	}

	public Cursor getIne( String clave){
		String columnas[] = {_ID, Constantes.NOMBRE, Constantes.PATERNO, Constantes.MATERNO, Constantes.SECCION};
		String[] ident= { clave };
		Log.e(TAG, "getIne: " + clave );
		Cursor c = this.getReadableDatabase().query(Constantes.UNIVERSOS, columnas, Constantes.CLAVEELECTOR+"=?", ident, null, null, null);
		return c;
	}

	public void insertar(
			String clave,
			String nombre,
			String paterno,
			String materno,
			String domicilio,
			String colonia,
			String seccion,
			String ocupacion,
			String curp,
			String telefono,
			String correo,
			String facebook,
			String twitter,
			String emision,
			String vigencia,
			String sexo,
			String casilla,
			String escolaridad,
			String promotor,
			String idcoordinador,
			String idlider,
			String idjefe
			)
	{
		ContentValues valores = new ContentValues();
		valores.put(Constantes.NOMBRE, nombre);
		valores.put(Constantes.PATERNO, paterno);
		valores.put(Constantes.MATERNO, materno);
		valores.put(Constantes.DOMICILIO, domicilio);
		valores.put(Constantes.COLONIA, colonia);
		valores.put(Constantes.LOCALIDAD, colonia);
		valores.put(Constantes.SECCION, seccion);
		valores.put(Constantes.CLAVE, clave);
		valores.put(Constantes.OCUPACION, ocupacion);
		valores.put(Constantes.CURP, curp);
		valores.put(Constantes.TELEFONO, telefono);
		valores.put(Constantes.CORREO, correo);
		valores.put(Constantes.FACEBOOK, facebook);
		valores.put(Constantes.TWITTER, twitter);
		valores.put(Constantes.EMISION, emision);
		valores.put(Constantes.VIGENCIA, vigencia);
		valores.put(Constantes.SEXO,sexo);
		valores.put(Constantes.CASILLA,casilla);
		valores.put(Constantes.ESCOLARIDAD, escolaridad);
		valores.put(Constantes.PROMOTOR, promotor);
		valores.put(Constantes.IDCOORDINADOR, idcoordinador);
		valores.put(Constantes.IDLIDER, idlider);
		valores.put(Constantes.IDJEFE, idjefe);

		if ( (nombre != null) && (!nombre.equals("")) ) {
			this.getWritableDatabase().insert(Constantes.INE , null, valores);
		}
	}

	public void insertarUsuario(String usuario, String password, String clave)
	{
		ContentValues valores = new ContentValues();
		valores.put(Constantes.USUARIO, usuario);
		valores.put(Constantes.PASSWORD, password);
		valores.put(Constantes.CLAVE, clave);

		if ( (Constantes.USUARIO != null) && (!Constantes.USUARIO.equals("")) ) {
			this.getWritableDatabase().insert(Constantes.USUARIOS , null, valores);
		}
	}

	public void insertarFoto(String imagen, String idimagen)
	{
		ContentValues valores = new ContentValues();
		valores.put(Constantes.IMAGEN, imagen);
		valores.put(Constantes.IDIMAGEN, idimagen);
		valores.put(Constantes.ACTUALIZADO, "1");
		valores.put(Constantes.ESTATUS, "1");

		if ( (imagen != null) && (!imagen.equals("")) ) {
			this.getWritableDatabase().insert(Constantes.IMAGENES , null, valores);
		}
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

	///////////////////////////////    Registro  2da parte   ////////////////////////////////////////

	public void actualzaRegistro(String id, String domicilioactual, String correo, String telefono, String guardado)
	{
		ContentValues valores = new ContentValues();
		valores.put(Constantes.CORREO, correo);
		valores.put(Constantes.TELEFONO, telefono);
		valores.put(Constantes.DOMICILIOACTUAL, domicilioactual);
		valores.put(Constantes.GUARDADO, guardado);

		this.getWritableDatabase().update(Constantes.INE, valores, _ID + "=?", new String[] {id});
	}

	///////////////////////////////    Registro  2da parte   ////////////////////////////////////////

	public void actualzaEditar(String id, String nombre, String paterno, String materno,
							   String domicilio, String municipio, String localidad, String seccion, String clave,
							   String domicilioactual, String correo, String telefono, String actualizado ){

		ContentValues valores = new ContentValues();
		valores.put(Constantes.NOMBRE, nombre);
		valores.put(Constantes.PATERNO, paterno);
		valores.put(Constantes.MATERNO, materno);
		valores.put(Constantes.CORREO, correo);
		valores.put(Constantes.DOMICILIO, domicilio);
		valores.put(Constantes.MUNICIPIO, municipio);
		valores.put(Constantes.LOCALIDAD, localidad);
		valores.put(Constantes.SECCION, seccion);
		valores.put(Constantes.CLAVE, clave);
		valores.put(Constantes.TELEFONO, telefono);
		valores.put(Constantes.DOMICILIOACTUAL, domicilioactual);
		valores.put(Constantes.ESTATUS, "1");
		valores.put(Constantes.ACTUALIZADO, actualizado);

		this.getWritableDatabase().update(Constantes.INE, valores, _ID + "=?", new String[] {id});
	}

	public void actualizaImagen( String id, String actualizado ){
		String result="0";
		String[] idimagen = { id };
		ContentValues valores = new ContentValues();
		valores.put(Constantes.ESTATUS, "1");
		valores.put(Constantes.ACTUALIZADO, actualizado);

		this.getWritableDatabase().update(Constantes.IMAGENES, valores, Constantes.IDIMAGEN + "=?", idimagen );
	}

	/////////////////////////////////    _ID Registro     ////////////////////////////////////////
	public int id(){
		int result=0;
		String columnas[] = {_ID};
		try{
			Cursor c = this.getReadableDatabase().query(Constantes.INE, columnas,  null, null, null, null, null);
			c.moveToLast();
			if ( c.getCount() > 0) {
				result = c.getInt(0);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result=0;
			}

		}
		return result;
	}

	public void setEstado(String id, String campo, String estado, String tabla) {
		ContentValues valores = new ContentValues();
		valores.put(Constantes.ESTATUS, estado);
		this.getWritableDatabase().update(tabla, valores, campo+"=?", new String[] {id});
	}

	public void setEstado(String id, String campo, String estado, String tabla, String idcapturista) {
		ContentValues valores = new ContentValues();
		valores.put(Constantes.ESTATUS, estado);
		this.getWritableDatabase().update(tabla, valores, campo+"=?", new String[] {id});
	}

	public String getGuardado(){
		String result="0";
		String columnas[] = {Constantes.GUARDADO};
		try{
			Cursor c = this.getReadableDatabase().query(Constantes.INE, columnas,  null, null, null, null, null);
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

	public void setGuardado( String guardado, String id){
		ContentValues valores = new ContentValues();
		valores.put(Constantes.GUARDADO, guardado);
		this.getWritableDatabase().update(Constantes.INE, valores, _ID + "=?", new String[] {id});
	}

	public String getCampo(String campo ){
		String result="0";
		String columnas[] = {campo};
		try{
			Cursor c = this.getReadableDatabase().query(Constantes.INE, columnas,  null, null, null, null, null);
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex(campo);
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

	public String getFoto( String id){
		String result="0";
		String[] idimagen = { id };
		String columnas[] = { Constantes.IMAGEN };
		try{
			Cursor c = this.getReadableDatabase().query(Constantes.IMAGENES, columnas,  Constantes.IDIMAGEN+"=?", idimagen, null, null, null);
			c.moveToLast();
			Log.e("Select foto", "getFoto: "+ c.getCount() );
			if ( c.getCount() > 0) {

				int iu;
				iu = c.getColumnIndex(Constantes.IMAGEN);
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

	public String getImagen( String id){
		String result="0";
		String columnas[] = {Constantes.IMAGEN};
		try{
			Cursor c = this.getReadableDatabase().query(Constantes.IMAGENES, columnas,  _ID + "=?", new String[] {id}, null, null, null);
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex(Constantes.IMAGEN);
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

	public Cursor getDatos(){
		String columnas[] = {_ID, Constantes.NOMBRE, Constantes.PATERNO, Constantes.MATERNO,
				Constantes.CLAVE, Constantes.TELEFONO, Constantes.CORREO, Constantes.FECHA};
		Cursor c = this.getReadableDatabase().query(Constantes.INE, columnas, null, null, null, null, null);
		return c;
	}
	public Cursor getDatos( String ide){
		String columnas[] = {_ID, Constantes.NOMBRE, Constantes.PATERNO, Constantes.MATERNO, Constantes.DOMICILIO,
				Constantes.COLONIA, Constantes.CLAVE, Constantes.SECCION,
				Constantes.CASILLA, Constantes.SEXO, Constantes.ESCOLARIDAD, Constantes.OCUPACION,
				Constantes.CURP, Constantes.FECHA, Constantes.TELEFONO, Constantes.CORREO, Constantes.FACEBOOK, Constantes.TWITTER,
				Constantes.EMISION, Constantes.VIGENCIA, Constantes.ESTATUS};
		String[] ident= { ide };
		Cursor c = this.getReadableDatabase().query(Constantes.INE, columnas, _ID+"=?", ident, null, null, null);
		return c;
	}

	public void  eliminarTabla(){
		this.getWritableDatabase().execSQL("DROP TABLE IF EXISTS "+ Constantes.UNIVERSOS);

	}

	public void  crearTabla(){
		query5 = "CREATE TABLE " + Constantes.UNIVERSOS + "("+
				_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				Constantes.CLAVEELECTOR  + " text," +
				Constantes.PATERNO  + " text," +
				Constantes.MATERNO  + " text," +
				Constantes.NOMBRE  + " text," +
				Constantes.CALLE  + " text," +
				Constantes.SECCION  + " text," +
				Constantes.NUM_EXT  + " text," +
				Constantes.COLONIA + " text)";
		this.getWritableDatabase().execSQL(query5);
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

	public int getRegistros(String queryUp ){
		Cursor c = this.getWritableDatabase().rawQuery(queryUp, null);
		if(c.getCount()>0){
			return c.getCount();
		}else{
			return 0;
		}
	}

	public void actualizaGps(String id, String latitud, String longitud){
		ContentValues valores = new ContentValues();
		valores.put(Constantes.LATITUD, latitud);
		valores.put(Constantes.LONGITUD, longitud);
		valores.put(Constantes.GUARDADO, "0");
		valores.put(Constantes.ESTATUS, "1");

		this.getWritableDatabase().update(Constantes.INE, valores, Constantes.CLAVE + "=?", new String[] {id});
	}

	public Cursor getEnvio( String ide){
		String columnas[] = {
				_ID,
				Constantes.NOMBRE,
				Constantes.PATERNO,
				Constantes.MATERNO,
				Constantes.DOMICILIO,
				Constantes.COLONIA,
				Constantes.SECCION,
				Constantes.CLAVE,
				Constantes.OCUPACION,
				Constantes.CURP,
				Constantes.TELEFONO,
				Constantes.CORREO,
				Constantes.FACEBOOK,
				Constantes.TWITTER,
				Constantes.EMISION,
				Constantes.VIGENCIA,
				Constantes.SEXO,
				Constantes.CASILLA,
				Constantes.LATITUD,
				Constantes.LONGITUD,
				Constantes.ESCOLARIDAD,
				Constantes.TIPO,
				Constantes.PROMOTOR,
				Constantes.IDCOORDINADOR,
				Constantes.IDLIDER,
				Constantes.IDJEFE,
				Constantes.FECHA
		};
		String[] ident= { ide };
		Cursor c = this.getReadableDatabase().query(Constantes.INE, columnas, Constantes.ESTATUS + "=?", ident, null, null, null, "0,30");
		return c;
	}

	public Cursor getImagenes( String ide){
		String columnas[] = { Constantes.IMAGEN };
		String[] ident= { ide };
		Cursor c = this.getReadableDatabase().query(Constantes.IMAGENES, columnas, Constantes.ESTATUS + "=?", ident, null, null, null, null);
		return c;
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
