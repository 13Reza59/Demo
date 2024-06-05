package com.example.demo.serivce;

import ch.qos.logback.classic.Logger;
import com.example.demo.DemoApplication;
import com.example.demo.model.Factor;
import com.example.demo.repository.FactorRepo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FactorServiceImpl implements FactorService {
    private static final Logger logger
            = (Logger) LoggerFactory.getLogger( FactorServiceImpl.class);

    @Autowired
    private FactorRepo factorRepo;

    @Override
    public List<Factor> getAllFactors() {
        List<Factor> factors = new ArrayList<>();
        factorRepo.findAll().forEach( factors::add);
        logger.info("{} Factors Returned", factors.size());
        return factors;
    }

    @Override
    public Factor getFactorById(long id) {
        Optional<Factor> factorDb = factorRepo.findById( id);

        if (factorDb.isPresent()) {
            logger.info("Factor {} Returned", factorDb.get());
            return factorDb.get();

        } else {
            logger.info("Factor {} Not Exist", id);
            return null;
        }
    }

    @Override
    public Factor createFactor(Factor factor) {
        logger.info("Factor {} Created", factor);
        return factorRepo.save(factor);
    }

    @Override
    public boolean updateFactor(Factor factor) {
        Optional<Factor> factorDb = factorRepo.findById( factor.getId());
        if( factorDb.isPresent()) {
            Factor orderUpdate = factorDb.get();
            orderUpdate.setDate( factor.getDate());
            orderUpdate.setOwner( factor.getOwner());
            factorRepo.save( orderUpdate);
            logger.info("Factor {} Updated", factor);
            return true;

        } else {
            logger.info("Factor {} Not Exist to Update", factor.getId());
            return false;
        }
    }

    @Override
    public boolean deleteFactor(long id) {
        if( !factorRepo.existsById( id)) {
            logger.info("Factor {} Not Exist to Delete", id);
            return false;
        }

        factorRepo.deleteById( id);
        logger.info("Factor {} Deleted", id);
        return !factorRepo.existsById( id);
    }
}
