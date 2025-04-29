package cl.hyh.entity;

/**
 * @author abertens
 *
 */
public class Button {
    private String name       = "";
    private String text       = "";
    private String action     = "";
    private String anexAction = "";
    private String profile    = "";
    private String parameter  = "";
    private boolean replace   = false; // SI action=trx, REEMPLAZA LA ULTIMA DEL STACK


    public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Button() {      
    }
    
    public Button( String name ) {
       this.name = name;
    }
    
    public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public boolean isReplace() {
        return replace;
    }

    public void setReplace(boolean replace) {
        this.replace = replace;
    }

    public String getAction() {
      return action;
    }

   public void setAction(String action) {
      this.action = action;
   }

   public String getAnexAction() {
      return anexAction;
   }

   public void setAnexAction(String anexAction) {
      this.anexAction = anexAction;
   }

   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getText() {
      return text;
   }
   public void setText(String text) {
      this.text = text;
   }
}
