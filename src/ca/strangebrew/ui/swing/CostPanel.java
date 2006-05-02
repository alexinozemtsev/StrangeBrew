package ca.strangebrew.ui.swing;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import ca.strangebrew.Quantity;
import ca.strangebrew.Recipe;
import ca.strangebrew.SBStringUtils;

public class CostPanel extends javax.swing.JPanel {
	private JPanel jPanel1;
	private JLabel jLabel4;
	private JLabel bottleCostLbl;
	private JLabel numBottlesLbl;
	private JLabel finalVolLbl;
	private JComboBox bottleSizeUCmb;
	private ComboModel bottleSizeUCmbModel;
	private JTextField bottleSizeTxt;
	private JTextField yeastTxt;
	private JLabel hopsLbl;
	private JLabel jLabel8;
	private JLabel totalLbl;
	private JTextField otherTxt;
	private JLabel miscLbl;
	private JLabel grainLbl;
	private JLabel jLabel10;
	private JLabel jLabel9;
	private JLabel jLabel7;
	private JLabel jLabel6;
	private JLabel jLabel5;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JLabel jLabel1;
	private JPanel jPanel2;

	private Recipe myRecipe;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new CostPanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	public CostPanel() {
		super();
		initGUI();
	}

	public void setData(Recipe r) {
		myRecipe = r;
		// displayCost();
	}

	public void displayCost(){
		grainLbl.setText(SBStringUtils.myNF.format(myRecipe.getTotalMaltCost()));
		hopsLbl.setText(SBStringUtils.myNF.format(myRecipe.getTotalHopsCost()));
		yeastTxt.setText(SBStringUtils.myNF.format(myRecipe.getYeastObj().getCostPerU()));
		miscLbl.setText(SBStringUtils.myNF.format(myRecipe.getTotalMiscCost()));
		otherTxt.setText(SBStringUtils.myNF.format(myRecipe.getOtherCost()));
		double totalCost = myRecipe.getTotalMaltCost() + 
			myRecipe.getTotalHopsCost() +
			myRecipe.getYeastObj().getCostPerU() +
			myRecipe.getTotalMiscCost();
		
		totalLbl.setText(SBStringUtils.myNF.format(totalCost));

		bottleSizeTxt.setText(SBStringUtils.df0.format(myRecipe.getBottleSize()));
		bottleSizeUCmbModel.addOrInsert(myRecipe.getBottleU());

		finalVolLbl.setText(myRecipe.getFinalWortVol() + " " + myRecipe.getVolUnits());
		double numBottles = Quantity.convertUnit(myRecipe.getVolUnits(), 
				myRecipe.getBottleU(), 
				Double.parseDouble(myRecipe.getFinalWortVol())) / myRecipe.getBottleSize();
		numBottlesLbl.setText(SBStringUtils.df1.format(numBottles));
		bottleCostLbl.setText(SBStringUtils.myNF.format( totalCost/numBottles ));


	}	private void initGUI() {
		try {

			jPanel1 = new JPanel();
			GridBagLayout jPanel1Layout = new GridBagLayout();
			jPanel1Layout.rowWeights = new double[]{0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
			jPanel1Layout.rowHeights = new int[]{7, 7, 7, 7, 7, 7};
			jPanel1Layout.columnWeights = new double[]{0.1, 0.1, 0.1, 0.1};
			jPanel1Layout.columnWidths = new int[]{7, 7, 7, 7};
			jPanel1.setLayout(jPanel1Layout);
			BoxLayout thisLayout = new BoxLayout(this, javax.swing.BoxLayout.X_AXIS);
			this.setLayout(thisLayout);
			this.add(jPanel1);
			jPanel1.setBorder(BorderFactory.createTitledBorder("Recipe Cost:"));

			jLabel1 = new JLabel();
			jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			jLabel1.setText("Grain:");

			jLabel2 = new JLabel();
			jPanel1.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			jLabel2.setText("Hops:");

			jLabel3 = new JLabel();
			jPanel1.add(jLabel3, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			jLabel3.setText("Misc Ingr.:");

			jLabel4 = new JLabel();
			jPanel1.add(jLabel4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			jLabel4.setText("Yeast:");

			jLabel5 = new JLabel();
			jPanel1.add(jLabel5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			jLabel5.setText("Other:");

			jLabel6 = new JLabel();
			jPanel1.add(jLabel6, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			jLabel6.setText("Total:");
			jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));

			grainLbl = new JLabel();
			jPanel1.add(grainLbl, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			grainLbl.setText("jLabel11");

			hopsLbl = new JLabel();
			jPanel1.add(hopsLbl, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			hopsLbl.setText("jLabel11");

			yeastTxt = new JTextField();
			jPanel1.add(yeastTxt, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			yeastTxt.setText("jLabel11");

			miscLbl = new JLabel();
			jPanel1.add(miscLbl, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			miscLbl.setText("jLabel11");

			otherTxt = new JTextField();
			jPanel1.add(otherTxt, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			otherTxt.setText("jLabel11");

			totalLbl = new JLabel();
			jPanel1.add(totalLbl, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			totalLbl.setText("jLabel11");
			totalLbl.setFont(new java.awt.Font("Tahoma", 1, 11));

			jPanel2 = new JPanel();
			GridBagLayout jPanel2Layout = new GridBagLayout();
			jPanel2Layout.rowWeights = new double[]{0.1, 0.1, 0.1, 0.1};
			jPanel2Layout.rowHeights = new int[]{7, 7, 7, 7};
			jPanel2Layout.columnWeights = new double[]{0.1, 0.1, 0.1, 0.1};
			jPanel2Layout.columnWidths = new int[]{7, 7, 7, 7};
			jPanel2.setLayout(jPanel2Layout);
			this.add(jPanel2);
			jPanel2.setBorder(BorderFactory.createTitledBorder("Bottle Cost:"));

			jLabel7 = new JLabel();
			jPanel2.add(jLabel7, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			jLabel7.setText("Bottle Size:");

			jLabel8 = new JLabel();
			jPanel2.add(jLabel8, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			jLabel8.setText("Final Volume:");

			jLabel9 = new JLabel();
			jPanel2.add(jLabel9, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			jLabel9.setText("# Bottles:");

			jLabel10 = new JLabel();
			jPanel2.add(jLabel10, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			jLabel10.setText("Cost / Bottle:");

			bottleSizeTxt = new JTextField();
			jPanel2.add(bottleSizeTxt, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			bottleSizeTxt.setText("jTextField1");

			bottleSizeUCmbModel = new ComboModel();
			bottleSizeUCmbModel.setList(new Quantity().getListofUnits("vol"));

			bottleSizeUCmb = new JComboBox();
			jPanel2.add(bottleSizeUCmb, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			bottleSizeUCmb.setModel(bottleSizeUCmbModel);

			finalVolLbl = new JLabel();
			jPanel2.add(finalVolLbl, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			finalVolLbl.setText("jLabel11");

			numBottlesLbl = new JLabel();
			jPanel2.add(numBottlesLbl, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			numBottlesLbl.setText("jLabel11");

			bottleCostLbl = new JLabel();
			jPanel2.add(bottleCostLbl, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
					0));
			bottleCostLbl.setText("jLabel11");

			setPreferredSize(new Dimension(400, 300));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}