#include <iostream>
#include <vector>
#include <list>

// implement the queues on the stack for limited memory use
// https://leetcode.com/problems/design-ride-sharing-system/description/

using namespace std;
class RideSharingSystem {
public:
    RideSharingSystem() {
    }
    
    void addRider(int riderId) {
        ridersQueue.push_back(riderId);
    }

    void addDriver(int driverId) {
        driversQueue.push_back(driverId);
    }
    
    vector<int> matchDriverWithRider() {
//        printQueues();

        if (ridersQueue.empty() || driversQueue.empty()) {
            return {-1, -1};
        }
        int riderId = ridersQueue.front();
        int driverId = driversQueue.front();
        ridersQueue.pop_front();
        driversQueue.pop_front();
        return {driverId, riderId};
    }
    
    void cancelRider(int riderId) {
        ridersQueue.remove(riderId);        
    }

private:
    list<int> ridersQueue;
    list<int> driversQueue;

    void printQueues() {
        cout << "Riders Queue: ";
        for (const auto& rider : ridersQueue) {
            cout << rider << " ";
        }
        cout << endl;
        cout << "Drivers Queue: ";
        for (const auto& driver : driversQueue) {
            cout << driver << " ";
        }
        cout << endl;
    }
};

int main() {
    RideSharingSystem system;

    // Sequence: 
    // ["addRider","addDriver","addRider","matchDriverWithRider","addDriver","cancelRider",
    // "matchDriverWithRider","matchDriverWithRider"]

    system.addRider(3);
    system.addDriver(2);
    system.addRider(1);
    auto match1 = system.matchDriverWithRider();
    cout << "Matched Driver: " << match1[0] << ", Rider: " << match1[1] << endl;
    system.addDriver(5);
    system.cancelRider(3);
    auto match2 = system.matchDriverWithRider();
    cout << "Matched Driver: " << match2[0] << ", Rider: " << match2[1] << endl;
    auto match3 = system.matchDriverWithRider();
    cout << "Matched Driver: " << match3[0] << ", Rider: " << match3[1] << endl;
    return 0;
}
