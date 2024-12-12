package logic;

import java.util.LinkedList;

import data.AttentionData;
import entities.Appointment;
import entities.Attention;
import entities.Employee;

public class AttentionLogic {
	private AttentionData attentionData;
	
	public AttentionLogic() {
		attentionData = new AttentionData();
	}
	
	public LinkedList<Attention> searchByAppointment(Appointment app){
		return attentionData.searchByAppointment(app);
	}
	
	public Attention create(Attention a, Employee e){
		if (e.isAdmin())
			return attentionData.add(a, null);
		else
			return attentionData.add(a, e);
	}
	
	public Attention delete(Attention a, Employee e){
		if (e.isAdmin())
			return attentionData.delete(a, null);
		else
			return attentionData.delete(a, e);
	}
}
