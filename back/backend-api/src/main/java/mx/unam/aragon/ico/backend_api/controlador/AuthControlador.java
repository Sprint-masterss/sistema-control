package mx.unam.aragon.ico.backend_api.controlador;

import mx.unam.aragon.ico.backend_api.dto.LoginDTO;
import mx.unam.aragon.ico.backend_api.entidad.Usuario;
import mx.unam.aragon.ico.backend_api.servicio.AuthServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthControlador {

    @Autowired
    private AuthServicio authServicio;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        Usuario usuario = authServicio.login(loginDTO);

        if (usuario == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Correo o contraseña incorrectos");
        }

        return ResponseEntity.ok(usuario);
    }
}