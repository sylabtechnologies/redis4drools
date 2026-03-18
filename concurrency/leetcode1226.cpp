// https://leetcode.com/problems/the-dining-philosophers/description/

class DiningPhilosophers {
private:
    // 5 forks for 5 philosophers
    std::mutex forks[5];
    // A global lock to ensure philosophers pick up both forks atomically
    std::mutex layout_lock;
    
    // stop source to manage the lifecycle of the dinner
    std::stop_source dinner_signal;

public:
    DiningPhilosophers() {}

    void wantsToEat(int philosopher,
                    function<void()> pickLeftFork,
                    function<void()> pickRightFork,
                    function<void()> eat,
                    function<void()> putLeftFork,
                    function<void()> putRightFork) {
        
        // Get a stop token from our source
        std::stop_token st = dinner_signal.get_token();
        
        // If a stop was already requested elsewhere, we exit immediately
        if (st.stop_requested()) return;

        int left = philosopher;
        int right = (philosopher + 1) % 5;

        // Use scoped_lock to acquire both forks (mutexes) without deadlock.
        {
            std::scoped_lock lock(forks[left], forks[right]);

            // Execute the provided functional actions
            pickLeftFork();
            pickRightFork();
            eat();
            putLeftFork();
            putRightFork();
        }
        
        // If this was the last meal the philosopher can signal everyone to stop.
        // dinner_signal.request_stop(); 
    }
};
