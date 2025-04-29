package cl.hyh.entity;

/**
 * @author abertens
 *
 */
public class ReturnAction {
    private String value      = "";
    private String action     = "";
    private String anexAction = "";
    private boolean replace   = false; // SI action=trx, REEMPLAZA LA ULTIMA DEL STACK
    private String parameter = "";

    public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
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
    public boolean isReplace() {
        return replace;
    }
    public void setReplace(boolean replace) {
        this.replace = replace;
    }

}
