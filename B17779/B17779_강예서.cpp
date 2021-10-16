#include <iostream>
#include <algorithm>
#include <climits>
using namespace std;
int N, res = INT_MAX, tot;
int arr[21][21];
int dx[4] = { -1,1,0,0 }, dy[4] = { 0,0,-1,1 };

void divide(int x, int y, int d1, int d2) {
	int area[5] = { 0 };

	// 1锅 备开
	int idx = y;
	for (int i = 1; i < x; i++) for (int j = 1; j <= y; j++) area[0] += arr[i][j];
	for (int i = x; i < x + d1; i++, idx--) for (int j = 1; j < idx; j++) area[0] += arr[i][j];

	// 2锅 备开
	idx = y;
	for (int i = 1; i < x; i++) for (int j = y + 1; j <= N; j++) area[1] += arr[i][j];
	for (int i = x; i <= x + d2; i++, idx++) for (int j = idx + 1; j <= N; j++) area[1] += arr[i][j];

	// 3锅 备开
	idx = x;
	for (int i = x + d1; i <= N; i++) for (int j = 0; j < y - d1; j++) area[2] += arr[i][j];
	for (int j = y - d1; j < y - d1 + d2; j++, idx++) for (int i = idx + d1 + 1; i <= N; i++) area[2] += arr[i][j];

	// 4锅 备开
	idx = x;
	for (int i = x + d2 + 1; i <= N; i++) for (int j = y + d2; j <= N; j++) area[3] += arr[i][j];
	for (int j = y - d1 + d2; j < y + d2; j++, idx--) for (int i = N; i > idx + d1 + d2; i--) area[3] += arr[i][j];

	// 5锅 备开
	area[4] = tot - area[3] - area[2] - area[1] - area[0];

	sort(area, area + 5);
	res = min(res, area[4] - area[0]);
}

int main() {
	cin >> N;
	for (int i = 1; i <= N; i++) for (int j = 1; j <= N; j++) cin >> arr[i][j], tot += arr[i][j];
	for (int i = 1; i <= N - 2; i++) {
		for (int j = 2; j <= N - 1; j++) {
			for (int d1 = 1; d1 < N - 1; d1++) {
				for (int d2 = 1; d2 < N - 1; d2++) {
					divide(i, j, d1, d2);
				}
			}
		}
	}
	cout << res;
	return 0;
}
