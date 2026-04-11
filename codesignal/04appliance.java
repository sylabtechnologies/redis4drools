public class Solution {

    abstract static class HomeAppliance {
        abstract String powerOn();
    }

    static class Refrigerator extends HomeAppliance {
        @Override
        String powerOn() {
            return "Refrigerator is now on and cooling.";
        }
    }

    static class Oven extends HomeAppliance {
        @Override
        String powerOn() {
            return "Oven is now on and heating.";
        }
    }

    public static void main(String[] args) {
        HomeAppliance fridge = new Refrigerator();
        HomeAppliance oven = new Oven();
        System.out.println(fridge.powerOn());  // Output: Refrigerator is now on and cooling.
        System.out.println(oven.powerOn());    // Output: Oven is now on and heating.
    }
}
