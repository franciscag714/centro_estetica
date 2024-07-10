package logic;

import java.util.LinkedList;
import data.ServiceData;
import entities.Service;

public class ServiceLogic
{
	private ServiceData servData;

	public ServiceLogic() {
		servData = new ServiceData();
	}

	public LinkedList<Service> list() {
		return servData.list();
	}
	
	public Service create(Service service)
	{	
		if (service.getDescription().equals(""))
			return null;
		if (service.getUpdatedPrice() < 0)
			return null;
		
		ServiceTypeLogic typeCtrl = new ServiceTypeLogic();
		if (typeCtrl.searchById(service.getType()) == null)
			return null;
		
		return servData.add(service);
	}
	
	public Service update(Service service)
	{
		if (service.getId() == 0)
			return null;
		
		if (service.getDescription().equals(""))
			return null;
		
		if (service.getUpdatedPrice() < 0)
			return null;
		
		ServiceTypeLogic typeCtrl = new ServiceTypeLogic();
		if (typeCtrl.searchById(service.getType()) == null)
			return null;
		
		return servData.update(service);
	}
	
	public Service delete(Service service) {
		if (service.getId() == 0)
			return null;
		
		return servData.delete(service);
	}
}
