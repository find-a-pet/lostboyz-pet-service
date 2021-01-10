package com.mtotowamkwe.lostboyzpetservice.api;

import com.mtotowamkwe.lostboyzpetservice.exception.AddPetFailedException;
import com.mtotowamkwe.lostboyzpetservice.exception.PetDeletionFailedException;
import com.mtotowamkwe.lostboyzpetservice.exception.PetNotFoundException;
import com.mtotowamkwe.lostboyzpetservice.exception.PetUpdateFailedException;
import com.mtotowamkwe.lostboyzpetservice.model.Pet;
import com.mtotowamkwe.lostboyzpetservice.model.PetModelAssembler;
import com.mtotowamkwe.lostboyzpetservice.service.PetService;
import com.mtotowamkwe.lostboyzpetservice.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PetController implements Constants {

    private static final Logger LOG = LoggerFactory.getLogger(PetController.class);

    private final PetService petService;

    private final PetModelAssembler assembler;

    @Autowired
    public PetController(PetService petService, PetModelAssembler assembler) {
        this.petService = petService;
        this.assembler = assembler;
    }

    @GetMapping(API_PETS_ENDPOINT)
    public ResponseEntity<?> getAllPets() {
        List<EntityModel<Pet>> pets = petService.getAllPets().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Pet>> entityModel = CollectionModel.of(pets,
                linkTo(methodOn(PetController.class).getAllPets()).withSelfRel());

        return ResponseEntity.ok().body(entityModel);
    }

    @GetMapping(API_ID_ENDPOINT)
    public ResponseEntity<?> getPet(@PathVariable(PATH_VARIABLE) UUID id) {
        try {
            Pet pet = petService.getPet(id);
            EntityModel<Pet> entityModel = assembler.toModel(pet);
            return ResponseEntity.ok().body(entityModel);
        } catch (PetNotFoundException pnfe) {
            LOG.error("\nPetNotFoundException at getPet(UUID id):\n", pnfe);
            return new ResponseEntity<>(pnfe.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(API_PETS_ENDPOINT)
    public ResponseEntity<?> addPet(@Valid @NonNull @RequestBody Pet pet) {
        try {
            EntityModel<Pet> entityModel = assembler.toModel(petService.addPet(pet));
            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        } catch (AddPetFailedException apfex) {
            LOG.error("\nAddPetFailedException at addPet(Pet pet):\n", apfex);
            return new ResponseEntity<>(apfex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(API_ID_ENDPOINT)
    public ResponseEntity<?> updatePet(@PathVariable(PATH_VARIABLE) UUID id, @Valid @NonNull @RequestBody Pet pet) {
        try {
            Pet aPet = petService.updatePet(id, pet);
            EntityModel<Pet> aModel = assembler.toModel(aPet);
            return ResponseEntity.ok().body(aModel);
        } catch (PetUpdateFailedException pufex) {
            LOG.error("\nPetUpdateFailedException at updatePet(UUID id, Pet pet):\n", pufex);
            return new ResponseEntity<>(pufex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(API_ID_ENDPOINT)
    public ResponseEntity<?> deletePet(@PathVariable(PATH_VARIABLE) UUID id) {
        try {
            petService.deletePet(id);
        } catch (PetDeletionFailedException pedefax) {
            LOG.error("\nPetDeletionFailedException at deletePet(UUId id):\n", pedefax);
            return new ResponseEntity<>(pedefax.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.noContent().build();
    }
}