public class Solution {
    private String model; // Private attribute for the laptop's model
    private double price; // Private attribute for the laptop's price

    public Solution(String model, double price) {
        this.model = model;
        this.price = price;
    }
    
    public String getModel() {
        return this.model;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        if (price > 0)
            this.price = price;
        else
            System.out.println("invalid price!");
    }

    public static void main(String[] args) {
        // Example usage:
        Solution myLaptop = new Solution("ThinkPad X1", 1500);
        myLaptop.setPrice(1200); // Update the price
        System.out.println(myLaptop.getModel()); // Output: 'ThinkPad X1'
        myLaptop.setPrice(-100); // Expected to print: "Error: Price must be positive"
    }
}
