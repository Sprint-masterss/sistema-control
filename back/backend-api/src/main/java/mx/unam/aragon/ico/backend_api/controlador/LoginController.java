package mx.unam.aragon.ico.backend_api.controlador;

import mx.unam.aragon.ico.backend_api.dto.LoginDTO;
import mx.unam.aragon.ico.backend_api.entidad.Usuario;
import mx.unam.aragon.ico.backend_api.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        Optional<Usuario> usuarioOpt =
                usuarioRepositorio.findByCorreoElectronico(
                        loginDTO.getCorreoElectronico()
                );

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.getContrasena()
                .equals(loginDTO.getContrasena())) {

            return ResponseEntity
                    .badRequest()
                    .body("Contraseña incorrecta");
        }

        return ResponseEntity.ok(usuario);
    }
}
