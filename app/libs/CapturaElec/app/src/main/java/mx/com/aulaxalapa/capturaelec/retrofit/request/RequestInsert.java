package mx.com.aulaxalapa.capturaelec.retrofit.request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestInsert {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("clave")
    @Expose
    private String clave;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("apaterno")
    @Expose
    private String apaterno;

    @SerializedName("amaterno")
    @Expose
    private String amaterno;

    @SerializedName("domicilio")
    @Expose
    private String domicilio;

    @SerializedName("colonia")
    @Expose
    private String colonia;

    @SerializedName("seccion")
    @Expose
    private String seccion;

    @SerializedName("ocupacion")
    @Expose
    private String ocupacion;

    @SerializedName("curp")
    @Expose
    private String curp;

    @SerializedName("telefono")
    @Expose
    private String telefono;

    @SerializedName("correo")
    @Expose
    private String correo;

    @SerializedName("facebook")
    @Expose
    private String facebook;

    @SerializedName("twitter")
    @Expose
    private String twitter;

    @SerializedName("emision")
    @Expose
    private String emision;

    @SerializedName("vigencia")
    @Expose
    private String vigencia;

    @SerializedName("sexo")
    @Expose
    private String sexo;

    @SerializedName("casilla")
    @Expose
    private String casilla;

    @SerializedName("latitud")
    @Expose
    private String latitud;

    @SerializedName("longitud")
    @Expose
    private String longitud;

    @SerializedName("escolaridad")
    @Expose
    private String escolaridad;

    @SerializedName("tipo")
    @Expose
    private String tipo;

    @SerializedName("promotor")
    @Expose
    private String promotor;

    @SerializedName("idcoordinador")
    @Expose
    private String idcoordinador;

    @SerializedName("idlider")
    @Expose
    private String idlider;

    @SerializedName("idjefe")
    @Expose
    private String idjefe;

    @SerializedName("fecha")
    @Expose
    private String fecha;


    public RequestInsert() {

    }

    public RequestInsert(String id, String nombre, String apaterno, String amaterno, String domicilio, String colonia,
                         String seccion, String clave, String ocupacion, String curp, String telefono,
                         String correo, String facebook, String twitter, String emision, String vigencia,
                         String sexo, String casilla, String latitud, String longitud, String escolaridad, String tipo,
                         String promotor, String idcoordinador, String idlider, String idjefe, String fecha ) {
        super();
            this.id = id;
            this.clave = clave;
            this.nombre = nombre;
            this.apaterno = apaterno;
            this.amaterno = amaterno;
            this.domicilio = domicilio;
            this.colonia = colonia;
            this.seccion = seccion;
            this.ocupacion = ocupacion;
            this.curp = curp;
            this.telefono = telefono;
            this.correo = correo;
            this.facebook = facebook;
            this.twitter = twitter;
            this.emision = emision;
            this.vigencia = vigencia;
            this.sexo = sexo;
            this.casilla = casilla;
            this.latitud = latitud;
            this.longitud = longitud;
            this.escolaridad = escolaridad;
            this.tipo = tipo;
            this.promotor = promotor;
            this.fecha = fecha;
            this.idcoordinador = idcoordinador;
            this.idlider = idlider;
            this.idjefe = idjefe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getEmision() {
        return emision;
    }

    public void setEmision(String emision) {
        this.emision = emision;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getSexo() {
        return sexo;
    }

    public String getCasilla() {
        return casilla;
    }

    public void setCasilla(String casilla) {
        this.casilla = casilla;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getEscolaridad() {
        return escolaridad;
    }

    public void setEscolaridad(String escolaridad) {
        this.escolaridad = escolaridad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPromotor() {
        return promotor;
    }

    public void setPromotor(String promotor) {
        this.promotor = promotor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdcoordinador() {
        return idcoordinador;
    }

    public void setIdcoordinador(String idcoordinador) {
        this.idcoordinador = idcoordinador;
    }

    public String getIdlider() {
        return idlider;
    }

    public void setIdlider(String idlider) {
        this.idlider = idlider;
    }

    public String getIdjefe() {
        return idjefe;
    }

    public void setIdjefe(String idjefe) {
        this.idjefe = idjefe;
    }
}
