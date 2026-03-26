import java.util.*;

// https://leetcode.com/problems/design-auction-system/description/

public class Lcode485C {
    public static void main(String[] args) {
        AuctionSystem auctionSystem = new AuctionSystem();

        auctionSystem.addBid(1, 7, 5); // User 1 bids 5 on item 7
        auctionSystem.addBid(2, 7, 6); // User 2 bids 6 on item 7
        auctionSystem.getHighestBidder(7); // return 2 as User 2 has the highest bid
        auctionSystem.updateBid(1, 7, 8); // User 1 updates bid to 8 on item 7
        auctionSystem.getHighestBidder(7); // return 1 as User 1 now has the highest bid
        auctionSystem.removeBid(2, 7); // Remove User 2's bid on item 7
        auctionSystem.getHighestBidder(7); // return 1 as User 1 is the current highest bidder
        auctionSystem.getHighestBidder(3); // return -1 as no bids exist for item 3        
    }

}

class AuctionSystem {
    HashSet<Integer> users;
    HashSet<Integer> items;
    HashMap<Integer, HashMap<Integer, Integer>> bids; // itemId -> (userId -> bidAmount)

    public AuctionSystem() {
        users = new HashSet<>();
        items = new HashSet<>();
        bids = new HashMap<>();
    }
    
    public void addBid(int userId, int itemId, int bidAmount) {
        users.add(userId);
        items.add(itemId);
        var bidsForItem = bids.get(itemId);
        if (bidsForItem == null) {
            bidsForItem = new HashMap<>();
            bidsForItem.put(userId, bidAmount);
            bids.put(itemId, bidsForItem);
        }
        else {
            bidsForItem.put(userId, bidAmount);
        }

        System.out.println(bids);
    }

    public void updateBid(int userId, int itemId, int newAmount) {
        var bidsForItem = bids.get(itemId);
        bidsForItem.put(userId, newAmount);

        System.out.println(bids);
    }
    
    public void removeBid(int userId, int itemId) {
        var bidsForItem = bids.get(itemId);
        bidsForItem.remove(userId);        

        System.out.println(bids);
    }
    
    public int getHighestBidder(int itemId) {
        var bidsForItem = bids.get(itemId);
        if (bidsForItem == null) {
            return -1;
        }

        int count = 0;
        int maxBid = -1;
        int maxUser = -1;
        for (var entry : bidsForItem.entrySet()) {
            count++;

            if (count == 1) { // first one only
                maxUser = entry.getKey();
                maxBid = entry.getValue();
                continue;
            }

            if (entry.getValue() == maxBid) {
                maxUser = Math.max(entry.getKey(), maxUser);
            }
            else if (entry.getValue() > maxBid) {
                maxUser = entry.getKey();
                maxBid = entry.getValue();
            }
        }


        System.out.println(bids);
        System.out.println(maxUser);

        return maxUser;
    }
}