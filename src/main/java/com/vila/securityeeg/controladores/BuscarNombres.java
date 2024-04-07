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
import com.vila.securityeeg.repositorios.AutorRepository;
import com.vila.securityeeg.repositorios.EditorialRepository;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/buscar")
public class BuscarNombres {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private EditorialRepository editorialRepository;

    @GetMapping("/autor")
    public ResponseEntity<List<Autor>> BuscarNombreAutor(@RequestParam String nombre) {
        List<Autor> listaAutores = new ArrayList<>();
        for (Autor autor : autorRepository.findAll()) {
            if (autor.getNombre().toLowerCase().startsWith(nombre)) {
                listaAutores.add(autor);
            }
        }
        // retornando la respuesta como un json otra forma mas facil podria ser cambiar
        // @Controller y @RequestMapping por @RestController
        return new ResponseEntity<>(listaAutores, HttpStatus.OK);
    }

    @GetMapping("/editorial")
    public ResponseEntity<List<Editorial>> BuscarNombreEditorial(@RequestParam String nombre) {
        List<Editorial> listaEditoriales = new ArrayList<>();
        for (Editorial editorial : editorialRepository.findAll()) {
            if (editorial.getNombre().toLowerCase().startsWith(nombre)) {
                listaEditoriales.add(editorial);
            }
        }
        
        // retornando la respuesta como un json otra forma mas facil podria ser cambiar
        // @Controller y @RequestMapping por @RestController
        return new ResponseEntity<>(listaEditoriales, HttpStatus.OK);
    }

}
