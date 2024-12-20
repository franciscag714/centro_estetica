package logic;

import java.util.LinkedList;

import data.ServiceData;
import entities.Service;

public class ServiceLogic {
	private ServiceData servData;

	public ServiceLogic() {
		servData = new ServiceData();
	}

	public LinkedList<Service> list() {
		return servData.list();
	}

	public Service create(Service service) {
		service.trimFields();
		
		if (service.getDescription().isBlank() || service.getUpdatedPrice() < 0)
			return null;

		return servData.add(service);
	}

	public Service update(Service service) {
		service.trimFields();
		
		if (service.getDescription().isBlank() || service.getUpdatedPrice() < 0)
			return null;

		return servData.update(service);
	}

	public Service delete(Service service) {
		return servData.delete(service);
	}
}
