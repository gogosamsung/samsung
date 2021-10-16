#include <iostream>
#include <algorithm>
#include <queue>
#include <cstring>
using namespace std;
int N, M, T, X, D, K, res, chk;
int arr[51][51], order[51][3];
int dx[4] = { -1,1,0,0 }, dy[4] = { 0,0,-1,1 };
bool visited[51][51] = { 0 };
queue<pair<int, int> > q;

void clockwise(int x) {
	for (int j = 1; j < M; j++) swap(arr[x][j], arr[x][M]);
}

void counterClockwise(int x) {
	for (int j = M; j > 1; j--) swap(arr[x][j], arr[x][1]);
}

bool bfs() {
	bool chk = false;
	int val = arr[q.front().first][q.front().second];
	while (!q.empty()) {
		for (int k = 0; k < 4; k++) {
			int x = q.front().first + dx[k], y = q.front().second + dy[k];
			if (y < 1) y = M;
			else if(y > M) y = 1;
			if (x < 1 || x > N || !arr[x][y] || visited[x][y] || arr[x][y] != val) continue;
			visited[x][y] = true;
			q.push({ x,y });
			arr[x][y] = 0, chk = true;
		}
		q.pop();
	}
	return chk;
}

int main() {
	cin >> N >> M >> T;
	for (int i = 1; i <= N; i++) for (int j = 1; j <= M; j++) cin >> arr[i][j];
	for (int t = 0; t < T; t++) cin >> order[t][0] >> order[t][1] >> order[t][2];
	for (int t = 0; t < T; t++) {
		X = order[t][0], D = order[t][1], K = order[t][2];
		for (int x = X; x <= N; x += X) { // 회전
			for (int k = 0; k < K; k++) {
				D ? counterClockwise(x) : clockwise(x);
			}
		}
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				if (visited[i][j] || !arr[i][j]) continue;
				visited[i][j] = true;
				q.push({ i,j });
				if (bfs()) arr[i][j] = 0, chk = 1;
				else visited[i][j] = false;
			}
		}
		memset(visited, false, sizeof(visited));
		if (chk) { // 인접하면서 같은 수가 있었음
			chk = 0;
			continue;
		}
		float avg = 0, cnt = 0;
		for (int i = 1; i <= N; i++) for (int j = 1; j <= M; j++) if(arr[i][j]) avg += arr[i][j], cnt++;
		avg /= cnt;
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= M; j++) {
				if (arr[i][j]) {
					if ((float)arr[i][j] > avg) arr[i][j]--;
					else if ((float)arr[i][j] < avg) arr[i][j]++;
				}
			}
		}
	}
	for (int i = 1; i <= N; i++) for (int j = 1; j <= M; j++) res += arr[i][j];
	cout << res;
	return 0;
}
