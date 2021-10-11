#include <iostream>
#include <algorithm>
#include <climits>
using namespace std;
int N, maxRes = INT_MIN, minRes = INT_MAX;
int num[101], op[12];
bool visited[12];

void permutation(int r, int val) {
	if (r == N - 1) {
		minRes = min(minRes, val);
		maxRes = max(maxRes, val);
		return;
	}
	for (int i = 0; i < N - 1; i++) {
		if (visited[i]) continue;
		visited[i] = true;
		if (op[i] == 0) permutation(r + 1, val + num[r + 1]);
		else if (op[i] == 1) permutation(r + 1, val - num[r + 1]);
		else if (op[i] == 2) permutation(r + 1, val * num[r + 1]);
		else permutation(r + 1, val / num[r + 1]);
		visited[i] = false;
	}
}

int main() {
	cin >> N;
	for (int i = 0; i < N; i++) cin >> num[i];
	int idx = 0;
	for (int i = 0; i < 4; i++) {
		int a;
		cin >> a;
		for (int j = 0; j < a; j++) op[idx++] = i;
	}
	permutation(0, num[0]);
	cout << maxRes << "\n" << minRes;
	return 0;
}