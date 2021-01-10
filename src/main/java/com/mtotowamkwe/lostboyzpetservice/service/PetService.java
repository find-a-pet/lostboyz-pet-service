package com.mtotowamkwe.lostboyzpetservice.service;

import com.mtotowamkwe.lostboyzpetservice.dao.PetDao;
import com.mtotowamkwe.lostboyzpetservice.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PetService {

    private final PetDao petDao;

    @Autowired
    public PetService(@Qualifier("postgres") PetDao petDao) {
        this.petDao = petDao;
    }

    public Pet addPet(Pet pet) {
        return petDao.registerPet(pet);
    }

    public List<Pet> getAllPets() {
        return petDao.selectAllPets();
    }

    public Pet getPet(UUID id) {
        return petDao.selectPet(id);
    }

    public void deletePet(UUID id) {
        petDao.deletePet(id);
    }

    public Pet updatePet(UUID id, Pet pet) {
        return petDao.updatePet(id, pet);
    }
}
