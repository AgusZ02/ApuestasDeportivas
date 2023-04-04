package exceptions;

public class NotEnoughMoney extends Exception{
    private static final long serialVersionUID = 1L;
 
    public NotEnoughMoney()
     {
       super();
     }
     /**This exception is triggered if the question already exists 
     *@param s String of the exception
     */
     public NotEnoughMoney(String s)
     {
       super(s);
     }

}