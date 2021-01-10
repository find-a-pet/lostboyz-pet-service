package com.mtotowamkwe.lostboyzpetservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ResponseBody
    @ExceptionHandler(PetNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String petNotFoundHandler(PetNotFoundException pnfex) {
        return pnfex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(AddPetFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String addPetFailedHandler(AddPetFailedException apfe) {
        return apfe.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PetUpdateFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String petUpdateFailedHandler(PetUpdateFailedException pufex) {
        return pufex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PetDeletionFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String petDeletionFailedHandler(PetDeletionFailedException pedefax) {
        return pedefax.getMessage();
    }
}
