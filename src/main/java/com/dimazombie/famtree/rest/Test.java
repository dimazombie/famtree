package com.dimazombie.famtree.rest;

import com.dimazombie.famtree.model.Person;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("test")
@Produces("application/json")
public class Test {
    @GET
    public Person getPerson() {
        return new Person("John",null,"Doe","01.01.1900");
    }
}
