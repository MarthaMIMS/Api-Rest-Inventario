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

    // Listar todos los productos
    public List<Producto> listarProductos() {
        TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p", Producto.class);
        return query.getResultList();
    }
}
