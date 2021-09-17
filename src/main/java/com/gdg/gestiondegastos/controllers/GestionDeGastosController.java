package com.gdg.gestiondegastos.controllers;

import com.gdg.gestiondegastos.entities.Grupo;
import com.gdg.gestiondegastos.entities.Movimiento;
import com.gdg.gestiondegastos.entities.Usuario;
import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import com.gdg.gestiondegastos.repositories.GrupoRepository;
import com.gdg.gestiondegastos.repositories.MovimientosRepository;
import com.gdg.gestiondegastos.repositories.PresupuestoRepository;
import com.gdg.gestiondegastos.repositories.UsuarioGrupoRepository;
import com.gdg.gestiondegastos.repositories.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping("/gestion")
public class GestionDeGastosController {

    @Autowired
    private UsuarioRepository repoUsuario;
    @Autowired
    private GrupoRepository repoGrupo;
    @Autowired
    private UsuarioGrupoRepository repoUsuarioGrupo;
    @Autowired
    private PresupuestoRepository repoPresupuesto;
    @Autowired
    private MovimientosRepository repoMovimientos;
   // @Autowired
    //private ModelMapper obj;
    @Autowired
    private PasswordEncoder clave;
            

    @PostMapping("/agregar")
    public String agregarUsuario(Model m, Usuario usuario) {
        m.addAttribute("usuario", new Usuario());
       // repoUsuario.save(usuario);
        return "crearUsuario";
    }
    
    @GetMapping("/principal")
    public String principal(Model m){   
        //m.addAttribute("usuario", new Usuario());    
        return "login";
    }
    
    
    @PostMapping("/crear")
    public String crear(Model m, Usuario usuario){   
           
       usuario.setContrasenya(clave.encode(usuario.getContrasenya()));     
       repoUsuario.save(usuario);
       return "login";
    }
    
    @PostMapping ("/ingresar")
    public String ingresar(Model m, Usuario usuario){   
           
        
       usuario.setContrasenya(clave.encode(usuario.getContrasenya()));     
       //repoUsuario.save(usuario);
       return "principal.html";
    }
    
    
    
    @GetMapping("/grupo/{idGrupo}")
    public String verGrupos(Model m, @PathVariable Integer idGrupo) {
        // m.addAttribute("usuario", )
        // m.addAttribute("nombrePresupuesto",
        // repoPresupuesto.findByIdGrupo(idGrupo).get());
        // m.addAttribute("grupo", repoGrupo.findById(idGrupo));

        // m.addAttribute("grupo", obj.map(repoGrupo.findById(idGrupo),
        // GrupoDto.class));

        m.addAttribute("grupo", repoGrupo.findById(idGrupo).get());
        m.addAttribute("movimientos", repoMovimientos.leerPorGrupo(idGrupo));
        /*
         * m.addAttribute("movimientos",
         * repoGrupo.findById(idGrupo).get().getUsuarioGrupo().stream().filter((t) -> {
         * return
         * t.getMovimiento().stream().filter(x->x.getUsuarioGrupo().getId().equals(t.
         * getId())).collect(Collectors.toList()); }));
         */
        // m.addAttribute("movimientos",
        // repoMovimientos.findAll().stream().filter(x->x.getUsuarioGrupo().getId().equals(repoGrupo.findById(idGrupo).get().getId())));
        // m.addAttribute("usuarioGrupo",
        // repoUsuarioGrupo.findById(idGrupo).get().getMovimiento().get(0).getConcepto());
        // m.addAttribute("usuarioGrupo",
        // repoUsuarioGrupo.findById(idGrupo).get().getUsuario().getNombre());
        // m.addAttribute("usuarioGrupo",
        // repoUsuarioGrupo.findById(idGrupo).get().getMovimiento().get(0).getCantidad());
        m.addAttribute("presupuesto", repoPresupuesto.findByIdGrupo(idGrupo));

        return "grupos";
    }

    @GetMapping("/movimientos")
    public String verMovimientos(Model m, Integer idMovimiento) {
        m.addAttribute("movimiento", repoMovimientos.findById(idMovimiento).get());

        return "movimientos";
    }

    /*
     * @PostMapping("/grupo/{idGrupo}/nuevoMovimiento") public String
     * nuevoMovimientos(Model m, Integer idUsuarioGrupo){ Movimiento mov = new
     * Movimiento();
     * mov.setUsuarioGrupo(repoUsuarioGrupo.findById(idUsuarioGrupo).get());
     * m.addAttribute("movimiento", mov);
     * 
     * return "nuevoMov"; }
     */
    // Ejemplo ded url: http://localhost:8080/gestion/grupo/6
    @GetMapping("/grupo/{idGrupo}/nuevoMovimiento")
    public String nuevoMovimientos(Model m, Integer idUsuarioGrupo) {
        Movimiento mov = new Movimiento();
        mov.setUsuarioGrupo(repoUsuarioGrupo.findById(idUsuarioGrupo).get());
        m.addAttribute("movimiento", mov);

        return "nuevoMov";
    }

    //
    @PostMapping("/grupo/{idGrupo}/guardarMovimiento")
    public String guardarMovimiento(Model m, Movimiento mov, Integer idUsuarioGrupo) {
        UsuarioGrupo ug = repoUsuarioGrupo.findById(idUsuarioGrupo).get();
        mov.setUsuarioGrupo(ug);
        repoMovimientos.save(mov);
        return "redirect:/gestion/grupo/{idGrupo}";
    }

}
