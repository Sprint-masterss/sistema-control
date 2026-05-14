package mx.unam.aragon.ico.frontend_mvc.controlador;

import jakarta.servlet.http.HttpSession;
import mx.unam.aragon.ico.frontend_mvc.data.LoginData;
import mx.unam.aragon.ico.frontend_mvc.data.UsuarioData;
import mx.unam.aragon.ico.frontend_mvc.servicio.AuthServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthControlador {

    @Autowired
    private AuthServicio authServicio;

    @GetMapping("/login")   //get
    public String mostrarLogin(Model model){
        model.addAttribute("loginDTO", new LoginData());
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute LoginData loginDTO,
                                HttpSession session,
                                Model model) {

        UsuarioData usuario = authServicio.login(loginDTO);

        if (usuario == null) {
            model.addAttribute("loginDTO", new LoginData());
            model.addAttribute("error", "Correo o contraseña incorrectos.");
            return "login";
        }

        session.setAttribute("usuario", usuario);
        return "redirect:/bienvenido";
    }

    @GetMapping("/bienvenido")
    public String bienvenido(HttpSession session, Model model) {
        UsuarioData usuario = (UsuarioData) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/login";
        model.addAttribute("usuario", usuario);
        return "bienvenido";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
