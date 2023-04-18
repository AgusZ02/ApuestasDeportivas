package exceptions;

public class EventExpired extends Exception {
    private static final long serialVersionUID = 1L;
 
 public EventExpired()
  {
    super();
  }
  /**This exception is triggered if the event has already finished
  *@param s String of the exception
  */
  public EventExpired(String s)
  {
    super(s);
  }
}
