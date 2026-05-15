package mx.unam.aragon.ico.frontend_mvc.data;

public class UsuarioData {
    private Integer idUsuario;
    private String correoElectronico;
    private String rol;

    public UsuarioData() {}

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
