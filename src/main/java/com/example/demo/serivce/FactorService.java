package com.example.demo.serivce;

import com.example.demo.model.Factor;

import java.util.List;

public interface FactorService {
    public List<Factor> getAllFactors();

    Factor getFactorById(long id);

    public Factor createFactor(Factor factor);

    boolean updateFactor(Factor factor);

    boolean deleteFactor(long id);
}
