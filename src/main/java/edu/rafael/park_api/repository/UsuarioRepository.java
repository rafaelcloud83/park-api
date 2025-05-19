package edu.rafael.park_api.repository;

import edu.rafael.park_api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    @Query("SELECT u.role FROM Usuario u WHERE u.username like :username")
    Usuario.Role findRoleByUsername(String username);
}
