package com.mtotowamkwe.lostboyzpetservice.dao;

import com.mtotowamkwe.lostboyzpetservice.exception.AddPetFailedException;
import com.mtotowamkwe.lostboyzpetservice.exception.PetDeletionFailedException;
import com.mtotowamkwe.lostboyzpetservice.exception.PetNotFoundException;
import com.mtotowamkwe.lostboyzpetservice.exception.PetUpdateFailedException;
import com.mtotowamkwe.lostboyzpetservice.model.Pet;
import com.mtotowamkwe.lostboyzpetservice.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Repository("postgres")
public class PetDataAccessService implements PetDao, Constants {

    private static final Logger LOG = LoggerFactory.getLogger(PetDataAccessService.class);

    private final JdbcTemplate template;

    @Autowired
    public PetDataAccessService(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Pet registerPet(UUID id, Pet pet) {

        Pet aPet = new Pet();

        try {
            int rows = template.update(REGISTER_A_PET,
                    id,
                    pet.getAge(),
                    pet.getSex(),
                    pet.getDescription(),
                    pet.getName(),
                    pet.getType(),
                    pet.getUrl(),
                    pet.getBreed(),
                    pet.getLocation());

            if (rows > 0) aPet = selectPet(id);
        } catch (DataAccessException | PetNotFoundException e) {
            LOG.error("registerPet():", e);
            throw new AddPetFailedException(PET_CREATION_ERROR_MESSAGE + pet + ". Reason is" + e.getMessage());
        }

        return aPet;
    }

    @Override
    public List<Pet> selectAllPets() {
        List<Pet> pets = new ArrayList<>();

        try {
            pets = template.query(
                    SELECT_ALL_PETS,
                    (resultSet, index) -> getPet(resultSet));
        } catch (DataAccessException dax) {
            LOG.error("selectAllPets():", dax);
        }

        return pets;
    }

    @Override
    public Pet selectPet(UUID id) {

        Pet aPet;

        try {
            aPet = template.queryForObject(
                    SELECT_A_PET,
                    new Object[] {id},
                    (resultSet, index) -> getPet(resultSet)
            );
        } catch (DataAccessException dax) {
            LOG.error("selectPet():", dax);
            throw new PetNotFoundException(PET_NOT_FOUND_ERROR_MESSAGE + id + ". Reason is" + dax.getMessage());
        }

        return aPet;
    }

    @Override
    public void deletePet(UUID id) {
        try {
            if (!isNull(selectPet(id))) {
                template.update(DELETE_A_PET, id);
            } else {
                throw new PetNotFoundException(PET_NOT_FOUND_ERROR_MESSAGE + id);
            }
        } catch (DataAccessException | PetNotFoundException ex) {
            LOG.error("deleteUser():", ex);
            throw new PetDeletionFailedException(PET_DELETION_FAILED_ERROR_MESSAGE + id +
                    ". Reason is " + ex.getMessage());
        }
    }

    @Override
    public Pet updatePet(UUID id, Pet pet) {
        try {
            Pet old = selectPet(id);

            if (!isNull(old)) {
                int rows = template.update(
                        UPDATE_A_PET_IS_FOUND,
                        pet.getId(),
                        pet.getAge(),
                        pet.getSex(),
                        pet.getDescription(),
                        pet.getName(),
                        pet.getType(),
                        pet.getUrl(),
                        pet.getBreed(),
                        pet.isFound(),
                        pet.getLocation(),
                        old.getId()
                );
                if (rows > 0) {
                    old.setFound(pet.isFound());
                }
                return old;
            } else {
                throw new PetNotFoundException(PET_NOT_FOUND_ERROR_MESSAGE + id);
            }
        } catch (DataAccessException | PetNotFoundException e) {
            LOG.error("updatePet():", e);
            throw new PetUpdateFailedException(PET_UPDATE_FAILED_ERROR_MESSAGE + pet + ". Cause is " + e.getMessage());
        }
    }

    private Pet getPet(ResultSet resultSet) throws SQLException {
        UUID id = UUID.fromString(resultSet.getString("id"));
        int age = resultSet.getInt("age");
        String sex = resultSet.getString("sex");
        String description = resultSet.getString("description");
        String name = resultSet.getString("name");
        String type = resultSet.getString("type");
        String url = resultSet.getString("url");
        String breed = resultSet.getString("breed");
        boolean found = resultSet.getBoolean("found");
        String location = resultSet.getString("location");

        Pet pet = new Pet();

        pet.setId(id);
        pet.setAge(age);
        pet.setSex(sex.charAt(0));
        pet.setDescription(description);
        pet.setName(name);
        pet.setType(type);
        pet.setUrl(url);
        pet.setBreed(breed);
        pet.setFound(found);
        pet.setLocation(location);

        return  pet;
    }
}
