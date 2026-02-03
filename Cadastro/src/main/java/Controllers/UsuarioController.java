package Controllers;

import Entitys.Usuario;
import Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> listarUsuarios(){
        return service.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.cadastrar(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deletarUsuario(@PathVariable Long id) {
        service.deletarUsuario(id);
        return
                ResponseEntity.noContent().build();
    }

}
