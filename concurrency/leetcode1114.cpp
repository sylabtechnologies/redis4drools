// leetcode task 1114

std::mutex mtx;

class Foo {
    int order = 1;
    std::condition_variable condition;

public:
    Foo() {}

    void first(function<void()> printFirst) {
        waitForAndInc(1, printFirst);
    }

    void second(function<void()> printSecond) {
        waitForAndInc(2, printSecond);
    }

    void third(function<void()> printThird) {
        waitForAndInc(3, printThird);
    }

    void waitForAndInc(int actualCondition, function<void()> print) {
        std::unique_lock<std::mutex> lock(mtx);
        condition.wait(lock, [=] { return actualCondition == order; });

        order++;
        print();
        condition.notify_all();
    }
};
