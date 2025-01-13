package com.sahil.globe.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sahil.globe.auth.model.Model;
import com.sahil.globe.auth.repository.ModelRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ModelService {
    @Autowired
    private ModelRepository modelRepository;

    public Model addModel(String name) {
        if (modelRepository.findByName(name).isEmpty()) {
            Model model = new Model();
            model.setName(name);
            modelRepository.save(model);
            log.info("Model created: " + model.toString());
            return model; 
        }
        Model model = modelRepository.findByName(name).get();
        log.info("Model already exists: " + model.toString());
        return null;
    }

    public Model getModel(String name) {
        if (modelRepository.findByName(name).isPresent()) {
            Model model = modelRepository.findByName(name).get();
            log.info("Model found: " + model.toString());
            return model;
        }
        log.info("Model not found: " + name);
        return null;
    }

    public boolean deleteModel(String name) {
        if (modelRepository.findByName(name).isPresent()) {
            Model model = modelRepository.findByName(name).get();
            modelRepository.delete(model);
            log.info("Model deleted: " + name);
            return true;
        }
        log.info("Model not found: " + name);
        return false;
    }
}
