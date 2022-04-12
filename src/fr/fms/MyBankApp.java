package fr.fms;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

import fr.fms.business.IBankBusinessImpl;
import fr.fms.entities.Current;
import fr.fms.entities.Customer;
import fr.fms.entities.Saving;
import fr.fms.entities.Transaction;

public class MyBankApp {

	private static Scanner scan = new Scanner(System.in);
	private static IBankBusinessImpl bankJob;
	private static Customer robert;
	private static Customer julie;
	private static Current firstAccount;
	private static Saving secondAccount;

	public static void main(String[] args) {

		initBank();

		System.out.println("Bonjour et bienvenue dans MyBankApp");
		while (true) {
			System.out.println("Saisissez un numéro de compte bancaire valide : ");
			Long accountId = checkAccountId();
			System.out.print("Bonjour, " + bankJob.consultAccount(accountId).getCustomer().getFirstName()
					+ " que souhaitez-vous faire ?");
			int choice = 0;

			while (choice != 6) {
				try {
					System.out.println();
					System.out.println("--------Tapez le numéro correspond--------");
					System.out.println(
							"1 : Versement - 2 : Retrait - 3 : Virement - 4 : Information sur ce compte - 5 : Liste des opérations - 6 : Sortir");
					System.out.println();

					while (scan.hasNextInt() == false)
						scan.next();
					choice = scan.nextInt();
					double amount;
					switch (choice) {
					case 1:
						System.out.println("Saissisez le montant à verser sur ce compte");
						amount = isAmountPositive();
						bankJob.pay(accountId, amount);
						System.out.println("La somme de " + amount + " a bien été ajouté sur votre compte.");
						break;
					case 2:
						System.out.println("Saissisez le montant à retirer sur ce compte");
						amount = isAmountPositive();
						bankJob.withdraw(accountId, amount);
						System.out.println("La somme de " + amount + " a bien été retiré de votre compte.");
						break;
					case 3:
						System.out.println("Saissisez le numéro de compte destinataire");
						while (scan.hasNextLong() == false)
							scan.next();
						long accDes = scan.nextLong();

						System.out.println("Saissisez le montant à virer sur ce compte");
						amount = isAmountPositive();
						bankJob.transfert(accountId, accDes, amount);
						System.out.println("La somme de " + amount + " a bien été transféré.");
						break;
					case 4:
						System.out.println("Information de ce compte");
						System.out.println(bankJob.consultAccount(accountId));
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
						break;
					default:
						System.out.println("Mauvaise saisie, recommencez !");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	private static void initBank() {
		robert = new Customer(1, "dupont", "robert", "robert.dupont@xmail.com");
		julie = new Customer(2, "jolie", "julie", "julie.jolie@xmail.com");
		firstAccount = new Current(100200300, new Date(), 1500, 200, robert);
		secondAccount = new Saving(200300400, new Date(), 2000, 5.5, julie);
		bankJob = new IBankBusinessImpl();
		bankJob.addAccount(firstAccount);
		bankJob.addAccount(secondAccount);
	}

	private static Long checkAccountId() {
		Long accoundId = null;

		while (scan.hasNext()) {
			if (scan.hasNext(Pattern.compile("[0-9]+"))) {
				accoundId = scan.nextLong();
				try {
					if (bankJob.consultAccount(accoundId) != null)
						break;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} else {
				System.out.println("U numéro de compte bancaire est constitué que de chiffres");
				scan.next();
			}

		}
		return accoundId;
	}

	/**
	 * Recup le montant saisi, verif si positif et le retourne
	 * 
	 * @param amount
	 * @return
	 */
	public static double isAmountPositive() {
		while (scan.hasNextDouble() == false)
			scan.next();

		double amount = scan.nextDouble();

		if (amount < 0) {
			throw new IllegalArgumentException("Le montant ne peut être négatif.");
		}
		return amount;
	}
}
