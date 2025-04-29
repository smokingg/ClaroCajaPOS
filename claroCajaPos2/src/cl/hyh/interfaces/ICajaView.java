package cl.hyh.interfaces;

import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.OperTRV; 

/**
 * @author abertens
 *
 */
public interface ICajaView {
    /*
     * VALORE DE RETORNO PARA EXECUTE.
     * SI RETORNO > _PREPOSTRETURN, DEBE EXISTIR UN <return ...>
     */
    public final static  int _WAITFORACTION   = 1;
    public final static  int _NOWAITFORACTION = 2;
    public final static  int _PASSTHROUGH     = 3;
    public final static  int _PREPOSTRETURN   = 9;

    public int execute();
    /**
     * @param buttons = String array [9] with button texts.
     */
    public void paintButtons( String[] buttons );

    public void setTrxTitle(String trxTitle);
    
    /** 
     * Le llegara el control de la tecla ESCAPE al flujo
     * */
    public void acceptEscape(boolean accept);
    
    public String getEntryMessage();
    public void setEntryMessage(String text, boolean visible);
    
    public String getEntryTitle();
    public void setEntryTitle(String text, boolean visible);
    
    public String getEntryTextLabel();
    public void setEntryTextLabel(String text, boolean visible);
    
    public void hideEnter();
    
    public String getEntryText();
    /**
     * @param text = preset value
     * @param visible = visible or not
     * @param readOnly = readonly or editable
     * @param password = hide typed characters
     * @param regExp = regular expression for entry validation. null if not used
     * @param message = message to display if validation fails. null if not used
     */
    public void setEntryText(String text, boolean visible, boolean readOnly, 
            boolean password, String regExp, String message );
    
    /**
     * @param date = date to show on calendar. null for today
     * @param visible = visible or not
     */
    public void setEntryCalendar( java.util.Date date, boolean visible );
    public java.util.Date getEntryCalendarDate();
    
    public String getEntryTextArea();
    /**
     * @param text = text, use '\n' to break lines
     * @param visible = visible or not
     */
    public void setEntryTextArea( String text, boolean visible );

    /**
     * @param list = String array with list values
     * @param visible = visible or not
     * @param multiple = multiple selection if true
     */
    public void setEntryList(String[] list, boolean visible, boolean multiple);
    public void setEntryList(String[] list, boolean visible, boolean multiple,int index);
    public int getEntryListIndex();
    public int[] getEntryListIndeces();
    public void cleanEntryList();
    public String[] getEntryList();
    
    public void setEntryTable(String[] title, Object[][] rows, boolean visible );
    public int getEntryTableIndex();
    public void setEntryTableIndex( int index );

    public void hideAllEntries();
    public void clearAllEntries();
    /**
     * @param timeout = timeout in seconds to wait for user. If occurs, key=1 to user.
     */
    public void setInputTimeout( int timeout );    // SEGUNDOS, 0=INFINITO (OMISION)

    public int showMyConfirmDialog( String title, String message );
    
    public String openSolicitaTrajetas();

    public void showBusyWindow( String title, String message );
    public void hideBusyWindow();    

    // Table View
    public void showTableView( String[] titles, Object[][] rows, boolean[] editable );
    public void dismissTableView();
    public void setTableViewIndex( int index );
    public int getTableViewIndex();
    
    public void removeTRV();
    public void createTRV();
    public OperTRV getOperTRV();
    
    public Datos getDatos();
    
    public void setMaxPasswordSize(Integer maxPasswordSize);
}
