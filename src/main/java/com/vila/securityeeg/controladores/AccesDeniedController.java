package com.vila.securityeeg.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping
public class AccesDeniedController {
    

    @GetMapping("/acces-denied")
    public String accesDenied() {
        return "access-denied";
    }
    
}
