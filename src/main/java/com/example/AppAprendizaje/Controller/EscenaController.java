package com.example.AppAprendizaje.Controller;

import com.example.AppAprendizaje.Dto.EscenaDto;
import com.example.AppAprendizaje.Dto.RecursoDto;
import com.example.AppAprendizaje.Model.*;
import com.example.AppAprendizaje.Repository.InteraccionRepository;
import com.example.AppAprendizaje.Repository.UsoRecursoRepository;
import com.example.AppAprendizaje.Service.EscenaService;
import com.example.AppAprendizaje.Service.InteraccionService;
import com.example.AppAprendizaje.Service.ProyectoService;
import com.example.AppAprendizaje.Service.RecursoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/escenas")
@CrossOrigin(origins = "https://app-aprendizaje-front.vercel.app")
public class EscenaController {

    //Inyectamos dependencias
    private final EscenaService escenaService;
    private final RecursoService recursoService;
    private  final ProyectoService proyectoService;
    private  final InteraccionService interaccionService;
    private  final UsoRecursoRepository usoRecursoRepository;
    private  final InteraccionRepository interaccionRepository;

    public EscenaController(EscenaService escenaService, RecursoService recursoService, ProyectoService proyectoService, InteraccionService interaccionService, UsoRecursoRepository usoRecursoRepository
    ,InteraccionRepository interaccionRepository) {
        this.escenaService = escenaService;
        this.recursoService = recursoService;
        this.proyectoService = proyectoService;
        this.interaccionService = interaccionService;
        this.usoRecursoRepository = usoRecursoRepository;
        this.interaccionRepository = interaccionRepository;
    }

    //Guardamos las Escenas
    @PostMapping("/guardar")
    public ResponseEntity<List<Escena>> guardarEscenas(@RequestBody List<EscenaDto> escenasDTO) {

        //valida que la lista no este vacia
        if (escenasDTO.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        //obtenemos el id del proyecto de la primera escena
        Integer idProyecto = escenasDTO.get(0).getIdProyecto();

        //buscamos el proyecto
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(idProyecto)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + idProyecto));

        //convertimos el dto en una entidad escena
        List<Escena> escenas = escenasDTO.stream().map(dto -> {
            Escena e = new Escena();
            e.setNombre(dto.getNombre());
            e.setDescripcion(dto.getDescripcion());
            e.setColorFondo(dto.getColorFondo());
            e.setOrden(dto.getOrden());
            e.setFecha(LocalDateTime.now());
            e.setProyecto(proyecto);

            return e;
        }).toList();

        //guardamos las escenas
        List<Escena> guardadas = escenaService.guardarEscenas(escenas);
        return ResponseEntity.ok(guardadas);
    }

    //Subir un archivo asociado a una escena
    @PostMapping("/{idEscena}/subir-recurso")
    public Recurso subirRecurso(
            @PathVariable Integer idEscena,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("nombre") String nombre,
            @RequestParam("tipo") Recurso.TipoRecurso tipo,
            @RequestParam Integer idUsuario, // para organizar carpeta
            @RequestParam(required = false) Double x,
            @RequestParam(required = false) Double y,
            @RequestParam(required = false) Double ancho,
            @RequestParam(required = false) Double alto,
            @RequestParam(required = false) UsoRecurso.Forma forma
    ) throws IOException {
        //buscamos la escena por id
        Escena escena = escenaService.obtenerEscenaPorId(idEscena);

        //creamos el obj recurso
        Recurso recurso = new Recurso();
        recurso.setNombreRecurso(nombre);
        recurso.setTipoRecurso(tipo);

        //guardamos fisicamente y a nivel de bd(relacionado con la escena)
        return recursoService.guardarRecurso(archivo, recurso, escena, idUsuario,  x, y, ancho, alto, forma);
    }

    @PostMapping("/{idEscena}/subir-reaccion")
    public ResponseEntity<?> subirReaccion(
            @PathVariable Integer idEscena,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("nombre") String nombre,
            @RequestParam("tipo") Recurso.TipoRecurso tipo,
            @RequestParam Integer idUsuario,
            @RequestParam Integer idUsoRecurso
    ) {
        try {
            Escena escena = escenaService.obtenerEscenaPorId(idEscena);

            Recurso recurso = new Recurso();
            recurso.setNombreRecurso(nombre);
            recurso.setTipoRecurso(tipo);

            Recurso recursoGuardado = recursoService.guardarRecursoReaccion(archivo, recurso, escena, idUsuario);

            // Buscar si ya existe Interaccion para ese UsoRecurso
            UsoRecurso usoRecurso = usoRecursoRepository.findById(idUsoRecurso)
                    .orElseThrow(() -> new RuntimeException("UsoRecurso no encontrado con id: " + idUsoRecurso));

            Optional<Interaccion> interaccionExistenteOpt = interaccionRepository.findByUsoRecurso(usoRecurso);

            Interaccion interaccion;
            if (interaccionExistenteOpt.isPresent()) {
                // Actualizar recurso existente
                interaccion = interaccionExistenteOpt.get();
                interaccion.setRecurso(recursoGuardado);
            } else {
                // Crear nueva interacción
                interaccion = new Interaccion();
                interaccion.setUsoRecurso(usoRecurso);
                interaccion.setRecurso(recursoGuardado);
            }

            interaccionRepository.save(interaccion);

            return ResponseEntity.ok(recursoGuardado);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir archivo: " + e.getMessage());
        }
    }


    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarEscenas(@RequestBody List<EscenaDto> escenasDto) {
        if (escenasDto.isEmpty()) {
            return ResponseEntity.badRequest().body("No se enviaron escenas");
        }

        Integer idProyecto = escenasDto.get(0).getIdProyecto();

        try {
            List<Escena> escenasActualizadas = escenaService.actualizarEscenas(escenasDto, idProyecto);
            return ResponseEntity.ok(escenasActualizadas);
        } catch (RuntimeException e) {
            // Captura las excepciones
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al actualizar las escenas: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al actualizar las escenas: " + e.getMessage());
        }
    }


    @PostMapping("/{idEscena}/reaccion")
    public ResponseEntity<?> asignarReaccion(
            @PathVariable Integer idEscena,
            @RequestParam Integer idUsoRecurso,
            @RequestParam Integer idRecurso
    ) {

        // validar que la escena exista
        Escena escena = escenaService.obtenerEscenaPorId(idEscena);

        // guardar en tabla interaccion
        Interaccion interaccion = interaccionService.crearInteraccion(idUsoRecurso, idRecurso);

        return ResponseEntity.ok(interaccion);
    }

}
