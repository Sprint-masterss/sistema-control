package mx.unam.aragon.ico.frontend_mvc.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorHome {
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("mensaje", "Front MVC funcionando");
                return "index";
    }
}
