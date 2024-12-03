package logic;

import java.util.LinkedList;

import data.ServiceTypeData;
import entities.ServiceType;

public class ServiceTypeLogic
{
	private ServiceTypeData stData;

	public ServiceTypeLogic() {
		stData = new ServiceTypeData();
	}

	public LinkedList<ServiceType> list(Boolean populateServices) {
		return stData.list(populateServices);
	}
	
	public ServiceType searchById(ServiceType servType) {
		return stData.searchById(servType);
	}
	
	public ServiceType create(ServiceType servType) {
		if (servType.getDescription().equals(""))
			return null;
		
		return stData.add(servType);
	}
	
	public ServiceType update(ServiceType servType) {
		if (servType.getDescription().equals("") || servType.getId() == 0)
			return null;
		
		return stData.update(servType);
	}
	
	public ServiceType delete(ServiceType servType) {
		if (servType.getId() == 0)
			return null;
		
		return stData.delete(servType);
	}
}
