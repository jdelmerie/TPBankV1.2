package fr.fms;

import java.nio.channels.AcceptPendingException;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import fr.fms.business.IBankBusinessImpl;
import fr.fms.entities.Account;
import fr.fms.entities.Current;
import fr.fms.entities.Customer;
import fr.fms.entities.Saving;
import fr.fms.entities.Transaction;

public class MyBankApp {

	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		// représente l'activité de notre banque
		IBankBusinessImpl bankJob = new IBankBusinessImpl();

		System.out.println("création de 2 comptes bancaires");
		Customer robert = new Customer(1, "dupont", "robert", "robert.dupont@xmail.com");
		Customer julie = new Customer(2, "jolie", "julie", "julie.jolie@xmail.com");
		Current firstAccount = new Current(100200300, new Date(), 1500, 200, robert);
		Saving secondAccount = new Saving(200300400, new Date(), 2000, 5.5, julie);

		System.out.println("Affichage des données des 2 comptes");
		System.out.println(firstAccount);
		System.out.println(secondAccount);

		System.out.println("notre banquier ajoute les 2 comptes");
		bankJob.addAccount(firstAccount);
		bankJob.addAccount(secondAccount);

		System.out.println("-----------");

		boolean app = false;

		do {
			System.out.println("Bonjour, saisissez un numéro de compte bancaire valide : ");
			while (scan.hasNextLong() == false)
				scan.next();
			Long accountId = scan.nextLong();
			Account account;
			try {
				account = bankJob.consultAccount(accountId);
				app = true;
				int choice = 0;

				while (choice != 6) {
					menu();

					while (scan.hasNextInt() == false)
						scan.next();
					
					choice = scan.nextInt();
					switch (choice) {
					case 1:
						System.out.println("Saissisez le montant à verser sur ce compte");
						while (scan.hasNextDouble() == false)
							scan.next();
						bankJob.pay(accountId, scan.nextDouble());
						// CHECK IF AMOUNT NEG Regex ?
						break;
					case 2:
						System.out.println("Saissisez le montant à retirer sur ce compte");
						while (scan.hasNextDouble() == false)
							scan.next();
						try {
							bankJob.withdraw(accountId, scan.nextDouble());
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						break;
					case 3:
						System.out.println("Saissisez le numéro de compte destinataire");
						while (scan.hasNextLong() == false)
							scan.next();
						long accDes = scan.nextLong();
						System.out.println("Saissisez le montant à virer sur ce compte");
						while (scan.hasNextDouble() == false)
							scan.next();
						try {
							bankJob.transfert(accountId, accDes, scan.nextDouble());
						} catch (Exception e) {
							e.getMessage();
						}
						break;
					case 4:
						System.out.println("Information de ce compte");
						System.out.println(account);
						break;
					case 5:
						if (bankJob.listTransactions(accountId).size() > 0) {
							System.out.println("Liste des opérations");
							for (Transaction trans : bankJob.listTransactions(accountId))
								System.out.println(trans);
						} else {
							System.out.println("Aucune opération sur ce compte pour l'instant.");
						}
						break;
					case 6:
						System.out.println("Déconnexion");
						app = false;
						break;
					default:
						System.out.println("Mauvaise saisie, recommencez !");
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} while (!app);
	}

	public static void menu() {
		System.out.println();
		System.out.println("--------Tapez le numéro correspond--------");
		System.out.println(
				"1 : Versement - 2 : Retrait - 3 : Virement - 4 : Information sur ce compte - 5 : Liste des opérations - 6 : Sortir");
		System.out.println();
	}
}
