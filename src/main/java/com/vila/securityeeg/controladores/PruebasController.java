package com.vila.securityeeg.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vila.securityeeg.entitys.Libro;
import com.vila.securityeeg.servicios.LibroServicio;


@Controller
@RequestMapping("/pruebas")
public class PruebasController {
    @Autowired
    private  LibroServicio libroServicio;

    @GetMapping
    public String getMethodName() {
        return "modalExample";
    }

    @GetMapping("/total")
    public ResponseEntity<Integer> totalPaginas() {
        int i=libroServicio.totalPaginasde5en5();
        return new ResponseEntity<>(i,HttpStatus.OK);
    }

    @GetMapping("/libros")
    public ResponseEntity<List<Libro>> datosLibros5en5(@RequestParam String fila) {
        int i=Integer.parseInt(fila);
        List<Libro> listLibros=libroServicio.listar5en5(i);
        return new ResponseEntity<>(listLibros,HttpStatus.OK);
    }
    
    
    
    
}
