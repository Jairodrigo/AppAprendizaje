package com.example.AppAprendizaje.Service;

import com.example.AppAprendizaje.Dto.RecursoDto;
import com.example.AppAprendizaje.Model.Escena;
import com.example.AppAprendizaje.Model.Interaccion;
import com.example.AppAprendizaje.Model.Recurso;
import com.example.AppAprendizaje.Model.UsoRecurso;
import com.example.AppAprendizaje.Repository.InteraccionRepository;
import com.example.AppAprendizaje.Repository.RecursoRepository;
import com.example.AppAprendizaje.Repository.UsoRecursoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class RecursoService {

    //inyectamos dependencias
    private final RecursoRepository recursoRepository;
    private final UsoRecursoRepository usoRecursoRepository;
    private final InteraccionRepository interaccionRepository;

    @Value("${app.upload.dir}") // ruta para guardar (application.properties)
    private String uploadDir;

    public RecursoService(RecursoRepository recursoRepository, UsoRecursoRepository usoRecursoRepository,
                          InteraccionRepository interaccionRepository) {
        this.recursoRepository = recursoRepository;
        this.usoRecursoRepository = usoRecursoRepository;
        this.interaccionRepository = interaccionRepository;
    }

    public Recurso guardarRecurso(MultipartFile archivo, Recurso recurso, Escena escena, Integer idUsuario,
                                  Double x, Double y, Double ancho, Double alto, UsoRecurso.Forma forma) throws IOException {
        // Crear carpeta del usuario si no existe
        String userFolder = uploadDir + File.separator + "usuario_" + idUsuario;
        Files.createDirectories(Paths.get(userFolder));

        // Generar UUID para el archivo
        String uuid = UUID.randomUUID().toString();
        String extension = archivo.getOriginalFilename()
                .substring(archivo.getOriginalFilename().lastIndexOf("."));
        String nombreArchivo = uuid + extension;

        // Guardar archivo en disco
        File destino = new File(userFolder + File.separator + nombreArchivo);
        archivo.transferTo(destino);

        // Guardar ruta en recurso
        recurso.setUrlArchivo("usuario_" + idUsuario + "/" + nombreArchivo);

        // Guardar recurso en BD
        Recurso recursoGuardado = recursoRepository.save(recurso);

        // Crear UsoRecurso automático y asociar con la escena
        if (escena != null) {
            UsoRecurso uso = new UsoRecurso();
            uso.setEscena(escena);
            uso.setRecurso(recursoGuardado);
            if (x != null) uso.setX(x);
            if (y != null) uso.setY(y);
            if (ancho != null) uso.setAncho(ancho);
            if (alto != null) uso.setAlto(alto);
            if (forma != null) uso.setForma(forma); // <-- Guardamos la forma
            else uso.setForma(UsoRecurso.Forma.CUADRADO);
            usoRecursoRepository.save(uso);

            // Guardar UsoRecurso
            UsoRecurso usoGuardado = usoRecursoRepository.save(uso);
            recursoGuardado.setUsosRecursos(List.of(usoGuardado));

        }

        return recursoGuardado;
    }

    public Recurso guardarRecursoReaccion(
            MultipartFile archivo,
            Recurso recurso,
            Escena escena,
            Integer idUsuario
    ) throws IOException {

        // Carpeta del usuario
        String userFolder = uploadDir + File.separator + "usuario_" + idUsuario + File.separator + "reacciones";
        Files.createDirectories(Paths.get(userFolder));

        // UUID para archivo
        String uuid = UUID.randomUUID().toString();
        String extension = archivo.getOriginalFilename()
                .substring(archivo.getOriginalFilename().lastIndexOf("."));
        String nombreArchivo = uuid + extension;

        // Guardar físicamente
        File destino = new File(userFolder + File.separator + nombreArchivo);
        archivo.transferTo(destino);

        // URL accesible
        recurso.setUrlArchivo("usuario_" + idUsuario + "/reacciones/" + nombreArchivo);

        // Guardar solo el recurso
        return recursoRepository.save(recurso);
    }


}
