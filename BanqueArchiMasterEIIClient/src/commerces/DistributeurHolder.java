package commerces;

/**
* commerces/DistributeurHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from distributeur.idl
* Tuesday, June 13, 2017 3:52:07 PM CEST
*/

public final class DistributeurHolder implements org.omg.CORBA.portable.Streamable
{
  public commerces.Distributeur value = null;

  public DistributeurHolder ()
  {
  }

  public DistributeurHolder (commerces.Distributeur initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = commerces.DistributeurHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    commerces.DistributeurHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return commerces.DistributeurHelper.type ();
  }

}
