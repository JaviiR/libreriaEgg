package com.vila.securityeeg.utileria;

import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public class Utileria {
    public static String guardarArchivo(MultipartFile multiPart, String ruta) throws IOException{
        String nombreOriginal="" ;
        try {
             // Obtenemos el nombre original del archivo.
        nombreOriginal = multiPart.getOriginalFilename();
        nombreOriginal = nombreOriginal.replace(" ", "-");
        String NombreFinal=randomAlphaNumeric(8)+nombreOriginal;
            // Formamos el nombre del archivo para guardarlo en el disco duro.
            File imageFile = new File(ruta + NombreFinal);
            System.out.println("Archivo:" + imageFile.getAbsolutePath());
            // Guardamos fisicamente el archivo en HD.
            multiPart.transferTo(imageFile);
            return NombreFinal;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Metodo para generar una cadena aleatoria de longitudN
     * 
     * @param count
     * @return
     */
    public static String randomAlphaNumeric(int count) {
        String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * CARACTERES.length());
            builder.append(CARACTERES.charAt(character));
        }

        return builder.toString();
    }
}
