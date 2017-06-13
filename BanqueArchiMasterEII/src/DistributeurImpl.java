import banque.Compte;
import commerces.DistributeurPOA;
import org.omg.CORBA.Object;

/**
 * Created by nico on 6/13/17.
 */
public class DistributeurImpl extends DistributeurPOA {

    private String modeleDistributeur;

    public DistributeurImpl(String modeleDistributeur) {
        this.modeleDistributeur = modeleDistributeur;
    }

    @Override
    public String modeleDistributeur() {
        return modeleDistributeur;
    }

    @Override
    public void modeleDistributeur(String newModeleDistributeur) {
        this.modeleDistributeur = newModeleDistributeur;
    }

    @Override
    public float solde() {
        return 0;
    }

    @Override
    public boolean crediter(Object compte, int somme) {
        if(compte instanceof Compte) {
            Compte elCompte = (Compte)compte;
            elCompte.crediter(somme);
            return true;
        } else {
            System.out.println("Mauvais objet passé a la fonction");
            return false;
        }
    }

    @Override
    public boolean debiter(Object compte, int somme) {
        if(compte instanceof Compte) {
            Compte elCompte = (Compte)compte;
            elCompte.debiter(somme);
            return true;
        } else {
            System.out.println("Mauvais objet passé a la fonction");
            return false;
        }
    }

    @Override
    public String infosDistributeur() {
        return null;
    }
}
