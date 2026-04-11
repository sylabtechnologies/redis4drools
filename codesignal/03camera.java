public class Solution {
    private String resolution;
    private int price;

    public Solution(String resolution, int price) {
        this.resolution = resolution;
        if (price >= 0) {
            this.price = price;
        }
    }

    public String getResolution() {
        return resolution;
    }

    public void setPrice(int price) {
        if (price >= 0) {
            this.price = price;
        }
    }

    public static void main(String[] args) {
        Solution myCamera = new Solution("1080p", 250);
        myCamera.setPrice(300);           // Update the price to a valid non-negative value
        myCamera.setPrice(-50);           // Attempt to set a negative price which should fail due to validation
        System.out.println(myCamera.getResolution());  // Expected to print '1080p'
    }
}