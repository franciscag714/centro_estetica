package logic;

import java.util.LinkedList;

import data.ClientData;
import entities.Client;

public class LoginCli {
	private ClientData cd;
	
	public LoginCli() {
		cd = new ClientData();
	}
	
	public Client validate(Client cli) {
		return cd.searchByUser(cli);
	}
	
	public LinkedList<Client> list(){
		return cd.list();
	}
}
