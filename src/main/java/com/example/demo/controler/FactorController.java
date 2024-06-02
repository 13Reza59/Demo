package com.example.demo.controler;

import com.example.demo.model.Factor;
import com.example.demo.serivce.FactorService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FactorController {
    @Autowired
    private FactorService factorService;

    public FactorController( FactorService factorService) {
        this.factorService = factorService;
    }

    @GetMapping("/factors")
    public List<Factor> getAllFactors() {
        return factorService.getAllFactors();
    }

    @GetMapping("/factor/{id}")
    public Factor getAllFactorById( @PathVariable(value = "id") Long id) {
        return factorService.getFactorById( id);
    }

    @PostMapping("/factor/add")
    public Factor createFactor( @RequestBody Factor factor) {
        return factorService.createFactor( factor);
    }

    @PostMapping("/factor/update")
    public String updateFactor( @RequestBody Factor factor) {
        JSONObject output = new JSONObject();

        if( factorService.updateFactor( factor))
            output.put( "result", "OK");
        else
            output.put( "result", "Not Found");

        return output.toString();
    }

    @PostMapping("/factor/delete")
    public String deleteFactor( @RequestBody Factor factor) {
        JSONObject output = new JSONObject();

        if( factorService.deleteFactor( factor.getId()))
            output.put( "result", "OK");
        else
            output.put( "result", "Not Found");

        return output.toString();
    }
}
