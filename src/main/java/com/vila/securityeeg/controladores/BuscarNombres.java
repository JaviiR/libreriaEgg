package com.vila.securityeeg.controladores;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.vila.securityeeg.entitys.Autor;
import com.vila.securityeeg.entitys.Editorial;
import com.vila.securityeeg.entitys.Usuario;
import com.vila.securityeeg.repositorios.AutorRepository;
import com.vila.securityeeg.repositorios.EditorialRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/buscar")
public class BuscarNombres {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private EditorialRepository editorialRepository;

    @GetMapping("/autor")
    public ResponseEntity<List<Autor>> BuscarNombreAutor(@RequestParam String nombre,HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if(logueado==null){
            List<Autor> listaSinLoguear=new ArrayList<>();
            Autor autorSinloguear=new Autor();
            autorSinloguear.setId("id sin loguear");
            autorSinloguear.setNombre("Usuario sin loguear");
            autorSinloguear.setImg("default.jpg");
            listaSinLoguear.add(autorSinloguear);
            return new ResponseEntity<>(listaSinLoguear,HttpStatus.OK);
        }
        else if (logueado.getRol().toString().equals("ADMIN")) {
            List<Autor> listaAutores = new ArrayList<>();
        for (Autor autor : autorRepository.findAll()) {
            if (autor.getNombre().toLowerCase().startsWith(nombre)) {
                listaAutores.add(autor);
            }
        }
        // retornando la respuesta como un json otra forma mas facil podria ser cambiar
        // @Controller y @RequestMapping por @RestController
        return new ResponseEntity<>(listaAutores, HttpStatus.OK);
        }else{
            List<Autor> listaAutorInvalido=new ArrayList<>();
            Autor autorInvalido=new Autor();
            autorInvalido.setId("id no autorizado");
            autorInvalido.setNombre("Usuario no autorizado");
            autorInvalido.setImg("default.jpg");
            listaAutorInvalido.add(autorInvalido);
            return new ResponseEntity<>(listaAutorInvalido,HttpStatus.OK);
    }
    }

    @GetMapping("/editorial")
    public ResponseEntity<List<Editorial>> BuscarNombreEditorial(@RequestParam String nombre,HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if(logueado==null){
            List<Editorial> listaSinLoguear=new ArrayList<>();
            Editorial editorialSinloguear=new Editorial();
            editorialSinloguear.setId("id sin loguear");
            editorialSinloguear.setNombre("Usuario sin loguear");
            editorialSinloguear.setLogo("default.jpg");
            listaSinLoguear.add(editorialSinloguear);
            return new ResponseEntity<>(listaSinLoguear,HttpStatus.OK);
        }
        else if (logueado.getRol().toString().equals("ADMIN")) {
        List<Editorial> listaEditoriales = new ArrayList<>();
        for (Editorial editorial : editorialRepository.findAll()) {
            if (editorial.getNombre().toLowerCase().startsWith(nombre)) {
                listaEditoriales.add(editorial);
            }
        }
        
        // retornando la respuesta como un json otra forma mas facil podria ser cambiar
        // @Controller y @RequestMapping por @RestController
        return new ResponseEntity<>(listaEditoriales, HttpStatus.OK);
    }else{
        List<Editorial> listaEditorialInvalido=new ArrayList<>();
            Editorial editorialInvalido=new Editorial();
            editorialInvalido.setId("id no autorizado");
            editorialInvalido.setNombre("Usuario no autorizado");
            editorialInvalido.setLogo("default.jpg");
            listaEditorialInvalido.add(editorialInvalido);
            return new ResponseEntity<>(listaEditorialInvalido,HttpStatus.OK);
    }
    }

}
