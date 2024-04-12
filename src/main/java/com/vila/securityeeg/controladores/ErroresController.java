package com.vila.securityeeg.controladores;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErroresController implements ErrorController {

    @RequestMapping(value="/error",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView error(HttpServletRequest httprequest){
        ModelAndView modelo=new ModelAndView("error/error");
        Integer code=getErrorCode(httprequest);
        if(code!=null){
            
            String mensaje="";
            switch (code) {
                case 400:
                    mensaje="El recurso solicitado no existe";
                    break;
                    case 401:
                    mensaje="No se encuentra autorizado";
                break;
                case 403:
                    mensaje="no tiene permisos para acceder al recurso";
                break;
                
                case 404:
                    mensaje="El recurso solicitado no fue encontrado";
                break;
    
                case 500:
                    mensaje="Ocurrió un error interno";
                break;
                
            }
            modelo.addObject("errorCode", code);
            modelo.addObject("errorText", mensaje);
            return modelo;
        }
            modelo.addObject("errorCode", 404);
            modelo.addObject("errorText", "recurso no encontrado");
        return modelo;
    }


    public Integer getErrorCode(HttpServletRequest request){
        return (Integer) request.getAttribute("javax.servlet.error.status_code");
    }
}
