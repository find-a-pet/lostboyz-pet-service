package com.mtotowamkwe.lostboyzpetservice.model;

import com.mtotowamkwe.lostboyzpetservice.api.PetController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PetModelAssembler implements RepresentationModelAssembler<Pet, EntityModel<Pet>> {

    @Override
    public EntityModel<Pet> toModel(Pet pet) {
        return EntityModel.of(pet,
                linkTo(methodOn(PetController.class).getPet(pet.getId())).withSelfRel(),
                linkTo(methodOn(PetController.class).getAllPets()).withRel("pets"));
    }
}
