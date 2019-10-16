/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 30. 1. 2018
* Project: SGE
*
***********************/

package com.steve6472.sge.main.periodicTable;
import static com.steve6472.sge.main.periodicTable.StateOfMatter.*;

/**
 * Data stolen from {@link} https://www.ptable.com/?lang=en
 * @author Mirek
 *
 */
public enum Element
{
	HYDROGEN        ("Hydrogen",        1,    "H",  1.008,  GAS,    0.0899),
	HELIUM          ("Helium",          2,    "He", 4.0026, GAS,    0),
	LITHIUM         ("Lithium",         3,    "Li", 6.94,   SOLID,  0),
	BERYLLIUM       ("Beryllium",       4,    "Be", 9.0122, SOLID,  0),
	BORON           ("Boron",           5,    "B",  10.81,  SOLID,  0),
	CARBON          ("Carbon",          6,    "C",  12.011, SOLID,  1.95),
	NITROGEN        ("Nitrogen",        7,    "N",  14.007, GAS,    0),
	OXYGEN          ("Oxygen",          8,    "O",  15.999, GAS,    0),
	FLUORINE        ("Fluorine",        9,    "F",  18.998, GAS,    0),
	NEON            ("Neon",            10,   "Ne", 20.180, GAS,    0),
	SODIUM          ("Sodium",          11,   "Na", 22.990, SOLID,  0),
	MAGNESIUM       ("Magnesium",       12,   "Mg", 24.305, SOLID,  0),
	ALUMINIUM       ("Aluminium",       13,   "Al", 26.982, SOLID,  0),
	SILICON         ("Silicon",         14,   "Si", 28.085, SOLID,  0),
	PHOSPHORUS      ("Phosporus",       15,   "P",  90.974, SOLID,  0),
	SULFUR          ("Sulfur",          16,   "S",  32.06,  SOLID,  0),
	CHLORINE        ("Chlorine",        17,   "Cl", 35.45,  GAS,    0),
	ARGON           ("Argon",           18,   "Ar", 39.948, GAS,    0),
	POTASSIUM       ("Potassium",       19,   "K",  39.098, SOLID,  0),
	CALCIUM         ("Calcium",         20,   "Ca", 40.078, SOLID,  0),
	SCANDIUM        ("Scandium",        21,   "Sc", 44.956, SOLID,  0),
	TITANIUM        ("Titanium",        22,   "Ti", 47.867, SOLID,  4.506),
	VANADIUM        ("Vanadium",        23,   "V",  50.942, SOLID,  0),
	CHROMIUM        ("Chromium",        24,   "Cr", 51.996, SOLID,  7.19),
	MANGANESE       ("Manganese",       25,   "Mn", 54.938, SOLID,  0),
	IRON            ("Iron",            26,   "Fe", 55.845, SOLID,  7.874),
	COBALT          ("Cobalt",          27,   "Co", 58.933, SOLID,  8.9),
	NICKEL          ("Nickel",          28,   "Ni", 58.693, SOLID,  8.908),
	COPPER          ("Copper",          29,   "Cu", 63.546, SOLID,  8.96),
	ZINC            ("Zinc",            30,   "Zn", 65.38,  SOLID,  0),
	GALLIUM         ("Gallium",         31,   "Ga", 69.723, SOLID,  0),
	GERMANIUM       ("Germanium",       32,   "Ge", 72.630, SOLID,  0),
	ARSENIC         ("Arsenic",         33,   "As", 74.922, SOLID,  0),
	SELENIUM        ("Selenium",        34,   "Se", 78.971, SOLID,  0),
	BROMINE         ("Bromine",         35,   "Br", 79.904, LIQUID, 0),
	KRYPTON         ("Krypton",         36,   "Kr", 89.798, GAS,    0),
	RUBIDIUM        ("Rubidium",        37,   "Rb", 85.468, SOLID,  0),
	STRONTIUM       ("Strontium",       38,   "Sr", 87.62,  SOLID,  0),
	YTTRIUM         ("Yttrium",         39,   "Y",  88.906, SOLID,  0),
	ZIRCONIUM       ("Zirconium",       40,   "Zr", 91.224, SOLID,  0),
	NIOBIUM         ("Niobium",         41,   "Nb", 92.906, SOLID,  0),
	MOLYBDENUR      ("Molybdenur",      42,   "Mo", 95.95,  SOLID,  0),
	TECHNETIUM      ("Technetium",      43,   "Tc", 98,     SOLID,  0);
	
	private int number;
	private String name;
	private double weight;
	private String symbol;
	private StateOfMatter stateOfMatter;
	/**
	 * kg/m3
	 */
	private double density;
	
	Element(String name, int number, String symbol, double weight, StateOfMatter stateOfMatter, double density)
	{
		this.name = name;
		this.number = number;
		this.symbol = symbol;
		this.weight = weight;
		this.stateOfMatter = stateOfMatter;
		this.density = density;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public String getName()
	{
		return name;
	}
	public double getWeight()
	{
		return weight;
	}
	
	public String getSymbol()
	{
		return symbol;
	}
	
	public StateOfMatter getStateOfMatter()
	{
		return stateOfMatter;
	}

	public double getDensity()
	{
		return density;
	}
}
