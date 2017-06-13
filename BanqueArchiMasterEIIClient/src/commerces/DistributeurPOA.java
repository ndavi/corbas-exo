package commerces;


/**
* commerces/DistributeurPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from distributeur.idl
* Tuesday, June 13, 2017 3:52:07 PM CEST
*/

public abstract class DistributeurPOA extends org.omg.PortableServer.Servant
 implements commerces.DistributeurOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("_get_modeleDistributeur", new Integer (0));
    _methods.put ("_set_modeleDistributeur", new Integer (1));
    _methods.put ("_get_solde", new Integer (2));
    _methods.put ("crediter", new Integer (3));
    _methods.put ("debiter", new Integer (4));
    _methods.put ("infosDistributeur", new Integer (5));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    Integer __method = (Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // commerces/Distributeur/_get_modeleDistributeur
       {
         String $result = null;
         $result = this.modeleDistributeur ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // commerces/Distributeur/_set_modeleDistributeur
       {
         String newModeleDistributeur = in.read_string ();
         this.modeleDistributeur (newModeleDistributeur);
         out = $rh.createReply();
         break;
       }

       case 2:  // commerces/Distributeur/_get_solde
       {
         float $result = (float)0;
         $result = this.solde ();
         out = $rh.createReply();
         out.write_float ($result);
         break;
       }

       case 3:  // commerces/Distributeur/crediter
       {
         org.omg.CORBA.Object compte = org.omg.CORBA.ObjectHelper.read (in);
         int somme = in.read_long ();
         boolean $result = false;
         $result = this.crediter (compte, somme);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 4:  // commerces/Distributeur/debiter
       {
         org.omg.CORBA.Object compte = org.omg.CORBA.ObjectHelper.read (in);
         int somme = in.read_long ();
         boolean $result = false;
         $result = this.debiter (compte, somme);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 5:  // commerces/Distributeur/infosDistributeur
       {
         String $result = null;
         $result = this.infosDistributeur ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:commerces/Distributeur:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Distributeur _this() 
  {
    return DistributeurHelper.narrow(
    super._this_object());
  }

  public Distributeur _this(org.omg.CORBA.ORB orb) 
  {
    return DistributeurHelper.narrow(
    super._this_object(orb));
  }


} // class DistributeurPOA
