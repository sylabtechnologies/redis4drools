/*
vector timing: shows uneven performance and x2 size increase and self-copying

Time taken to insert element 0: 748 ns
Time taken to insert element 1: 1081 ns
Time taken to insert element 2: 128 ns
Time taken to insert element 3: 45 ns
Time taken to insert element 4: 145 ns
ime taken to insert element 15: 17 ns
Time taken to insert element 16: 201 ns
Time taken to insert element 17: 20 ns
Time taken to insert element 62: 19 ns
Time taken to insert element 63: 20 ns
Time taken to insert element 64: 232 ns
Time taken to insert element 65: 19 ns

Time taken to insert element 128: 248 ns
*/

#include <iostream>
#include <string>
#include <sstream>
#include <vector>
#include <algorithm>
#include <chrono>
using namespace std;

int main() {

    vector<int> container;

    chrono::nanoseconds elapsed_ns(0);

    for(int i = 0; i < 200; ++i) {
        auto startTime = chrono::high_resolution_clock::now();
        container.push_back(i);
        auto endTime = chrono::high_resolution_clock::now();
        elapsed_ns = chrono::duration_cast<chrono::nanoseconds>(endTime - startTime);

        cout << "Time taken to insert element " << i << ": " << elapsed_ns.count() << " ns" << endl;
    }

    return 0;
}