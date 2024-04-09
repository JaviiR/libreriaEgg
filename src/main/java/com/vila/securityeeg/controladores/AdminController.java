package com.vila.securityeeg.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vila.securityeeg.entitys.Usuario;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {
    
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard")
    public String panelAdministrativo(HttpSession session,Model modelo) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if(logueado==null){
            return "redirect:/login";
        }
        else if (!logueado.getRol().toString().equals("ADMIN")) {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            return "redirect:/principal";

        }else{
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            return "/paneles/panelAdmin";
        }
        
    }
    
}
