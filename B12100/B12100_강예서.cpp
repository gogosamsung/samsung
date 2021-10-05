#include <iostream>
#include <vector>
#include <algorithm>
#include <cstring>
using namespace std;
int N, res;
int arr[21][21];
int dx[4] = { -1,1,0,0 }, dy[4] = { 0,0,-1,1 };
vector<int> order;

void move(int a[], int d) {
	if (d % 2) {
		bool chk[21] = { false };
		for (int i = N - 1; i >= 0; i--) {
			if (!a[i]) continue;
			int x = i;
			while (1) {
				if (x + 1 >= N) break;
				if (a[x + 1] == 0) a[x + 1] = a[x], a[x++] = 0;
				else {
					if (a[x + 1] == a[x] && !chk[x + 1]) {
						a[x + 1] *= 2, a[x++] = 0;
						chk[x] = true;
					}
					break;
				}
			}
		}
	}
	else {
		bool chk[21] = { false };
		for (int i = 0; i < N; i++) {
			if (!a[i]) continue;
			int x = i;
			while (1) {
				if (x - 1 < 0) break;
				if (a[x - 1] == 0) a[x - 1] = a[x], a[x--] = 0;
				else {
					if (a[x - 1] == a[x] && !chk[x - 1]) {
						a[x - 1] *= 2, a[x--] = 0;
						chk[x] = true;
					}
					break;
				}
			}
		}
	}
}

void game() {
	int tmp[21][21] = { 0 };
	for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) tmp[i][j] = arr[i][j];
	for (int i = 0; i < order.size(); i++) {
		for (int j = 0; j < N; j++) {
			if (order[i] < 2) {
				int col[21];
				for (int k = 0; k < N; k++) col[k] = tmp[k][j];
				move(col, order[i]);
				for (int k = 0; k < N; k++) tmp[k][j] = col[k];
			}
			else move(tmp[j], order[i]);
		}
	}
	for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) res = max(res, tmp[i][j]);
}

void permutation(int r) {
	if (r == 5) {
		game();
		return;
	}
	for (int i = 0; i < 4; i++) {
		order.push_back(i);
		permutation(r + 1);
		order.pop_back();
	}
}

int main() {
	cin >> N;
	for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) cin >> arr[i][j];
	permutation(0);
	cout << res;
	return 0;
}