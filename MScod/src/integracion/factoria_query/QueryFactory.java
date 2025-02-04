package integracion.factoria_query;

import integracion.query.Query;

public abstract class QueryFactory {

	private static QueryFactory instance;

	
	public synchronized static QueryFactory getInstance() {
		if(instance == null) {
			instance = new QueryFactoryImpl();
		}
		return instance;
	}

	public abstract Query mostrarPeliculasPorFecha();
	public abstract Query mostrarTopPeliculas();
}
