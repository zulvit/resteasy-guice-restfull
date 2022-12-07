package api;

import dao.InvoiceDaoImpl;
import entity.InvoiceJson;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("NotNullNullableValidation")
@Path("/")
public final class FormParamRest {
    private final InvoiceDaoImpl invoiceDao = new InvoiceDaoImpl();

    @Path("/get")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<@NotNull InvoiceJson> all = invoiceDao.getAll();
        if (!all.isEmpty()) {
            return Response.ok(
                    all
            ).header(HttpHeaders.CACHE_CONTROL, "no-cache").build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Path("/get/{id}")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        Optional<InvoiceJson> byId = invoiceDao.findById(id);
        if (byId.isPresent()) {
            InvoiceJson invoiceJson = byId.get();
            return Response.ok(
                    new InvoiceJson(id, invoiceJson.getName(), invoiceJson.getFirm(), invoiceJson.getAmount())
            ).header(HttpHeaders.CACHE_CONTROL, "no-cache").build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Path("/post")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String post(InvoiceJson invoiceJson) {
        invoiceDao.save(invoiceJson);
        return invoiceJson.getFirm() + " " + invoiceJson.getAmount() + " " + invoiceJson.getName();
    }

    @Path("/post/{name}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(InvoiceJson invoiceJson, @PathParam("name") String name) {
        if (invoiceDao.findByName(name).isPresent()) {
            invoiceDao.update(invoiceJson);
            return Response.ok(
                    new InvoiceJson(invoiceJson.getId(), invoiceJson.getName(), invoiceJson.getFirm(), invoiceJson.getAmount())
            ).header(HttpHeaders.CACHE_CONTROL, "no-cache").build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
