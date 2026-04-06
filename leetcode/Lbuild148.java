
public class Lbuild148 {
    int nums[] = {5,1,1,2,0,0};

    public static void main(String[] args) {

        ListNode head = new ListNode(4);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(3);

        Solution solution = new Solution();
        head = solution.sortList(head);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }

    }
}

class Solution {
    public ListNode sortList(ListNode head) {
        return mergeSort(head);
    }

    private ListNode mergeSort(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // swap just 2
        if (head.next.next == null) {
            if (head.val > head.next.val) {
                ListNode temp = head;
                head = head.next;
                head.next = temp;
                temp.next = null;
            }
            return head;
        }

        // halve it
        ListNode slow = head, fast = head, prev = null;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        // and cut
        prev.next = null;

        var leftPart = mergeSort(head);
        var rightPart = mergeSort(slow);

        return merge(leftPart, rightPart);
    }

    private ListNode merge(ListNode left, ListNode right) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (left != null && right != null) {
            if (left.val < right.val) {
                current.next = left;
                left = left.next;
            } else {
                current.next = right;
                right = right.next;
            }
            current = current.next;
        }

        if (left != null) {
            current.next = left;
        } else {
            current.next = right;
        }

        return dummy.next;
    }
}

class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
