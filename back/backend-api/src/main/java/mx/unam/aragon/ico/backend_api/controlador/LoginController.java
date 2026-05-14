package mx.unam.aragon.ico.backend_api.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class LoginController {
    @GetMapping ("/inicio")
    public String inicio(){
        return "Inicio privado";
    }

   // @GetMapping
}
