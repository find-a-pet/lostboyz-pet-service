package com.mtotowamkwe.lostboyzpetservice.dao;

import com.mtotowamkwe.lostboyzpetservice.model.Pet;

import java.util.List;
import java.util.UUID;

public interface PetDao {

    default Pet registerPet(Pet pet) {
        return registerPet(UUID.randomUUID(), pet);
    }

    Pet registerPet(UUID id, Pet pet);

    List<Pet> selectAllPets();

    Pet selectPet(UUID id);

    void deletePet(UUID id);

    Pet updatePet(UUID id, Pet pet);
}
