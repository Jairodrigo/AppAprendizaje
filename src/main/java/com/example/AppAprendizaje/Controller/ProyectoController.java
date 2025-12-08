package com.example.AppAprendizaje.Controller;

import com.example.AppAprendizaje.Dto.ProyectoDto;
import com.example.AppAprendizaje.Dto.ProyectoPublicoDto;
import com.example.AppAprendizaje.Model.Proyecto;
import com.example.AppAprendizaje.Model.Usuario;
import com.example.AppAprendizaje.Repository.ProyectoRepository;
import com.example.AppAprendizaje.Repository.UsuarioRepository;
import com.example.AppAprendizaje.Service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProyectoController {

    //Inyectamos las dependencias
    private final ProyectoService proyectoService;
    private final UsuarioRepository usuarioRepository;
    private final ProyectoRepository proyectoRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Autowired
    public ProyectoController(ProyectoService proyectoService, UsuarioRepository usuarioRepository, ProyectoRepository proyectoRepository) {
        this.proyectoService = proyectoService;
        this.usuarioRepository = usuarioRepository;
        this.proyectoRepository = proyectoRepository;
    }

    // Obtener proyecto por id
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable Integer id) {
        return proyectoService.obtenerProyectoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Listar proyectos por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Proyecto>> listarProyectosPorUsuario(@PathVariable Short usuarioId) {
        return ResponseEntity.ok(proyectoService.listarProyectosPorUsuario(usuarioId));
    }

    //Obtenemos los proyectos publicos
    @GetMapping("/publicos")
    public List<ProyectoPublicoDto> obtenerProyectosPublicos() {
        return proyectoService.obtenerProyectosPublicos();
    }


    //Creamos los los proyectos
    @PostMapping(value = "/crear", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crearProyecto(
            @RequestPart("proyecto") ProyectoDto dto,
            @RequestPart(value = "portada", required = false) MultipartFile portada,
            Authentication authentication) throws IOException {

        if (authentication == null || !(authentication.getPrincipal() instanceof OAuth2User)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Usuario usuario = usuarioRepository.findByCorreo(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Proyecto proyecto = new Proyecto();
        proyecto.setNombreProyecto(dto.getNombreProyecto());
        proyecto.setDescripcion(dto.getDescripcion());
        proyecto.setUsuario(usuario);
        proyecto.setTipo(Proyecto.TipoProyecto.PV);
        proyecto.setEstado(Proyecto.Estado.B);
        proyecto.setFechaProyecto(LocalDateTime.now());
        proyecto.setUrlArchivo(dto.getUrlArchivo());

        // 🔥 SI HAY PORTADA, LA GUARDAMOS EN DISCO
        if (portada != null && !portada.isEmpty()) {

            String userFolder = uploadDir + File.separator + "usuario_" + usuario.getIdUsuario();
            Files.createDirectories(Paths.get(userFolder));

            String uuid = UUID.randomUUID().toString();
            String extension = portada.getOriginalFilename()
                    .substring(portada.getOriginalFilename().lastIndexOf("."));
            String nombreArchivo = uuid + extension;

            File destino = new File(userFolder + File.separator + nombreArchivo);
            portada.transferTo(destino);

            proyecto.setImagenPortada("usuario_" + usuario.getIdUsuario() + "/" + nombreArchivo);
        }


        Proyecto nuevo = proyectoRepository.save(proyecto);

        return ResponseEntity.ok(nuevo);
    }


    @GetMapping("/{id}/completo")
    public ResponseEntity<ProyectoDto> obtenerProyectoCompleto(@PathVariable Integer id) {
        try {
            ProyectoDto proyectoDto = proyectoService.getProyectoCompleto(id);
            return ResponseEntity.ok(proyectoDto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/actualizar-estado")
    public ResponseEntity<?> actualizarEstadoYTipoProyecto(
            @PathVariable Integer id,
            @RequestBody ProyectoDto dto,
            Authentication authentication) {

        // Verificar autenticación
        if (authentication == null || !(authentication.getPrincipal() instanceof OAuth2User)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        // Buscar el proyecto
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(id);
        if (proyectoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }

        Proyecto proyecto = proyectoOpt.get();

        // Actualizar estado y tipo si se enviaron
        if (dto.getEstado() != null) {
            proyecto.setEstado(dto.getEstado());
        }
        if (dto.getTipo() != null) {
            proyecto.setTipo(dto.getTipo());
        }

        // Actualizar la fecha de modificación
        proyecto.setFechaProyecto(LocalDateTime.now());

        // Guardar cambios
        proyectoRepository.save(proyecto);

        return ResponseEntity.ok(Map.of(
                "mensaje", "Proyecto actualizado correctamente",
                "estado", proyecto.getEstado(),
                "tipo", proyecto.getTipo()
        ));

    }
    @GetMapping("/usuario/{usuarioId}/favoritos")
    public ResponseEntity<List<Proyecto>> listarProyectosFavoritos(@PathVariable Short usuarioId) {
        List<Proyecto> proyectos = proyectoService.listarProyectosPorUsuario(usuarioId)
                .stream()
                .filter(Proyecto::isEsFavorito) // filtra solo los favoritos
                .toList();
        return ResponseEntity.ok(proyectos);
    }

}
