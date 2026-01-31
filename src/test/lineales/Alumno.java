package test.lineales;

public class Alumno{
    //atributos
    private String legajo;
    private String nombre;
    private String apellido;
    private String tipoDNI;
    private String numeroDNI;
    private String domicilio;
    private String telefono;
    private String usuarioSIU;
    private String claveSIU;

    //constructores
    public Alumno (String unLegajo){
        this.legajo=unLegajo;
    }
    public Alumno(String unLegajo, String unNombre, String unApellido, String unTipoDNI, String unNumeroDNI, String unDomicilio, String unTelefono, String unUsuarioCIU, String unaClaveCIU){
        this.legajo=unLegajo;
        this.nombre=unNombre;
        this.apellido=unApellido;
        this.tipoDNI=unTipoDNI;
        this.numeroDNI=unNumeroDNI;
        this.domicilio=unDomicilio;
        this.telefono=unTelefono;
        this.usuarioSIU=unUsuarioCIU;
        this.claveSIU=unaClaveCIU;
    }

    //visualizadores
    public String getApellido() {
        return apellido;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDomicilio() {
        return domicilio;
    }
    public String getLegajo() {
        return legajo;
    }
    public String getNumeroDNI() {
        return numeroDNI;
    }
    public String getTelefono() {
        return telefono;
    }
    public String getTipoDNI() {
        return tipoDNI;
    }
    public String getUsuarioSIU() {
        return usuarioSIU;
    }
    //modificadores
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public void setClaveSIU(String claveSIU) {
        this.claveSIU = claveSIU;
    }
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public void setUsuarioSIU(String usuarioSIU) {
        this.usuarioSIU = usuarioSIU;
    }
    
    //propias del tipo

    @Override
    public String toString() {
       return "Alumno{" +
              "legajo='" + legajo + '\'' +
               (nombre != null ? ", nombre='" + nombre + '\'' : "") +
              (apellido != null ? ", apellido='" + apellido + '\'' : "") +
              '}';
    }

}