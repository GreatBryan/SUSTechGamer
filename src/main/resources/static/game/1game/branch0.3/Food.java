
public class Food {
	private String name;
	private String type;
	private int size;
	private double price;
	public static final double DEFAULT_SEAFOOD_PRICE = 40.0;
	public static final double DEFAULT_BEEF_PRICE = 35.0;

	String DEFAULT_TYPE = "seafood";

	Food(String name, String type, int size, double price) {
		this.name = name;
		this.type = type;
		this.size = size;
		this.price = price;
	}

	Food(String name, int size) {
		this.name = name;
		this.size = size;
		setDefaultType();
		setDefaultPrice();
	}

	Food(String name, String type, int size) {
		this.name = name;
		this.type = type;
		this.size = size;
		setDefaultPrice();
	}
	
	private void setDefaultType() {
		this.type = DEFAULT_TYPE;
	}
	
	private void setDefaultPrice() {
		if (type.equals("beef")) {
			this.price = DEFAULT_BEEF_PRICE;
		} else {
			this.price = DEFAULT_SEAFOOD_PRICE;
		}
	}

	@Override
	public String toString() {
		return String.format("%s %s: (%d Inches) %.2f $\n", this.type, this.name, this.size, this.price);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
