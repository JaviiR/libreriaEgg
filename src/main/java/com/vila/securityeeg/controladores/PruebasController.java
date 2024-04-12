package com.vila.securityeeg.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.vila.securityeeg.entitys.Autor;
import com.vila.securityeeg.entitys.Editorial;
import com.vila.securityeeg.servicios.AutorServicio;
import com.vila.securityeeg.servicios.EditorialServicio;


@Controller
@RequestMapping("/pruebas")
public class PruebasController {
    
    @Autowired
    private  AutorServicio autorServicio;
    @Autowired
    private  EditorialServicio editorialServicio;

    @GetMapping
    public String getMethodName() {
        return "modalExample";
    }

    @GetMapping("/totalPaginasAutores")
    public ResponseEntity<Integer> totalPaginasAutores() {
        int i=autorServicio.totalPaginasde5en5();
        return new ResponseEntity<>(i,HttpStatus.OK);
    }

    @GetMapping("/totalPaginasEditoriales")
    public ResponseEntity<Integer> totalPaginasEditoriales() {
        int i=editorialServicio.totalPaginasde5en5();
        return new ResponseEntity<>(i,HttpStatus.OK);
    }

    @GetMapping("/autor")
    public ResponseEntity<List<Autor>> datosAutores5en5(@RequestParam String fila) {
        int i=Integer.parseInt(fila);
        List<Autor> listAutores=autorServicio.listar5en5(i);
        return new ResponseEntity<>(listAutores,HttpStatus.OK);
    }

    @GetMapping("/editorial")
    public ResponseEntity<List<Editorial>> datosEditoriales5en5(@RequestParam String fila) {
        int i=Integer.parseInt(fila);
        List<Editorial> listEditoriales=editorialServicio.listar5en5(i);
        return new ResponseEntity<>(listEditoriales,HttpStatus.OK);
    }
    
    
    
    
    
}
