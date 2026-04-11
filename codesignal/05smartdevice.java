public class Solution {
    
    // TODO: Define an abstract class named SmartDevice with powerOn and powerOff methods.
    static abstract class SmartDevice {
        abstract void powerOn();
        abstract void powerOff();
    }

    // TODO: Create a Sensor class inheriting from SmartDevice, adding an abstract method readData.
    static abstract class Sensor extends SmartDevice {
        abstract double readData();
    }

    // TODO: Implement TemperatureSensor class.
    static class TemperatureSensor extends Sensor {
        private double calibrationFactor;
        private boolean isActive;

        public TemperatureSensor(double calibrationFactor) {
            this.calibrationFactor = calibrationFactor;
            this.isActive = false;
        }

        @Override
        void powerOn() {
            this.isActive = true;
        }

        @Override
        void powerOff() {
            this.isActive = false;
        }

        @Override
        double readData() {
            if (!this.isActive) {
                return 0;
            }
            return 20.0 * this.calibrationFactor;
        }
    }

    // TODO: Implement MotionSensor class.
    static class MotionSensor extends Sensor {
        private int sensitivityLevel;
        private boolean isActive;

        public MotionSensor() {
            this.isActive = false;
            this.sensitivityLevel = 0;
        }

        @Override
        void powerOn() {
            this.isActive = true;
            this.sensitivityLevel = 5;
        }

        @Override
        void powerOff() {
            this.isActive = false;
            this.sensitivityLevel = 0;
        }

        @Override
        double readData() {
            if (!this.isActive) {
                return 0;
            }
            return this.sensitivityLevel;
        }
    }

    public static void main(String[] args) {
        // Example usage
        TemperatureSensor tempSensor = new TemperatureSensor(1.5);
        tempSensor.powerOn();
        System.out.println("Temperature Sensor Reading: " + tempSensor.readData());
        tempSensor.powerOff();
        System.out.println("Temperature Sensor Reading after power off: " + tempSensor.readData());

        MotionSensor motionSensor = new MotionSensor();
        motionSensor.powerOn();
        System.out.println("Motion Sensor Reading: " + motionSensor.readData());
        motionSensor.powerOff();
        System.out.println("Motion Sensor Reading after power off: " + motionSensor.readData());
    }
}
