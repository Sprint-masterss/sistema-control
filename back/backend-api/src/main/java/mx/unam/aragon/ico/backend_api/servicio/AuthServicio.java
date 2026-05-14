package mx.unam.aragon.ico.backend_api.servicio;

import mx.unam.aragon.ico.backend_api.dto.LoginDTO;
import mx.unam.aragon.ico.backend_api.entidad.Usuario;
import mx.unam.aragon.ico.backend_api.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Usuario login(LoginDTO loginDTO) {

        Usuario usuario = usuarioRepositorio
                .findByCorreoElectronico(loginDTO.getCorreoElectronico())
                .orElse(null);

        if (usuario == null) {
            return null;
        }

        if (!usuario.getContrasena().equals(loginDTO.getContrasena())) {
            return null;
        }

        return usuario;
    }
}