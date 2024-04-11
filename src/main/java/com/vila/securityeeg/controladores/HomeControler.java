package com.vila.securityeeg.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.vila.securityeeg.entitys.Usuario;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeControler {

    @GetMapping()
    public String inicio() {

        return "index";
    }

    @GetMapping("/principal")
    public String principal(HttpSession session, Model modelo) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        } else if (logueado.getRol().toString().equals("ADMIN")) {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            modelo.addAttribute("idUsuario", logueado.getId());
            return "redirect:/admin/dashboard";

        } else {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            modelo.addAttribute("idUsuario", logueado.getId());
            return "/paneles/principal";
        }

    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo, HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            if (error != null) {
                modelo.put("error", "Usuario o Contraseña invalidos");
            }
            return "/registersAndLogins/login";
        }
        modelo.addAttribute("nombreUsuario", logueado.getNombre());
        return "redirect:/principal";
    }

}
