package cl.hyh.entity;

import java.util.ArrayList;

import cl.hyh.interfaces.ITrxBase;


/**
 * @author abertens
 *
 */
public class Trx {
   private String name = "";
   private String title = "";
   private String className = ""; 
   private String preClassName = ""; 
   private String postClassName = ""; 
   private ArrayList<Button> buttonList = new ArrayList<Button>();
   private ArrayList<ReturnAction> retActions = new ArrayList<ReturnAction>();
   private String cart = "";
   private boolean preProccessed = false;
   private ITrxBase trxInstance = null;
   private int key = 0;
   private String parameter = "";
   private boolean escapeAccepted = false;
   

	public boolean isEscapeAccepted() {
		return escapeAccepted;
	}

	public void setEscapeAccepted(boolean escapeAccepted) {
		this.escapeAccepted = escapeAccepted;
	}

	public String getParameter() {
	    return parameter;
    }

    public void setParameter(String parameter) {
	    this.parameter = parameter;
    }

	public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public ITrxBase getTrxInstance() {
         return trxInstance;
    }

    public void setTrxInstance(ITrxBase trxInstance) {
        this.trxInstance = trxInstance;
    }

    public Trx() {
        // ME ASEGURO QUE EXISTAN pf1,...,pf9,pf10,pf11,pf12
        for( int i = 1; i <= 12; ++i ) {
            buttonList.add( new Button("pf"+i) );
        }
    }
    
    public boolean isPreProccessed() {
        return preProccessed;
    }

    public void setPreProccessed(boolean preProccessed) {
        this.preProccessed = preProccessed;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public void addReturnAction( ReturnAction ra ) {
       retActions.add( ra );
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void AddButton( Button b ) {
        for( Button bb : buttonList ) {
            if( bb.getName().equalsIgnoreCase(b.getName()) ) {
                buttonList.remove(bb);
                break;
            }
        }
        buttonList.add( b );
    }
    
    public ArrayList<ReturnAction> getRetActions() {
        return retActions;
    }

    public void setRetActions(ArrayList<ReturnAction> retActions) {
        this.retActions = retActions;
    }

    public String getClassName() {
       return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPreClassName() {
        return preClassName;
    }

    public void setPreClassName(String className) {
         this.preClassName = className;
    }

    public String getPostClassName() {
        return postClassName;
    }

    public void setPostClassName(String className) {
        this.postClassName = className;
    }

    public ArrayList<Button> getButtonList() {
        return buttonList;
    }

    public void setButtonList(ArrayList<Button> buttonList) {
        this.buttonList = buttonList;
    }

    public String getName() {
        return name;
    }

   public void setName(String name) {
      this.name = name;
   }

   public ReturnAction getReturn( String id ) {
      for( ReturnAction ra: retActions ) {
          if( ra.getValue().equalsIgnoreCase(id) ) {
               return( ra );
          }
      }
      return null;
   }
   
   public Button getButton( String buttonName ) {
      for( Button b : buttonList ) {
          if( b.getName().equalsIgnoreCase(buttonName)) {
             return b;
          }
       }
       return null;
   }

   public String getButtonText( String buttonName ) {
       Button b = getButton( buttonName );
       return( b != null ? b.getText() : "" );
   }

}
