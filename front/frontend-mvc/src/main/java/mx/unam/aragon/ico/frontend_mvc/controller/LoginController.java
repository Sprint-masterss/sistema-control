package mx.unam.aragon.ico.frontend_mvc.controller;

import jakarta.servlet.http.HttpSession;
import mx.unam.aragon.ico.frontend_mvc.dto.LoginDTO;
import mx.unam.aragon.ico.frontend_mvc.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
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
    public String admin() {
        return "admin";
    }

    @GetMapping("/alumno")
    public String alumno() {
        return "alumno";
    }

    @GetMapping("/profesor")
    public String profesor() {
        return "profesor";
    }
}