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

    public Recurso guardarRecurso(
            MultipartFile archivo,
            Recurso recurso,
            Escena escena,
            Integer idUsuario,
            Double x,
            Double y,
            Double ancho,
            Double alto,
            UsoRecurso.Forma forma
    ) throws IOException {

        // 📌 Ruta absoluta del proyecto (igual que en el controlador)
        String absoluteUploadDir = System.getProperty("user.dir")
                + File.separator + uploadDir;

        // 📌 Carpeta por usuario: uploads/usuario_X
        String userFolderPath = absoluteUploadDir + File.separator + "usuario_" + idUsuario;

        // 📌 Crear carpeta si no existe
        Files.createDirectories(Paths.get(userFolderPath));

        // 📌 Generar nombre único
        String uuid = UUID.randomUUID().toString();
        String original = archivo.getOriginalFilename();
        String extension = original.substring(original.lastIndexOf("."));
        String nombreArchivo = uuid + extension;

        // 📌 Guardar archivo en disco
        File destino = new File(userFolderPath + File.separator + nombreArchivo);
        archivo.transferTo(destino);

        // 📌 Guardar ruta relativa en BD
        recurso.setUrlArchivo("usuario_" + idUsuario + "/" + nombreArchivo);

        // 📌 Guardar Recurso en la BD
        Recurso recursoGuardado = recursoRepository.save(recurso);

        // 📌 Crear UsoRecurso automático
        if (escena != null) {
            UsoRecurso uso = new UsoRecurso();
            uso.setEscena(escena);
            uso.setRecurso(recursoGuardado);

            if (x != null) uso.setX(x);
            if (y != null) uso.setY(y);
            if (ancho != null) uso.setAncho(ancho);
            if (alto != null) uso.setAlto(alto);

            uso.setForma(forma != null ? forma : UsoRecurso.Forma.CUADRADO);

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

        // 📌 Ruta absoluta donde se almacenan los archivos
        String absoluteUploadDir = System.getProperty("user.dir")
                + File.separator + uploadDir;

        // 📌 Carpeta específica: uploads/usuario_X/reacciones
        String userFolder = absoluteUploadDir
                + File.separator + "usuario_" + idUsuario
                + File.separator + "reacciones";

        // Crear carpetas si no existen
        Files.createDirectories(Paths.get(userFolder));

        // 📌 Generar nombre único
        String uuid = UUID.randomUUID().toString();
        String original = archivo.getOriginalFilename();
        String extension = original.substring(original.lastIndexOf("."));
        String nombreArchivo = uuid + extension;

        // 📌 Guardar archivo en disco
        File destino = new File(userFolder + File.separator + nombreArchivo);
        archivo.transferTo(destino);

        // 📌 Guardar ruta relativa en BD (para servir por /uploads/**)
        recurso.setUrlArchivo("usuario_" + idUsuario + "/reacciones/" + nombreArchivo);

        // 📌 Guardar recurso en BD
        return recursoRepository.save(recurso);
    }



}
