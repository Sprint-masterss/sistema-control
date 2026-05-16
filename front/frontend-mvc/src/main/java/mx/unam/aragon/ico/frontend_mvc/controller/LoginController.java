package mx.unam.aragon.ico.frontend_mvc.controller;

import jakarta.servlet.http.HttpSession;
import mx.unam.aragon.ico.frontend_mvc.dto.LoginDTO;
import mx.unam.aragon.ico.frontend_mvc.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String correoElectronico,
            @RequestParam String contrasena,
            HttpSession session) {

        try {

            LoginDTO loginDTO =
                    new LoginDTO(correoElectronico, contrasena);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<LoginDTO> request =
                    new HttpEntity<>(loginDTO, headers);

            ResponseEntity<UsuarioDTO> response =
                    restTemplate.postForEntity(
                            "http://backend-api:8080/api/login",
                            request,
                            UsuarioDTO.class
                    );

            UsuarioDTO usuario = response.getBody();

            session.setAttribute("usuario", usuario);

            if (usuario.getRol().equalsIgnoreCase("Administrador")) {
                return "redirect:/admin";
            }

            if (usuario.getRol().equalsIgnoreCase("Profesor")) {
                return "redirect:/profesor";
            }

            if (usuario.getRol().equalsIgnoreCase("Alumno")) {
                return "redirect:/alumno";
            }

            return "redirect:/login";

        } catch (Exception e) {

            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/admin")
    public String admin(HttpSession session, Model model) {

        UsuarioDTO usuario =
                (UsuarioDTO) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.getRol().equalsIgnoreCase("Administrador")) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);

        return "admin";
    }

    @GetMapping("/alumno")
    public String alumno(HttpSession session, Model model) {

        UsuarioDTO usuario =
                (UsuarioDTO) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.getRol().equalsIgnoreCase("Alumno")) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);

        return "alumno";
    }

    @GetMapping("/profesor")
    public String profesor(HttpSession session, Model model) {

        UsuarioDTO usuario =
                (UsuarioDTO) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!usuario.getRol().equalsIgnoreCase("Profesor")) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);

        return "profesor";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}