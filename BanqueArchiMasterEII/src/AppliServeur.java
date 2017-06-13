import banque.Compte;
import banque.CompteHelper;
import commerces.Distributeur;
import commerces.DistributeurHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

/**
 * Created by nico on 4/12/17.
 */
public class AppliServeur {

    private ORB orb;
    private POA rootpoa;

    public static void main(String[] args) {
       new AppliServeur(args);
    }

    public AppliServeur(String args[]) {
        try {
            // creer et initialiser l'ORB qui integre
            // le service de noms
            orb = ORB.init(args, null);
            // obtenir la reference de rootpoa &
            // activer le POAManager
            rootpoa = POAHelper.narrow(orb
                    .resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            System.out.println("CompteServer est pret et est en attente.");
            createCompte("Nicolas");
            createCompte("Pierre");
            createCompte("Jean");
            createCompte("Paul");

            createDistributeur("Villeurbanne");
            // attendre les invocations des clients
            orb.run();

        } catch (Exception e) {
            System.err.println("Erreur : " + e);
            e.printStackTrace(System.out);
        }
    }

    private static void createComptes(ORB orb, POA rootpoa) {

    }

    private void createDistributeur(String name) throws InvalidName, ServantNotActive, WrongPolicy, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, CannotProceed {
        DistributeurImpl distributeur = new DistributeurImpl(name);

        org.omg.CORBA.Object ref = rootpoa.servant_to_reference(distributeur);
        Distributeur href = DistributeurHelper.narrow(ref);
        // obtenir la reference du contexte de nommage
        org.omg.CORBA.Object objRef = orb
                .resolve_initial_references("NameService");
        // Utiliser NamingContextExt qui est Interoperable
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
        // enregistrer le servant dans le service de nommage
        NameComponent path[] = ncRef.to_name(name);
        ncRef.rebind(path, href);
        System.out.println("Le distributeur est prêt.");
    }

    private void createCompte(String name) throws ServantNotActive, WrongPolicy, InvalidName, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, CannotProceed {
        // creer le servant
        CompteImpl revRef = new CompteImpl(name, 200);
        // obtenir la reference CORBA du servant
        org.omg.CORBA.Object ref = rootpoa.servant_to_reference(revRef);
        Compte href = CompteHelper.narrow(ref);
        // obtenir la reference du contexte de nommage
        org.omg.CORBA.Object objRef = orb
                .resolve_initial_references("NameService");
        // Utiliser NamingContextExt qui est Interoperable
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
        // enregistrer le servant dans le service de nommage
        NameComponent path[] = ncRef.to_name(name);
        ncRef.rebind(path, href);
    }
}
