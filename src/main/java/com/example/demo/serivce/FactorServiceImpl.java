package com.example.demo.serivce;

import com.example.demo.model.Factor;
import com.example.demo.repository.FactorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FactorServiceImpl implements FactorService {
    @Autowired
    private FactorRepo factorRepo;

    @Override
    public List<Factor> getAllFactors() {
        List<Factor> factors = new ArrayList<>();
        factorRepo.findAll().forEach( factors::add);
        return factors;
    }

    @Override
    public Factor getFactorById(long id) {
        Optional<Factor> factorDb = factorRepo.findById( id);

        if (factorDb.isPresent()) {
            return factorDb.get();
        } else {
            return null;
        }
    }

    @Override
    public Factor createFactor(Factor factor) {
        return factorRepo.save(factor);
    }

    @Override
    public boolean updateFactor(Factor factor) {
        if( !factorRepo.existsById( factor.getId()))
            return false;

        Optional<Factor> factorDb = factorRepo.findById( factor.getId());

        if( factorDb.isPresent()) {
            Factor orderUpdate = factorDb.get();
            orderUpdate.setDate( factor.getDate());
            orderUpdate.setOwner( factor.getOwner());
            factorRepo.save( orderUpdate);
            return true;

        } else
            return false;
    }

    @Override
    public boolean deleteFactor(long id) {
        factorRepo.deleteById( id);
        return !factorRepo.existsById( id);
    }
}
