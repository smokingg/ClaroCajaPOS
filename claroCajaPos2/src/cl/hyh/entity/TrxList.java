package cl.hyh.entity;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author abertens
 *
 */
public class TrxList {

   private ArrayList<Trx> trxList = new ArrayList<Trx>();

   public ArrayList<Trx> getTrxList() {
      return trxList;
   }

   public void setTrxList(ArrayList<Trx> trxList) {
      this.trxList = trxList;
   }
   
   public Trx getTrx( String name ) {
      for( Trx trx : trxList ) {
          if( trx.getName().equalsIgnoreCase(name) ) {
              return trx;
          }
      }
      return null;
   }
   
}
