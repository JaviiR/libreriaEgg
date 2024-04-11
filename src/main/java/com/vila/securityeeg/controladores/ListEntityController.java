package com.vila.securityeeg.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.vila.securityeeg.entitys.Usuario;
import com.vila.securityeeg.repositorios.AutorRepository;
import com.vila.securityeeg.repositorios.EditorialRepository;
import com.vila.securityeeg.repositorios.LibroRepository;
import jakarta.servlet.http.HttpSession;

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
    public String listAutor(Model modelo,HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if(logueado==null){
            return "redirect:/login";
        }
        else if (logueado.getRol().toString().equals("ADMIN")) {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            modelo.addAttribute("idUsuario", logueado.getId());
            return "redirect:/listar/autorAdmin";
        }else{

        modelo.addAttribute("nombreUsuario", logueado.getNombre());
        modelo.addAttribute("idUsuario", logueado.getId());
        modelo.addAttribute("autor",autorRepo.findAll());
        return "/listEntitys/listAutorUser";
        }
    }

    @GetMapping("/libro")
    public String listLibro(Model modelo,HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if(logueado==null){
            return "redirect:/login";
        }
        else if (logueado.getRol().toString().equals("ADMIN")) {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            modelo.addAttribute("idUsuario", logueado.getId());
            return "redirect:/listar/libroAdmin";

        }else{
        modelo.addAttribute("nombreUsuario", logueado.getNombre());
        modelo.addAttribute("idUsuario", logueado.getId());
        modelo.addAttribute("libro",libroRepo.findAll());
        return "/listEntitys/listLibroUser";
        }
    }

    @GetMapping("/editorial")
    public String listEditorial(Model modelo,HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if(logueado==null){
            return "redirect:/login";
        }
        else if (logueado.getRol().toString().equals("ADMIN")) {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            modelo.addAttribute("idUsuario", logueado.getId());
            return "redirect:/listar/editorialAdmin";

        }else{
        modelo.addAttribute("nombreUsuario", logueado.getNombre());
        modelo.addAttribute("idUsuario", logueado.getId());
        modelo.addAttribute("editorial",editorialRepo.findAll());
        return "/listEntitys/listEditorialUser";
        }
    }


    //------------------------------------ADMIN-------------------------------------------
    @GetMapping("/autorAdmin")
    public String listAutorAdmin(Model modelo,HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if(logueado==null){
            return "redirect:/login";
        }
        else if (logueado.getRol().toString().equals("USER")) {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            modelo.addAttribute("idUsuario", logueado.getId());
            return "redirect:/listar/autor";

        }else{
        modelo.addAttribute("nombreUsuario", logueado.getNombre());
        modelo.addAttribute("idUsuario", logueado.getId());
        modelo.addAttribute("autor",autorRepo.findAll());
        return "/listEntitys/listAutorAdmin";
        }
    }

    @GetMapping("/libroAdmin")
    public String listLibroAdmin(Model modelo,HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if(logueado==null){
            return "redirect:/login";
        }
        else if (logueado.getRol().toString().equals("USER")) {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            modelo.addAttribute("idUsuario", logueado.getId());
            return "redirect:/listar/libro";

        }else{
        modelo.addAttribute("nombreUsuario", logueado.getNombre());
        modelo.addAttribute("idUsuario", logueado.getId());
        modelo.addAttribute("libro",libroRepo.findAll());
        return "/listEntitys/listLibroAdmin";
        }
    }

    @GetMapping("/editorialAdmin")
    public String listEditorialAdmin(Model modelo,HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if(logueado==null){
            return "redirect:/login";
        }
        else if (logueado.getRol().toString().equals("USER")) {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            modelo.addAttribute("idUsuario", logueado.getId());
            return "redirect:/listar/editorial";

        }else{
        modelo.addAttribute("nombreUsuario", logueado.getNombre());
        modelo.addAttribute("idUsuario", logueado.getId());
        modelo.addAttribute("editorial",editorialRepo.findAll());
        return "/listEntitys/listEditorialAdmin";
        }
    }


    
}
