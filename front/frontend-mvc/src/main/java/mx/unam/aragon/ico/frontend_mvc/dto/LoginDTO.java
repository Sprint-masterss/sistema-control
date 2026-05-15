package mx.unam.aragon.ico.frontend_mvc.dto;

public class LoginDTO {

    private String correoElectronico;
    private String contrasena;

    public LoginDTO() {
    }

    public LoginDTO(String correoElectronico, String contrasena) {
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "correoElectronico='" + correoElectronico + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}