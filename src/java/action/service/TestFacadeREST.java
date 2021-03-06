/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import model.jpa.Test;

/**
 *
 * @author csalas
 */
@Stateless
@Path("model.jpa.test")
public class TestFacadeREST extends AbstractFacade<Test> {
    @PersistenceContext(unitName = "TodoTestWebPU")
    private EntityManager em;

    public TestFacadeREST() {
        super(Test.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Test entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Test entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Test find(@PathParam("id") Long id) {
        return super.find(id);
    }
    
    @GET
    @Path("active/{dni}")
    @Produces({"application/json"})
    public List<Test> findActiveTest(@PathParam("dni") String dni) {
        return (List<Test>) em.createQuery("SELECT t FROM Test t WHERE t.activo = 1 AND t.idTest NOT IN(SELECT e.test.idTest FROM Examen e WHERE e.examenPK.dni = :dni)")
                .setParameter("dni", dni)
                .getResultList();
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Test> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Test> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
