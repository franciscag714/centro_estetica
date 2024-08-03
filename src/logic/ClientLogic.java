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
}
