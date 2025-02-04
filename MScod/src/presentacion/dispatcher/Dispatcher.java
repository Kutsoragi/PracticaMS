package presentacion.dispatcher;

import presentacion.controller.Context;

public abstract class Dispatcher {
	
	private static Dispatcher instance;
	
	public synchronized static Dispatcher getInstance() {
		if (instance == null) {
			instance = new DispatcherImpl();
		}
		return instance;
	}
	
	public abstract void dispatch(Context responseContext);
}