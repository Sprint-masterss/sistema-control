package mx.unam.aragon.ico.backend_api.repositorio;

import mx.unam.aragon.ico.backend_api.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreoElectronico(String correoElectronico);

}