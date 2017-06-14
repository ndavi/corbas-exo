import banque.Compte;
import banque.CompteHelper;
import banque._CompteStub;
import com.sun.org.apache.xpath.internal.operations.Bool;
import commerces.Distributeur;
import commerces.DistributeurHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.Object;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by nico on 4/12/17.
 */
public class AppliClient {

    private ORB orb;
    private Scanner scanner;
    private NamingContextExt ncRef;
    private List<Compte> comptes = new ArrayList<>();
    private List<Distributeur> distributeurs = new ArrayList<>();
    private Distributeur selectedDistributeur;
    private Compte selectedCompte;


    public static void main(String args[]) {
        try {
            new AppliClient(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // fin du main

    public AppliClient(String args[]) throws InvalidName, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, CannotProceed {
        init(args);
        searchItems();

        System.out.println("Bonjour ! Bienvenue sur l'application de retrait de compte distribuée.");

        getClientInfos();

        Boolean wantContinue = true;
        while (wantContinue) {
            System.out.println("Selectionnez l'action à executer");
            System.out.print("1 : Retirer ; 2 : Déposer ; 3 : " +
                    "Solde du compte ; 4 Changer de compte/distributeur ; 5 : Quitter : ");
            String userInput = scanner.nextLine();
            Integer somme;
            switch(userInput) {
                case "1":
                    System.out.print("Sélectionnez une somme : ");
                    somme = scanner.nextInt();
                    selectedDistributeur.debiter(selectedCompte, somme);
                    System.out.println("Retrait de " + somme);
                    break;
                case "2":
                    if(!selectedDistributeur.canCrediter()) {
                        System.out.println("Il n'est pas possible de déposer de l'argent dans ce distributeur");
                        break;
                    }
                    System.out.print("Sélectionnez une somme : ");
                    somme = scanner.nextInt();
                    System.out.println("Credit de " + somme);
                    selectedDistributeur.crediter(selectedCompte, somme);
                    break;
                case "3":
                    System.out.println("Solde du compte : " + selectedCompte.infosCompte());
                    break;
                case "4":
                    selectedCompte = null;
                    selectedDistributeur = null;
                    getClientInfos();
                    break;
                case "5":
                    System.out.println("Aurevoir");
                    wantContinue = false;
                    break;
                default:
                    System.out.println("Selection incorrecte");

            }

        }
    }

    public void getClientInfos() {
        Boolean compteReady = false;
        while (!compteReady) {
            System.out.println("Veuillez choisir un compte bancaire parmis les comptes disponibles : ");
            for (Compte compte : comptes) {
                System.out.println(compte.titulaire() + " ");
            }
            System.out.print("Nom du compte : ");
            String userInput = scanner.nextLine();
            selectedCompte = comptes.stream()
                    .filter(x -> x.titulaire().contains(userInput))
                    .findFirst()
                    .orElse(null);
            if (selectedCompte != null) {
                System.out.println("Compte trouvé");
                compteReady = true;
            } else {
                System.out.println("Ce compte n'existe pas");
            }
        }
        Boolean distributeurReady = false;
        while (!distributeurReady) {
            System.out.println("Veuillez choisir un distributeur parmis les distributeurs disponibles : ");
            for (Distributeur d : distributeurs) {
                String credit = "";
                if(!d.canCrediter()) {
                    credit = "(Il n'est pas possible de déposer de l'argent dans ce distributeur)";
                }
                System.out.println(d.modeleDistributeur() + " " + credit);
            }
            System.out.print("Nom du distributeur : ");
            String userInput = scanner.nextLine();
            selectedDistributeur = distributeurs.stream()
                    .filter(x -> x.modeleDistributeur().contains(userInput))
                    .findFirst()
                    .orElse(null);
            if (selectedDistributeur != null) {
                System.out.println("Distributeur trouvé");
                distributeurReady = true;
            } else {
                System.out.println("Ce distributeur n'existe pas");
            }
        }
    }

    public void init(String args[]) throws InvalidName {
        // creer et initialiser l'ORB
        this.orb = ORB.init(args, null);
        org.omg.CORBA.Object objRef = orb
                .resolve_initial_references("NameService");
        ncRef = NamingContextExtHelper.narrow(objRef);
        this.scanner = new Scanner(System.in);
    }

    public void searchItems() throws CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, InvalidName {
        BindingListHolder items = new BindingListHolder();
        BindingIteratorHolder holder = new BindingIteratorHolder();
        ncRef.list(10, items, holder);

        for (Binding item : items.value) {
            Class<? extends Object> ctx = ncRef.resolve_str(item.binding_name[0].id).getClass();
            if (ctx.getName().contains("Compte") && !item.binding_name[0].id.contains("mon")) {
                comptes.add(getCompte(item.binding_name[0].id));
            } else if (ctx.getName().contains("Distributeur") && !item.binding_name[0].id.contains("mon")) {
                distributeurs.add(getDistributeur(item.binding_name[0].id));
            }
        }
    }

    public Distributeur getDistributeur(String objectName) throws InvalidName, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound {
        Distributeur distributeurRef = DistributeurHelper.narrow(ncRef.resolve_str(objectName));
        return distributeurRef;
    }

    public Compte getCompte(String objectName) throws InvalidName, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound {
        Compte compteRef = CompteHelper.narrow(ncRef.resolve_str(objectName));
        return compteRef;
    }
}
