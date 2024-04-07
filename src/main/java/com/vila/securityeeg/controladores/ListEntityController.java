package com.vila.securityeeg.controladores;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.vila.securityeeg.entitys.Libro;
import com.vila.securityeeg.repositorios.AutorRepository;
import com.vila.securityeeg.repositorios.EditorialRepository;
import com.vila.securityeeg.repositorios.LibroRepository;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/listar")
public class ListEntityController {

    @Autowired
    private AutorRepository autorRepo;
    @Autowired
    private EditorialRepository editorialRepo;
    @Autowired
    private LibroRepository libroRepo;

    @GetMapping("/autor")
    public String listAutor(Model modelo) {
        modelo.addAttribute("autor",autorRepo.findAll());
        return "/listEntitys/listAutorUser";
    }

    @GetMapping("/libro")
    public String listLibro(Model modelo) {
        modelo.addAttribute("libro",libroRepo.findAll());
        return "/listEntitys/listLibroUser";
    }

    @GetMapping("/editorial")
    public String listEditorial(Model modelo) {
        modelo.addAttribute("editorial",editorialRepo.findAll());
        return "/listEntitys/listEditorialUser";
    }


    //------------------------------------ADMIN-------------------------------------------
    @GetMapping("/autorAdmin")
    public String listAutorAdmin(Model modelo) {
        modelo.addAttribute("autor",autorRepo.findAll());
        return "/listEntitys/listAutorAdmin";
    }

    @GetMapping("/libroAdmin")
    public String listLibroAdmin(Model modelo) {
        modelo.addAttribute("libro",libroRepo.findAll());
        return "/listEntitys/listLibroAdmin";
    }

    @GetMapping("/editorialAdmin")
    public String listEditorialAdmin(Model modelo) {
        modelo.addAttribute("editorial",editorialRepo.findAll());
        return "/listEntitys/listEditorialAdmin";
    }


    
}
