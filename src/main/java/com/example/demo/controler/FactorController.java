package com.example.demo.controler;

import com.example.demo.model.Factor;
import com.example.demo.serivce.FactorService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/factor")
public class FactorController {
    @Autowired
    private FactorService factorService;

    public FactorController( FactorService factorService) {
        this.factorService = factorService;
    }

    @GetMapping("/all")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Factor> getAllFactors() {
        return factorService.getAllFactors();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Factor getAllFactorById( @PathVariable(value = "id") Long id) {
        return factorService.getFactorById( id);
    }

    @PostMapping("/add")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Factor createFactor( @RequestBody Factor factor) {
        return factorService.createFactor( factor);
    }

    @PostMapping("/update")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String updateFactor( @RequestBody Factor factor) {
        JSONObject output = new JSONObject();

        if( factorService.updateFactor( factor))
            output.put( "result", "OK");
        else
            output.put( "result", "Not Found");

        return output.toString();
    }

    @PostMapping("/delete")
//    @PreAuthorize("hasRole('ADMIN')")
    public String deleteFactor( @RequestBody Factor factor) {
        JSONObject output = new JSONObject();

        if( factorService.deleteFactor( factor.getId()))
            output.put( "result", "OK");
        else
            output.put( "result", "Not Found");

        return output.toString();
    }
}
