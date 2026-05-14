package mx.unam.aragon.ico.frontend_mvc.modelo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RT {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
