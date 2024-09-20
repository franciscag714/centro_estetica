package logic;

import java.util.LinkedList;

import data.AttentionData;
import entities.Appointment;
import entities.Attention;

public class AttentionLogic {
	private AttentionData attentionData;
	
	public AttentionLogic() {
		attentionData = new AttentionData();
	}
	
	public LinkedList<Attention> list(){
		return attentionData.list();
	}
	
	public LinkedList<Attention> searchByAppointment(Appointment app){
		return attentionData.searchByAppointment(app);
	}
	
	/*
	public Attention create(Attention a){
	 	return attentionData.add(a);
	}
	
	public Attention update(Attention a){
	 	return attentionData.update(a);
	}
	
	public Attention delete(Attention a){
	 	return attentionData.delete(a);
	}
	  
	*/
	
}
