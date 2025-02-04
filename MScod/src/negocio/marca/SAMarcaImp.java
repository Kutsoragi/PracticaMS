package negocio.marca;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import negocio.entityManagerUtil.EntityManagerFact;
import negocio.producto.Producto;
import negocio.producto.ProductoBebida;
import negocio.producto.ProductoComida;
import negocio.producto.TProducto;
import negocio.producto.TProductoBebida;
import negocio.producto.TProductoComida;



public class SAMarcaImp implements SAMarca {
	
	
	
	public int registrarMarca(TMarca tMarca) {
		
		int idMarca = -1;// si devuelve -1, operacion no realizada
		
		//Iniciamos la transaccion
		EntityManager em = EntityManagerFact.getInstance().createEntityManager();
		em.getTransaction().begin();
		
		// Buscamos la marca por nombre
		TypedQuery<Marca> marcaLectura = em.createNamedQuery("negocio.marca.Marca.buscarPorNombre",Marca.class);
		marcaLectura.setParameter("nombre", tMarca.getNombre());

		//no hay ninguna marca con el mismo nombre
		if(marcaLectura.getResultList().size() == 0){ 
			// asignamos los atributos
			Marca marca = new Marca();
			marca.setNombre(tMarca.getNombre());
			marca.setActivo(true);
			// Damos de alta
			em.persist(marca);
			em.getTransaction().commit();
			idMarca = marca.getId();
			
		}else{ // Reactivamos
			 Marca marca = marcaLectura.getSingleResult();
			 
			 if(!marca.isActivo()){
				// Actualizamos los valores
				marca.setNombre(tMarca.getNombre());
				// reactivamos
				marca.setActivo(true);
				em.getTransaction().commit();
				idMarca = marca.getId();
			 }
			 else
				em.getTransaction().rollback();
		}
		
		em.close();

		return idMarca;
		
	}


	public int editarMarca(TMarca tMarca) {
		
		int id = -1;
		
		//Iniciamos la transaccion
		EntityManager em = EntityManagerFact.getInstance().createEntityManager();
		em.getTransaction().begin();
		
		// Buscamos la marca y bloqueamos la tabla
		Marca marcaLectura = em.find(Marca.class, tMarca.getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		
		// si existe en la base de datos y esta activo
		if(marcaLectura != null && marcaLectura.isActivo()){ 
			
			// Buscamos si hay un marca con el mismo nombre
			TypedQuery<Marca> marcaLecturaPorNombre = em.createNamedQuery("negocio.marca.Marca.buscarPorNombre",Marca.class);
			marcaLecturaPorNombre.setParameter("nombre", tMarca.getNombre());
			
			//Si no hay un marca con el mismo nombre
			if (marcaLecturaPorNombre.getResultList().size() == 0) {
				marcaLectura.setNombre(tMarca.getNombre());
				id = marcaLectura.getId();
				em.getTransaction().commit();
			}
			else{
				em.getTransaction().rollback();
				throw new IllegalArgumentException("Ya existe una marca con el mismo nombre");
			}
		} else
			em.getTransaction().rollback();
		
		em.close();
		
		return id;
	}
	

	public TMarca buscarMarcaPorId(int id) {
		TMarca res = null;
		
		//Iniciamos la transaccion
		EntityManager em = EntityManagerFact.getInstance().createEntityManager();
		em.getTransaction().begin();
		
		//Creamos y ejecutamos la query
		Marca m = em.find(Marca.class, id);
		
		if (m != null && m.isActivo()) {
			res = new TMarca();
			res.setId(m.getId());
			res.setNombre(m.getNombre());
			res.setActivo(m.isActivo());
			em.getTransaction().commit();
		}
		else
			em.getTransaction().rollback();
					
		
		em.close();
		
		return res;
	}

	
	public List<TProducto> buscarProductosPorMarca(TMarca marca) {
		List<TProducto> listaTransfer = null;
		
		//Iniciamos la transaccion
		EntityManager em = EntityManagerFact.getInstance().createEntityManager();
		em.getTransaction().begin();
		
		//Creamos y ejecutamos la query
		TypedQuery<Marca> query = em.createQuery("SELECT m FROM Marca m WHERE m.nombre = :nombre", Marca.class);
		query.setParameter("nombre", marca.getNombre());
		
		if(query.getResultList().size() != 0){
			listaTransfer = new ArrayList<TProducto>();
			Marca marcaLeida = query.getSingleResult(); 
			em.lock(marcaLeida, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			for(Producto p : marcaLeida.getListaProductos()){
				if (p.isActivo()) {
					TProducto producto = null;
					if(p instanceof ProductoComida){
						producto = new TProductoComida();
						producto.setPeso(((ProductoComida) p).getPeso());
					}
					else{
						producto = new TProductoBebida();
						producto.setVolumen(((ProductoBebida) p).getVolumen());
					}
					
					producto.setId(p.getId());
					producto.setPrecio(p.getPrecio());
					producto.setCalorias(p.getCalorias());
					producto.setStock(p.getStock());
					producto.setIdMarca(p.getMarca().getId());
					producto.setIdProveedor(p.getProveedor().getId());
					listaTransfer.add(producto);
				}
			}
			em.getTransaction().commit();
		}
		else
			em.getTransaction().rollback();
					
		em.close();
		return listaTransfer;
	}

	public int borrarMarca(int id) {
		
		int resId = -1;
		
		//Iniciamos la transaccion
		EntityManager em = EntityManagerFact.getInstance().createEntityManager();
		em.getTransaction().begin();
		
		// Buscamos la marca por id
		Marca marcaLectura = em.find(Marca.class, id);
		
		//Si existe y esta activo
		if(marcaLectura != null && marcaLectura.isActivo()){ 
			
			boolean tieneActivos = false;
			
			// comprueba si hay algun producto activo
			List<Producto> list = marcaLectura.getListaProductos();
			if (list.size() > 0){
				for(Producto p: list){ 
					em.lock(p, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
					if(p.isActivo()){
						tieneActivos = true;
					}
				}
			}
			
			if(!tieneActivos){
				marcaLectura.setActivo(false);
				em.getTransaction().commit();
				resId = id;
			}
			else{
				em.getTransaction().rollback();
				throw new IllegalArgumentException("La marca tiene productos activos asociados");
			}
		}
		else
			em.getTransaction().rollback();
		
		em.close();
		
		return resId;
	}
	
	public List<TMarca> mostrarMarcas() {
	
		List<Marca> listaEntidad = null;
		List<TMarca> listaTransfer = new ArrayList<TMarca>();
		
		//Iniciamos la transaccion
		EntityManager em = EntityManagerFact.getInstance().createEntityManager();
		em.getTransaction().begin();
		
		TypedQuery<Marca> query = em.createNamedQuery("negocio.marca.Marca.listarMarcas", Marca.class);
		listaEntidad = query.getResultList();
		
		if(listaEntidad != null){ 
			for(Marca m: listaEntidad){ 
				if(m.isActivo()){
					TMarca marca = new TMarca();
					marca.setId(m.getId());
					marca.setNombre(m.getNombre());
					marca.setActivo(m.isActivo());
					listaTransfer.add(marca);
				}
			}
			em.getTransaction().commit();
		}
		else
			em.getTransaction().rollback();
		
		em.close();
		
		return listaTransfer;
	}
	
public int obtenerCaloriasMediasDeMarca(int idMarca){
		
		int totalCalorias = 0;
		
		EntityManager em = EntityManagerFact.getInstance().createEntityManager();
		em.getTransaction().begin();
		
		Marca marcaLectura = em.find(Marca.class, idMarca);
		
		if (marcaLectura != null && marcaLectura.isActivo()) {
			for (Producto p : marcaLectura.getListaProductos()){
				totalCalorias += p.calculateCalorias();
			}
			if (marcaLectura.getListaProductos().size() != 0) totalCalorias = totalCalorias/marcaLectura.getListaProductos().size(); 
			em.getTransaction().commit();
		} else{
			em.getTransaction().rollback();
			totalCalorias = -1;
		}
		
		return totalCalorias;
	}
}