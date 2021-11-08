package david.augusto.luan.servicocriptogracaousuario.repository;

import david.augusto.luan.servicocriptogracaousuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
