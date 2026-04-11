public class Solution {
    private int coffeeLevel;

    public Solution() {
        this.coffeeLevel = 0;  // Public attribute
    }

    public void makeCoffee() {
        if (this.coffeeLevel > 0) {
            System.out.println("Enjoy your coffee!");
            this.coffeeLevel -= 1;
        } else {
            System.out.println("Please refill coffee!");
        }
    }

    // TODO: Add missing method to refill coffee
    // The method takes the amount to refill and adds it to the current coffee amount
    public void refillCoffee(int refill) {
        if (refill > 0) this.coffeeLevel += refill;
        System.out.println("Refilled w/ " + refill);
    }

    public static void main(String[] args) {
        Solution machine = new Solution();
        machine.refillCoffee(3); // Refill with 3 units of coffee
        machine.makeCoffee();    // Make coffee
        machine.makeCoffee();    // Make another coffee
        machine.makeCoffee();    // And another one
        machine.makeCoffee();    // Now it should request a refill
    }
}
