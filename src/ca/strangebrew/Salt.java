package ca.strangebrew;

import java.util.ArrayList;

public class Salt {
	private String name;
	private String commonName;
	private String chemicalName;
	private double amount = 0.0;
	private String amountU = "gr";
	private ArrayList chemicalEffects = new ArrayList();
	
	public static final String MAGNESIUM = "Mg";
	public static final String CHLORINE = "Cl";
	public static final String SODIUM = "Na";
	public static final String SULPHATE = "So4"; 
	public static final String CARBONATE = "Co3";
	public static final String CALCIUM = "Ca";
	public static final String HARDNESS = "Hardness";
	public static final String ALKALINITY = "Alkalinity";
	
	public Salt() { 
	}

	public double getAmount() {
		return amount;
	}

	public String getAmountU() {
		return amountU;
	}

	public String getChemicalName() {
		return chemicalName;
	}

	public String getCommonName() {
		return commonName;
	}

	public String getName() {
		return name;
	}

	public ArrayList getChemicalEffects() {
		return chemicalEffects;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String toString() {
		String str = String.format("%s (%s) %3.1d%s",
				new Object[] {name,
				commonName,
				new Double(amount),
				amountU});
		return str;
	}
	
	public String toXML(int indent) {
		String xml = "";
		
		xml += SBStringUtils.xmlElement("NAME", name, indent);
		xml += SBStringUtils.xmlElement("COMMONNAME", commonName, indent);
		xml += SBStringUtils.xmlElement("CHEM", chemicalName, indent);
		xml += SBStringUtils.xmlElement("AMOUNT", SBStringUtils.format(amount, 2), indent);
		xml += SBStringUtils.xmlElement("AMOUNTU", amountU, indent);		
			
		return xml;
	}

	// TODO currently only in grams
	/*public void setAmountU(String amountU) {
		this.amountU = amountU;
	}*/

	public void addChemicalEffect(String elem, double val) {
		// Remove old effect of same name first!
		for (int i = 0; i < this.chemicalEffects.size(); i++) {
			ChemicalEffect temp = (ChemicalEffect)this.chemicalEffects.get(i);
			if (temp.getElem().equals(elem)) {
				this.chemicalEffects.remove(i);
				break;
			}
		}

		ChemicalEffect e = new ChemicalEffect(elem, val);
		this.chemicalEffects.add(e);
	}
	
	public void setChemicalEffects(ArrayList effs) {
		this.chemicalEffects.clear();
		this.chemicalEffects.addAll(effs);
	}

	public void setChemicalName(String chemicalName) {
		this.chemicalName = chemicalName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public class ChemicalEffect {
		final private String elem;
		final private double effect;
		
		public ChemicalEffect(String elem, double effect) {
			this.elem = elem;
			this.effect = effect;
		}

		public double getEffect() {
			return effect;
		}

		public String getElem() {
			return elem;
		}
	}


	public double getEffectByChem(String chem) {
		for (int i = 0; i < chemicalEffects.size(); i++) {
			if (((ChemicalEffect)chemicalEffects.get(i)).getElem().equals(chem)) {
				return ((ChemicalEffect)chemicalEffects.get(i)).getEffect();
			}
		}
		
		return 0;
	}
	
	static public Salt getSaltByName(ArrayList salts, String name) {
		for (int i = 0; i < salts.size(); i++) {
			if (((Salt)salts.get(i)).getName().equals(name)) {
				return (Salt)salts.get(i);
			}
		}
		
		return null;
	}
}