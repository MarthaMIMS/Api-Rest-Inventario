package ejercicio.controller;

import ejercicio.service.ProductoService;
import ejercicio.model.Producto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoController {

    @Inject
    private ProductoService productoService;

    
    @GET
    public List<Producto> listarProductos(
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("limit") @DefaultValue("10") int limit,
            @QueryParam("nombre") String nombre,
            @QueryParam("categoria") String categoria) {
        return productoService.listarProductos(offset, limit, nombre, categoria);
    }

    @POST
    public Response crearProducto(Producto producto) {
        productoService.crearProducto(producto);
        return Response.status(Response.Status.CREATED).build();
    }


    @PUT
    @Path("/{id}")
    public Response actualizarProducto(@PathParam("id") int id, Producto producto) {
        producto.setId(id);
        productoService.actualizarProducto(producto);
        return Response.ok().build();
    }

    
    @DELETE
    @Path("/{id}")
    public Response eliminarProducto(@PathParam("id") int id) {
        productoService.eliminarProducto(id);
        return Response.noContent().build();
    }

    
    @PUT
    @Path("/{id}/stock")
    public Response actualizarStock(@PathParam("id") int id, @QueryParam("stock") int nuevoStock) {
        productoService.actualizarStock(id, nuevoStock);
        return Response.ok().build();
    }
}
