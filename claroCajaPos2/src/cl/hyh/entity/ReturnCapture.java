package cl.hyh.entity;

/**
 * @author abertens
 *
 */
public class ReturnCapture {
    private Trx trx = null;
    private boolean replace = false;
    private int rc = 0;
    private String raParameter = "";
    private String btnParameter = "";
    private String trxName = "";
    
    public String getTrxName() {
		return trxName;
	}

	public void setTrxName(String trxName) {
		this.trxName = trxName;
	}

	public String getBtnParameter() {
		return btnParameter;
	}

	public void setBtnParameter(String btnParameter) {
		this.btnParameter = btnParameter;
	}

	public String getRaParameter() {
		return raParameter;
	}

	public void setRaParameter(String parameter) {
		this.raParameter = parameter;
	}

	public ReturnCapture() {
    }

    public ReturnCapture( int rc ) {
        this.rc = rc;
    }
    public int getRc() {
        return rc;
    }
    public void setRc(int rc) {
        this.rc = rc;
    }
    public Trx getTrx() {
        return trx;
    }
    public void setTrx(Trx trx) {
        this.trx = trx;
    }
    public boolean isReplace() {
        return replace;
    }
    public void setReplace(boolean replace) {
        this.replace = replace;
    }
}
