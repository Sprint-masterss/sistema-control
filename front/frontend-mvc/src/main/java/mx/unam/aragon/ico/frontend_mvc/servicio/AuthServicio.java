package mx.unam.aragon.ico.frontend_mvc.servicio;

import mx.unam.aragon.ico.frontend_mvc.data.LoginData;
import mx.unam.aragon.ico.frontend_mvc.data.UsuarioData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthServicio {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base.url}")
    private String apiBaseUrl;

    public UsuarioData login(LoginData loginData){
        try {
            ResponseEntity<UsuarioData> respuesta = restTemplate.postForEntity(apiBaseUrl+"/auth/login",loginData, UsuarioData.class);
            return respuesta.getBody();
        }catch (HttpClientErrorException.BadRequest e){
            return null;
        }
    }


}
