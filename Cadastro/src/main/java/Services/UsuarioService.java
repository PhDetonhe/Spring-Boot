package Services;

import Entitys.Usuario;
import Repositorys.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;
    public List<Usuario> listarTodos(){
        return repository.findAll();
    }

    public  Usuario cadastrar(Usuario usuario){
        return (Usuario) repository.save(usuario);
    }

    public void deletarUsuario(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }




    }





