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
		System.out.println("Bonjour, saisissez un numéro de compte bancaire valide : ");
		Long accountId = inputAccountNumber();

		try {
			Account account = bankJob.consultAccount(accountId);
			System.out.println("Bonjour " + account.getCustomer().getFirstName() + ", que souhaitez vous faire ?");
			
			int choice = 0;
			while (choice != 6) {
				menu();
				choice = inputMenu();

				switch (choice) {
				case 1:
					System.out.println("Versement");
					break;
				case 2:
					System.out.println("Retrait");
					break;
				case 3:
					System.out.println("Virement");
					break;
				case 4:
					System.out.println("Information de ce compte");
					System.out.println(account);
					break;
				case 5:
					System.out.println("Liste des opérations");
					break;
				case 6:
					System.out.println("Déconnexion");
					break;
				default:
					System.out.println("Mauvaise saisie, recommencez !");
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

//		// banquier ou client
//		bankJob.pay(firstAccount.getAccountId(), 500); // versement de 500 euros sur le compte de robert
//		bankJob.pay(secondAccount.getAccountId(), 1000); // versement de 1000 euros sur le compte de julie
//
//		// banquier ou client
//		bankJob.withdraw(100200300, 250); // retrait de 250 euros sur le compte de robert
//		bankJob.withdraw(200300400, 400); // retrait de 400 euros sur le compte de julie
//
//		// banquier ou client
//		bankJob.transfert(firstAccount.getAccountId(), 200300400, 200); // virement de robert chez julie de 200
//		System.out.println("----------------------------------------------------------");
//		System.out.println("solde de " + firstAccount.getCustomer().getName() + " : "
//				+ bankJob.consultAccount(firstAccount.getAccountId()).getBalance());
//		System.out.println("solde de " + secondAccount.getCustomer().getName() + " : "
//				+ bankJob.consultAccount(secondAccount.getAccountId()).getBalance());
//		System.out.println("----------------------------------------------------------");
//		bankJob.consultAccount(111111); // test du compte inexistant
//		bankJob.withdraw(100200300, 10000); // test capacité retrait dépassée
//		bankJob.transfert(100200300, 100200300, 50000); // test virement sur le même compte
//
//		// banquier
//		bankJob.addAccount(firstAccount); // test rajout du même compte au même client
//		bankJob.addAccount(new Current(300400500, new Date(), 750, 150, julie)); // ajout nouveau compte à Julie
//		System.out
//				.println("\n-----------------------Liste des comptes de ma banque-----------------------------------");
//		for (Account acc : bankJob.listAccounts())
//			System.out.println(acc);
//		System.out.println("\n-----------------------Liste des comptes de julie-----------------------------------");
//		for (Account acc : julie.getListAccounts()) {
//			System.out.println(acc);
//		}
//
//		// banquier ou client
//		System.out.println(
//				"\n-------------------liste des transactions de l'unique compte de robert------------------------");
//		for (Transaction trans : bankJob.listTransactions(100200300))
//			System.out.println(trans);
//		System.out.println(
//				"-------------------liste des transactions du compte N° 200300400 de Julie------------------------");
//		for (Transaction trans : bankJob.listTransactions(200300400))
//			System.out.println(trans);
	}

	public static Long inputAccountNumber() {
		while (scan.hasNextLong() == false) {
			scan.next();
		}
		return scan.nextLong();
	}

	public static int inputMenu() {
		while (scan.hasNextInt() == false) {
			scan.next();
		}
		return scan.nextInt();
	}

	public static void menu() {
		System.out.println("--------Tapez le numéro correspond--------");
		System.out.println(
				"1 : Versement - 2 : Retrait - 3 : Virement - 4 : Information sur ce compte - 5 : Liste des opérations - 6 : Sortir");
		System.out.println();
	}
}
