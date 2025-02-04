package presentacion.controller;

public abstract class ApplicationController {
	
	private static ApplicationController instance;
	
	public synchronized static ApplicationController getInstance() {
		if (instance == null) {
			instance = new ApplicationControllerImpl();
		}
		return instance;
	}
	
	public abstract void handleRequest(Context requestContext);
}