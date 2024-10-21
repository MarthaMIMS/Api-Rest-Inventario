package ejercicio.service;

import ejercicio.model.Producto;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
public class ProductoService {

    @PersistenceContext(unitName = "ProductosPU")
    private EntityManager em;

    // Crear producto
    public void crearProducto(Producto producto) {
        em.persist(producto);
    }

    // Actualizar producto
    public void actualizarProducto(Producto producto) {
        em.merge(producto);
    }

    // Eliminar producto por ID
    public void eliminarProducto(int id) {
        Producto producto = em.find(Producto.class, id);
        if (producto != null) {
            em.remove(producto);
        }
    }

    // Buscar producto por ID
    public Producto buscarProducto(int id) {
        return em.find(Producto.class, id);
    }

    // Listar todos los productos con paginación y filtro
    public List<Producto> listarProductos(int offset, int limit, String nombre, String categoria) {
        StringBuilder queryStr = new StringBuilder("SELECT p FROM Producto p WHERE 1=1");

        if (nombre != null && !nombre.isEmpty()) {
            queryStr.append(" AND LOWER(p.nombre) LIKE :nombre");
        }

        if (categoria != null && !categoria.isEmpty()) {
            queryStr.append(" AND LOWER(p.categoria) = :categoria");
        }

        TypedQuery<Producto> query = em.createQuery(queryStr.toString(), Producto.class);

        if (nombre != null && !nombre.isEmpty()) {
            query.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
        }

        if (categoria != null && !categoria.isEmpty()) {
            query.setParameter("categoria", categoria.toLowerCase());
        }

        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    // Actualizar el stock de un producto por ID
    public void actualizarStock(int id, int nuevoStock) {
        Producto producto = em.find(Producto.class, id);
        if (producto != null) {
            producto.setStock(nuevoStock);
            em.merge(producto);
        }
    }
}
