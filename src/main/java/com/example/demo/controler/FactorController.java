package com.example.demo.controler;

import ch.qos.logback.classic.Logger;
import com.example.demo.model.Factor;
import com.example.demo.serivce.FactorService;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/factor")
public class FactorController {
    private static final Logger logger
            = (Logger) LoggerFactory.getLogger( FactorController.class);

    @Autowired
    private FactorService factorService;

    public FactorController( FactorService factorService) {
        this.factorService = factorService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<Factor> getAllFactors() {
        logger.info("{} Factors Returned", factorService.getAllFactors().size());

        return factorService.getAllFactors();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Factor getAllFactorById( @PathVariable(value = "id") Long id) {
        logger.info("Factor {} Returned", factorService.getFactorById( id));

        return factorService.getFactorById( id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Factor createFactor( @RequestBody Factor factor) {
        logger.info("Factor {} Created", factor);

        return factorService.createFactor( factor);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public String updateFactor( @RequestBody Factor factor) {
        JSONObject output = new JSONObject();

        if( factorService.updateFactor( factor)) {
            output.put("result", "OK");
            logger.info("Factor {} Updated", factor);

        }else
            output.put( "result", "Not Found");

        return output.toString();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteFactor( @RequestBody Factor factor) {
        JSONObject output = new JSONObject();

        if( factorService.deleteFactor( factor.getId())) {
            output.put("result", "OK");
            logger.info("Factor {} Deleted", factor);

        }else
            output.put( "result", "Not Found");

        return output.toString();
    }
}
