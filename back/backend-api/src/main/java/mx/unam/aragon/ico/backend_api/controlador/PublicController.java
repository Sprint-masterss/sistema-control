package mx.unam.aragon.ico.backend_api.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {
    @GetMapping("/inicio")
    public String inico(){
        return "Public inicio";
    }
}
