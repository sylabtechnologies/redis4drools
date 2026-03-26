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

    // Helper class to track an item's bids cleanly
    private class ItemAuction {
        HashMap<Integer, Integer> userBids = new HashMap<>();
        TreeMap<Integer, TreeSet<Integer>> amountToUsers = new TreeMap<>();

        public void addOrUpdate(int userId, int bidAmount) {
            // If the user already has a bid, we must remove it from the TreeMap first
            if (userBids.containsKey(userId)) {
                removeUser(userId);
            }

            // 1. Add to the standard HashMap
            userBids.put(userId, bidAmount);

            // 2. Add to the TreeMap
            amountToUsers.putIfAbsent(bidAmount, new TreeSet<>());
            amountToUsers.get(bidAmount).add(userId);
        }

        public void removeUser(int userId) {
            Integer oldAmount = userBids.remove(userId);
            if (oldAmount != null) {
                // Remove the user from the TreeSet
                TreeSet<Integer> usersWithBid = amountToUsers.get(oldAmount);
                usersWithBid.remove(userId);

                // If no one else has this bid amount, clean up the TreeMap
                if (usersWithBid.isEmpty()) {
                    amountToUsers.remove(oldAmount);
                }
            }
        }
        
        public int getHighestBidder() {
            if (amountToUsers.isEmpty()) {
                return -1;
            }
            // lastEntry() gets the highest bid amount in O(log M) or O(1) time
            // last() gets the highest userId in that bid amount to break ties
            return amountToUsers.lastEntry().getValue().last();
        }
    }

    // itemId -> ItemAuction
    private HashMap<Integer, ItemAuction> auctions;

    public AuctionSystem() {
        auctions = new HashMap<>();
    }

    public void addBid(int userId, int itemId, int bidAmount) {
        auctions.putIfAbsent(itemId, new ItemAuction());
        auctions.get(itemId).addOrUpdate(userId, bidAmount);
    }

    public void updateBid(int userId, int itemId, int newAmount) {
        ItemAuction auction = auctions.get(itemId);
        if (auction != null && auction.userBids.containsKey(userId)) {
            auction.addOrUpdate(userId, newAmount);
        }
    }

    public void removeBid(int userId, int itemId) {
        ItemAuction auction = auctions.get(itemId);
        if (auction != null) {
            auction.removeUser(userId);
        }
    }

    public int getHighestBidder(int itemId) {
        ItemAuction auction = auctions.get(itemId);
        if (auction == null) {
            return -1;
        }
        return auction.getHighestBidder();
    }
}
