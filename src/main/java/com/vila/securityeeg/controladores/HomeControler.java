package com.vila.securityeeg.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vila.securityeeg.servicios.LibroServicio;

@Controller
@RequestMapping("/")
public class HomeControler {

    @Autowired
    private LibroServicio libroServicio;

    @GetMapping()
    public String inicio() {
        
        return "index";
    }

    
    @GetMapping("/principal")
    public String principal(Model modelo) {
        modelo.addAttribute("libro",libroServicio.listarLibros());
        return "/paneles/principal";
    }

    

    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,ModelMap modelo) {
        if(error!=null){
            modelo.put("error","Usuario o Contraseña invalidos");
        }
        return "/registersAndLogins/login";
    }
    

}
