#include <iostream>
#include <cmath>
#include <algorithm>
#include <queue>
using namespace std;
int N, Q, M, res, big;
int arr[65][65];
int dx[4] = { -1,1,0,0, }, dy[4] = { 0,0,-1,1 };

int main() {
	cin >> N >> Q;
	N = pow(2, N);
	for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) cin >> arr[i][j];
	for (int qq = 0; qq < Q; qq++) {
		cin >> M;
		M = pow(2, M);
		// 회전
		for (int i = 0; i < N; i += M) {
			for (int j = 0; j < N; j += M) {
				int tmp[65][65] = { 0 };
				for (int m = i, a = j + M - 1; m < i + M; m++, a--) for (int n = j, b = i; n < j + M; n++, b++) tmp[b][a] = arr[m][n];
				for (int m = i; m < i + M; m++) for (int n = j; n < j + M; n++) arr[m][n] = tmp[m][n];
			}
		}

		// 얼음 조절
		queue<pair<int, int> > q;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (!arr[i][j]) continue;
				int cnt = 0;
				for (int k = 0; k < 4; k++) {
					int x = i + dx[k], y = j + dy[k];
					if (x < 0 || x >= N || y < 0 || y >= N || !arr[x][y]) cnt++;
				}
				if (cnt >= 2) q.push({ i,j });
			}
		}
		while (!q.empty()) {
			arr[q.front().first][q.front().second]--;
			q.pop();
		}
	}
	queue<pair<int, int> > q;
	bool visited[65][65] = { 0 };
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			res += arr[i][j];
			if (!arr[i][j] || visited[i][j]) continue;
			q.push({ i,j }), visited[i][j] = true;
			int cnt = 1;
			while (!q.empty()) {
				for (int k = 0; k < 4; k++) {
					int x = q.front().first + dx[k], y = q.front().second + dy[k];
					if (x < 0 || x >= N || y < 0 || y >= N || visited[x][y] || !arr[x][y]) continue;
					visited[x][y] = true;
					q.push({ x,y });
					cnt++;
				}
				q.pop();
			}
			big = max(big, cnt);
		}
	}

	cout << res << "\n" << big;
	return 0;
}
