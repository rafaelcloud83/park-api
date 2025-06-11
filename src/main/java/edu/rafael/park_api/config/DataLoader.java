package edu.rafael.park_api.config;

import edu.rafael.park_api.entity.Usuario;
import edu.rafael.park_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
        Usuario user = new Usuario(null, "admin@email.com", "123456", Usuario.Role.ROLE_ADMIN, null, null, null, null);

        Long count = usuarioService.contarUsuarios();
        if (count == 0) {
            usuarioService.salvar(user);
            log.info("Usuário administrador criado com sucesso: {}", user.getUsername());
        } else {
            log.info("Já existem usuários cadastrados.");
        }
    }
}
