package logic;

import java.util.LinkedList;
import data.ClientData;
import entities.Client;

public class ClientLogic {

	private ClientData clientData;

	public ClientLogic() {
		clientData = new ClientData();
	}

	public LinkedList<Client> list() {
		return clientData.list();
	}
	
	public Client create(Client c) {
		return clientData.add(c);
	}
	
	public Client update(Client c) {
		return clientData.update(c);
	}
	
	public Client delete(Client c) {
		return clientData.delete(c);
	}
}
