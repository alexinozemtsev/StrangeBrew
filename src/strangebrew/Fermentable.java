/**
 * Created on Oct 4, 2004
 * $Id: Fermentable.java,v 1.2 2004/10/18 20:34:04 andrew_avis Exp $
 * @author aavis
 *
 * This is the base malt class.  It doesn't do much, except hold data
 * and get/set data
 */

package strangebrew;

public class Fermentable {
	// base data
	private String name;
	private double pppg;
	private double lov;
	private String maltster;
	private String country;
	private String type;
	private boolean mashed;
	private double costPerU;
	private String description;

	/* recipe data
	* not sure if these should be here, probably not - add to
	* parallel array in recipe?
	* Similar issue with database data
	*/	
	
	public Quantity amount = new Quantity();
	// public double amount;
	// public String units;
	public double percent;

	// constructors:
	public Fermentable(String n, double p, double l, double a, String u) {
		name = n;
		pppg = p;
		lov = l;
		amount.setQuantity(u, null, a);
		mashed = true;
	}
	
	public Fermentable(){
		// default constructor
		name = "Malt";
		pppg = 0;
		lov = 0;
		amount.setQuantity("pounds", "lb", 1.0);
		mashed = true;
			// I want to get options working sometime,
			// but can't figure out how:
			// myOptions.getProperty("optMaltUnits");
			
	}
	
	// getter methods:
	public boolean getMashed(){ return mashed; }
	public double getPppg(){ return pppg; }
	public double getLov(){ return lov; }
	public double getCostPerU(){ return costPerU; }
	
	
	// setter methods:
	public void setName(String n){ name = n; }
	public void setAmount(double a){ amount.setQuantity(null, null, a); }
	public void setPppg(double p){ pppg = p; }
	public void setCost(double c){ costPerU = c; }
	public void setCost(String c){
		if (c.substring(0,1).equals("$")) {
			c = c.substring(1, c.length()); // trim leading "$"
		}
		costPerU = Double.parseDouble(c);
	}
	public void setLov(double l){ lov = l; }
	public void setDesc(String d){ description = d; }
	public void setUnits(String u){	amount.setQuantity(null, u, 0);	}
	
	/**
	 * Handles a string of the form "d u", where d is a double
	 * amount, and u is a string of units.  For importing the
	 * quantity attribute from QBrew xml.
	 * @param a
	 */
	public void setAmountAndUnits(String a){
		int i = a.indexOf(" ");
		String d = a.substring(0,i);
		String u = a.substring(i);
		amount.setQuantity(null, u.trim(), Double.parseDouble(d.trim()));
	}
	
	
	
	// Need to add the spaces and type attributes to make this
	// backwards-compatible with SB1.8:
	public String toXML(){
	    StringBuffer sb = new StringBuffer();
	    sb.append( "    <ITEM>\n" );
	    sb.append( "      <MALT>"+name+"</MALT>\n" );
	    sb.append( "      <AMOUNT>"+amount.getValue()+"</AMOUNT>\n" );
	    sb.append( "      <UNITS>"+amount.getUnits()+"</UNITS>\n" );
	    sb.append( "      <POINTS>"+pppg+"</POINTS>\n" );
	    sb.append( "    </ITEM>\n" );
	    return sb.toString();
	}

}