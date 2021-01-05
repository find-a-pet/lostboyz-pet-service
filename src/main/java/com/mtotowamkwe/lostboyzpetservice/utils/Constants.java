package com.mtotowamkwe.lostboyzpetservice.utils;

public interface Constants {

    // sql
    String SELECT_ALL_PETS = "SELECT * FROM pets";
    String SELECT_A_PET = "SELECT * FROM pets WHERE id = ?";
    String DELETE_A_PET = "DELETE FROM pets WHERE id = ?";
    String UPDATE_A_PET_IS_FOUND = "UPDATE pets SET is_found = ? WHERE id = ?";
    String REGISTER_A_PET = "INSERT INTO pets" +
            " (id, age, sex, description, name, type, url, breed, last_seen_location)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // exceptions
    String PET_NOT_FOUND_ERROR_MESSAGE = "Could not find the pet ";
    String PET_UPDATE_FAILED_ERROR_MESSAGE = "Could not update the pet ";
    String PET_CREATION_ERROR_MESSAGE = "Could not create the pet ";
    String PET_DELETION_FAILED_ERROR_MESSAGE = "Could not delete the pet ";

    // paths
    String API_PETS_ENDPOINT = "/api/v1/pets";
    String API_ID_ENDPOINT =  API_PETS_ENDPOINT + "/{id}";
    String PATH_VARIABLE = "id";
}
