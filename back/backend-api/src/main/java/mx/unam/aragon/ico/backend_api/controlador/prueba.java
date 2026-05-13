package mx.unam.aragon.ico.backend_api.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class prueba {
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
