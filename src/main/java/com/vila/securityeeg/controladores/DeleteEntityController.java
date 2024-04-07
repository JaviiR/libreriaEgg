package com.vila.securityeeg.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vila.securityeeg.servicios.AutorServicio;
import com.vila.securityeeg.servicios.EditorialServicio;
import com.vila.securityeeg.servicios.LibroServicio;


@Controller
@RequestMapping("/eliminar")
public class DeleteEntityController {
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private EditorialServicio editorialServicio;
    
    
    @GetMapping("/autor")
    public String eliminarAutor(@RequestParam String autorId,RedirectAttributes redirect) {
        String mensaje="autor eliminado";
        try {
            autorServicio.eliminarAutor(autorId);
            redirect.addFlashAttribute("mensaje",mensaje);
            return "redirect:/listar/autorAdmin";    
        } catch (Exception e) {
            mensaje=e.getMessage();
            redirect.addFlashAttribute("mensaje", mensaje);
            System.out.println("ERROR: "+e.getMessage());
            return "redirect:/listar/autorAdmin";
        }
        
    }

    @GetMapping("/libro")
    public String eliminarLibro(@RequestParam Long libroId,RedirectAttributes redirect) {
        String mensaje="libro eliminado";
        try {
            libroServicio.eliminarLibro(libroId);
            redirect.addFlashAttribute("mensaje",mensaje);
            return "redirect:/listar/libroAdmin";    
        } catch (Exception e) {
            mensaje=e.getMessage();
            redirect.addFlashAttribute("mensaje", mensaje);
            System.out.println("ERROR: "+e.getMessage());
            return "redirect:/listar/libroAdmin";
        }
        
    }

    @GetMapping("/editorial")
    public String eliminarEditorial(@RequestParam String editorialId,RedirectAttributes redirect) {
        String mensaje="editorial eliminada";
        try {
            editorialServicio.eliminarEditorial(editorialId);
            redirect.addFlashAttribute("mensaje",mensaje);
            return "redirect:/listar/editorialAdmin";    
        } catch (Exception e) {
            mensaje=e.getMessage();
            redirect.addFlashAttribute("mensaje", mensaje);
            System.out.println("ERROR: "+e.getMessage());
            return "redirect:/listar/editorialAdmin";
        }
        
    }
}
