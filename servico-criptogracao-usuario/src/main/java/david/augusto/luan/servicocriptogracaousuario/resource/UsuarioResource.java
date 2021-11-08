package david.augusto.luan.servicocriptogracaousuario.resource;

import david.augusto.luan.servicocriptogracaousuario.domain.Usuario;
import david.augusto.luan.servicocriptogracaousuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuarios")
public class UsuarioResource {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    @GetMapping("/listarTodos")
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/salvar")
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return ResponseEntity.ok(usuario);
    }
}
