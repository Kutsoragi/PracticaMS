package negocio.entityManagerUtil;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFact {

	private static EntityManagerFactory instance;
	
	public synchronized static EntityManagerFactory getInstance() {
		if (instance == null) {
			instance = Persistence.createEntityManagerFactory("msfilms");
		}
		
		return instance;
	}
}
