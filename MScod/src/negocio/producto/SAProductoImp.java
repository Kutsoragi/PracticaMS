package negocio.producto;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import negocio.entityManagerUtil.EntityManagerFact;
import negocio.marca.Marca;
import negocio.proveedor.Proveedor;

import java.util.ArrayList;
import java.util.List;

public class SAProductoImp implements SAProducto {

	
	public int registrarProducto(TProducto tProducto) {

		int idProducto = -1;

		EntityManager em = EntityManagerFact.getInstance().createEntityManager();
		em.getTransaction().begin();
		
		//Validamos el proveedor y la marca
		Proveedor proveedor = em.find(Proveedor.class, tProducto.getIdProveedor(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		Marca marca = em.find(Marca.class, tProducto.getIdMarca(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		
		if(proveedor != null && proveedor.isActivo() && marca != null && marca.isActivo()){
			Producto producto;
			
			if (tProducto instanceof TProductoBebida) {
				producto = new ProductoBebida();
				((ProductoBebida) producto).setVolumen(((TProductoBebida) tProducto).getVolumen());
			}
	
			else {
				producto = new ProductoComida();
				((ProductoComida) producto).setPeso(((TProductoComida) tProducto).getPeso());
			}
			producto.setCalorias(tProducto.getCalorias());
			producto.setId(tProducto.getId());
			producto.setPrecio(tProducto.getPrecio());
			producto.setStock(tProducto.getStock());
			producto.setActivo(true);
			producto.setMarca(marca);
			producto.setProveedor(proveedor);
	
			// Damos de alta y refrescamos
			em.persist(producto);
			em.refresh(marca);
			em.refresh(proveedor);
			em.getTransaction().commit();
			idProducto = producto.getId();
		}
		else{
			em.getTransaction().rollback();
			throw new IllegalArgumentException("No existe el proveedor o la marca");
		}
		
		em.close();
		return idProducto;

	}

	public List<TProducto> mostrarProductoConMasDeXstock(Integer stock) {
		List<TProducto> res = new ArrayList<TProducto>();
		
		EntityManager em = EntityManagerFact.getInstance().createEntityManager();
		em.getTransaction().begin();
		
		TypedQuery<Producto> query = em.createNamedQuery("negocio.producto.Producto.buscarPorMasDeCiertoStock", Producto.class);
		query.setParameter("stock", stock);
		List<Producto> listaLeida = query.getResultList();
		if(listaLeida.size() > 0){
			TProducto producto;
			for (Producto p : listaLeida){
				em.lock(p, LockModeType.OPTIMISTIC);
				if (p instanceof ProductoBebida) {
					producto = new TProductoBebida();
					producto.setVolumen(((ProductoBebida) p).getVolumen());
				}
	
				else {
					producto = new TProductoComida();
					producto.setPeso(((ProductoComida) p).getPeso());
				}
				producto.setCalorias(p.getCalorias());
				producto.setId(p.getId());
				producto.setPrecio(p.getPrecio());
				producto.setStock(p.getStock());
				producto.setIdMarca(p.getMarca().getId());
				producto.setIdProveedor(p.getProveedor().getId());
				res.add(producto);
			}
			em.getTransaction().commit();
		}
		else
			em.getTransaction().rollback();
		em.close();
		
		return res;
	}

	public int editarProducto(TProducto tProducto) {

		int idProducto = -1;

		// Iniciamos la transaccion
		EntityManager em = EntityManagerFact.getInstance().createEntityManager();
		em.getTransaction().begin();

		// Buscamos el producto
		Producto productoLectura = em.find(Producto.class, tProducto.getId());
		Marca marcaLectura = em.find(Marca.class, tProducto.getIdMarca());
		Proveedor proveedorLectura = em.find(Proveedor.class, tProducto.getIdProveedor());

		// si existe en la base de datos y esta activo
		if (productoLectura != null && productoLectura.isActivo() && marcaLectura != null && marcaLectura.isActivo() && proveedorLectura != null && proveedorLectura.isActivo()) {
				productoLectura.setCalorias(tProducto.getCalorias());
				productoLectura.setPrecio(tProducto.getPrecio());
				productoLectura.setStock(tProducto.getStock());

				if (tProducto instanceof TProductoBebida) {
					((ProductoBebida) productoLectura).setVolumen(((TProductoBebida) tProducto).getVolumen());
				}

				else {

					((ProductoComida) productoLectura).setPeso(((TProductoComida) tProducto).getPeso());
				}

				em.getTransaction().commit();
				idProducto = productoLectura.getId();
		} 
		else{
			em.getTransaction().rollback();
			throw new IllegalArgumentException("No existen productos, marcas y/o proveedores");
		}

		em.close();
		return idProducto;
	}

	public TProducto buscarProductoPorId(int id) {
		TProducto res = null;

		// Iniciamos la transaccion
		EntityManager em = EntityManagerFact.getInstance().createEntityManager();
		em.getTransaction().begin();

		// Creamos y ejecutamos la query
		Producto p = em.find(Producto.class, id);

		if (p != null && p.isActivo()) {
			if(p instanceof ProductoComida){
				res = new TProductoComida();
				res.setPeso(((ProductoComida) p).getPeso());
			}
			else{
				res = new TProductoBebida();
				res.setVolumen(((ProductoBebida) p).getVolumen());
			}
			
			res.setId(p.getId());
			res.setCalorias(p.getCalorias());
			res.setPrecio(p.getPrecio());
			res.setIdMarca(p.getMarca().getId());
			res.setIdProveedor(p.getProveedor().getId());
			res.setStock(p.getStock());
			em.getTransaction().commit();
		} else
			em.getTransaction().rollback();

		em.close();
		return res;
	}

	public List<TProducto> mostrarProductos() {
		List<TProducto> res = new ArrayList<TProducto>();
		
		EntityManager em = EntityManagerFact.getInstance().createEntityManager();
		em.getTransaction().begin();
		
		TypedQuery<Producto> query = em.createNamedQuery("negocio.producto.Producto.mostrarProductos", Producto.class);
		List<Producto> listaLeida = query.getResultList();
		if(listaLeida.size() > 0){
			TProducto producto;
			for (Producto p : listaLeida){
				if (p instanceof ProductoBebida) {
					producto = new TProductoBebida();
					producto.setVolumen(((ProductoBebida) p).getVolumen());
				}
	
				else {
					producto = new TProductoComida();
					producto.setPeso(((ProductoComida) p).getPeso());
				}
				producto.setCalorias(p.getCalorias());
				producto.setId(p.getId());
				producto.setPrecio(p.getPrecio());
				producto.setStock(p.getStock());
				producto.setIdMarca(p.getMarca().getId());
				producto.setIdProveedor(p.getProveedor().getId());
				res.add(producto);
			}
			em.getTransaction().commit();
		}
		else
			em.getTransaction().rollback();
		em.close();
		
		return res;
	}

	public int borrarProducto(int id) {

		int idProducto = -1;
		// Iniciamos la transaccion
		EntityManager em = EntityManagerFact.getInstance().createEntityManager();
		em.getTransaction().begin();

		// Buscamos el proveedor por id
		Producto productoLectura = em.find(Producto.class, id);

		// Si existe y esta activo
		if (productoLectura != null && productoLectura.isActivo()) {
			productoLectura.setActivo(false);
			em.getTransaction().commit();
			idProducto = productoLectura.getId();
		} else
			em.getTransaction().rollback();

		em.close();
		return idProducto;
	}

	/*public TProducto mostrarProductoMasCalorico() {

		TProducto producto = null;
		// Iniciamos la transaccion
		EntityManager em = EntityManagerFact.getInstance().createEntityManager();
		em.getTransaction().begin();
		
		TypedQuery<Producto> productoLeido = em.createQuery("SELECT p FROM Producto p WHERE p.activo = 1 ORDER BY p.calorias DESC", Producto.class);
		if (productoLeido.getResultList().size() > 0){
			Producto p = productoLeido.getResultList().get(0);
			em.lock(p, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			if(p instanceof ProductoComida){
				producto = new TProductoComida();
				producto.setPeso(((ProductoComida) p).getPeso());
			}
			else{
				producto = new TProductoBebida();
				producto.setVolumen(((ProductoBebida) p).getVolumen());
			}
			
			producto.setId(p.getId());
			producto.setCalorias(p.getCalorias());
			producto.setPrecio(p.getPrecio());
			producto.setStock(p.getStock());
			producto.setIdMarca(p.getMarca().getId());
			producto.setIdProveedor(p.getProveedor().getId());
			em.getTransaction().commit();
		}
		else
			em.getTransaction().rollback();
		em.close();
		
		return producto;
	}*/
	
}