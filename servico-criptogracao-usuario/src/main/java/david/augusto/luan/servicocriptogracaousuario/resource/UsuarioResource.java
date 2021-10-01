package david.augusto.luan.servicocriptogracaousuario.resource;

import david.augusto.luan.servicocriptogracaousuario.domain.Usuario;
import david.augusto.luan.servicocriptogracaousuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/usuarios")
@RequiredArgsConstructor
public class UsuarioResource {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    @GetMapping("/listarTodos")
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @PostMapping("/salvar")
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String login,
                                                @RequestParam String senha) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findByLogin(login);

        validaLoginUsuario(optionalUsuario);
        boolean valid = verificaSenhaAposCriptogracao(senha, optionalUsuario);

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }

    private boolean verificaSenhaAposCriptogracao(String senha, Optional<Usuario> optionalUsuario) {

        Usuario usuario = optionalUsuario.get();
        // metodo matches() valida se a senha codificada obtida do armazenamento corresponde a sena bruta
        // enviada depois que ela tambem for codificada
        boolean usuarioValido = encoder.matches(senha, usuario.getSenha());
        return usuarioValido;
    }

    private ResponseEntity<Boolean> validaLoginUsuario(Optional<Usuario> usuario) {
        if (!usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
