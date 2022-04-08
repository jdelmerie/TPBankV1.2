package fr.fms.business;

import java.util.ArrayList;
import fr.fms.entities.Account;
import fr.fms.entities.Transaction;


public interface IBankBusiness {
	public void addAccount(Account account);								//ajoute un compte associé à un client à notre banque
	public Account consultAccount(Long accountId) throws Exception;							//renvoi le compte correspondant à l'id 
	public void pay(long accountId, double amount);							//faire un versement sur un compte 
	public boolean withdraw(long accountId, double amount) throws Exception;					//faire un retrait sur un compte
	public void transfert(long accIdSrc, long accIdDest, double amount) throws Exception;	//faire un virement d'un compte source vers destination
	public ArrayList<Transaction> listTransactions(long accountId) throws Exception; 		//renvoi la liste des opérations sur un compte donné
 
}
