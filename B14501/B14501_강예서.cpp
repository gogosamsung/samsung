#include <iostream>
#include <algorithm>
using namespace std;
int N, res;
int arr[2][1001];

void solve(int day, int val) {
	res = max(res, val);
	for (int i = day; i <= N; i++) {
		if (i + arr[0][i] > N + 1) continue;
		solve(i + arr[0][i], val + arr[1][i]);
	}
}

int main() {
	cin >> N;
	for (int i = 1; i <= N; i++) cin >> arr[0][i] >> arr[1][i];
	for (int i = 1; i <= N; i++) solve(i, 0);
	cout << res;
	return 0;
}