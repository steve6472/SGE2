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
	HYDROGEN("Hydrogen", 1, "H", 1.008, GAS),
	HELIUM("Helium", 2, "He", 4.0026, GAS),
	LITHIUM("Lithium", 3, "Li", 6.94, SOLID),
	BERYLLIUM("Beryllium", 4, "Be", 9.0122, SOLID),
	BORON("Boron", 5, "B", 10.81, SOLID),
	CARBON("Carbon", 6, "C", 12.011, SOLID),
	NITROGEN("Nitrogen", 7, "N", 14.007, GAS),
	OXYGEN("Oxygen", 8, "O", 15.999, GAS),
	FLUORINE("Fluorine", 9, "F", 18.998, GAS),
	NEON("Neon", 10, "Ne", 20.180, GAS),
	SODIUM("Sodium", 11, "Na", 22.990, SOLID),
	MAGNESIUM("Magnesium", 12, "Mg", 24.305, SOLID),
	ALUMINIUM("Aluminium", 13, "Al", 26.982, SOLID),
	SILICON("Silicon", 14, "Si", 28.085, SOLID),
	PHOSPHORUS("Phosporus", 15, "P", 90.974, SOLID),
	SULFUR("Sulfur", 16, "S", 32.06, SOLID),
	CHLORINE("Chlorine", 17, "Cl", 35.45, GAS),
	ARGON("Argon", 18, "Ar", 39.948, GAS),
	POTASSIUM("Potassium", 19, "K", 39.098, SOLID),
	CALCIUM("Calcium", 20, "Ca", 40.078, SOLID),
	SCANDIUM("Scandium", 21, "Sc", 44.956, SOLID),
	TITANIUM("Titanium", 22, "Ti", 47.867, SOLID),
	VANADIUM("Vanadium", 23, "V", 50.942, SOLID),
	CHROMIUM("Chromium", 24, "Cr", 51.996, SOLID),
	MANGANESE("Manganese", 25, "Mn", 54.938, SOLID),
	IRON("Iron", 26, "Fe", 55.845, SOLID),
	COBALT("Cobalt", 27, "Co", 58.933, SOLID),
	NICKEL("Nickel", 28, "Ni", 58.693, SOLID),
	COPPER("Copper", 29, "Cu", 63.546, SOLID),
	ZINC("Zinc", 30, "Zn", 65.38, SOLID),
	GALLIUM("Gallium", 31, "Ga", 69.723, SOLID),
	GERMANIUM("Germanium", 32, "Ge", 72.630, SOLID),
	ARSENIC("Arsenic", 33, "As", 74.922, SOLID),
	SELENIUM("Selenium", 34, "Se", 78.971, SOLID),
	BROMINE("Bromine", 35, "Br", 79.904, LIQUID),
	KRYPTON("Krypton", 36, "Kr", 89.798, GAS),
	RUBIDIUM("Rubidium", 37, "Rb", 85.468, SOLID),
	STRONTIUM("Strontium", 38, "Sr", 87.62, SOLID),
	YTTRIUM("Yttrium", 39, "Y", 88.906, SOLID),
	ZIRCONIUM("Zirconium", 40, "Zr", 91.224, SOLID),
	NIOBIUM("Niobium", 41, "Nb", 92.906, SOLID),
	MOLYBDENUR("Molybdenur", 42, "Mo", 95.95, SOLID),
	TECHNETIUM("Technetium", 43, "Tc", 98, SOLID);
	
	private int number;
	private String name;
	private double weight;
	private String symbol;
	private StateOfMatter stateOfMatter;
	
	Element(String name, int number, String symbol, double weight, StateOfMatter stateOfMatter)
	{
		this.name = name;
		this.number = number;
		this.symbol = symbol;
		this.weight = weight;
		this.stateOfMatter = stateOfMatter;
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

}
