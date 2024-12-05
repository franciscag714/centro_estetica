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
		if (c.getFirstname().isBlank() || c.getLastname().isBlank() || c.getEmail().isBlank() || c.getUser().isBlank()
				|| c.getPassword().trim().length() < 4)
			return null;

		return clientData.add(c);
	}

	public Client update(Client c) {
		if (c.getFirstname().isBlank() || c.getLastname().isBlank() || c.getEmail().isBlank() || c.getUser().isBlank())
			return null;

		String password = c.getPassword().trim();

		if (!password.isEmpty() && password.length() < 4)
			return null;

		return clientData.update(c);
	}

	public Client delete(Client c) {
		return clientData.delete(c);
	}

	public Client searchById(Client c) {
		return clientData.searchById(c);
	}

	public Client searchByUserAndPassword(Client c) {
		return clientData.searchByUserAndPassword(c);
	}
}
