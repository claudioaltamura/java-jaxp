package de.claudioaltamura.jaxp.stax;

public class Item {
	private String date;
	private String unit;
	private String current;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	@Override
	public String toString() {
		return "Item [date=" + date + ", unit=" + unit + ", current=" + current + "]";
	}

}
