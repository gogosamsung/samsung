#include <iostream>
#include <algorithm>
using namespace std;
int N, M, res;
int arr[501][501];
int dx[4] = { -1,1,0,0 }, dy[4] = { 0,0,-1,1 };
int dx2[4][4] = {
	{0,0,0,1},
	{0,1,2,1},
	{0,1,2,1},
	{1,1,1,0}
};
int dy2[4][4] = {
	{0,1,2,1},
	{0,0,0,1},
	{0,0,0,-1},
	{0,1,2,1}
};
bool visited[501][501];

void dfs(int X, int Y, int depth, int val) {
	if (depth == 4) {
		res = max(res, val);
		return;
	}
	for (int i = 0; i < 4; i++) {
		int x = X + dx[i], y = Y + dy[i];
		if (x < 0 || x >= N || y < 0 || y >= M || visited[x][y]) continue;
		visited[x][y] = true;
		dfs(x, y, depth + 1, val + arr[x][y]);
		visited[x][y] = false;
	}
}

void other(int X, int Y) { // ぬたっで
	for (int i = 0; i < 4; i++) {
		int val = 0;
		for (int j = 0; j < 4; j++) {
			int x = X + dx2[i][j], y = Y + dy2[i][j];
			if (x < 0 || x >= N || y < 0 || y >= M) break;
			val += arr[x][y];
		}
		res = max(res, val);
	}
}

int main() {
	cin >> N >> M;
	for (int i = 0; i < N; i++) for (int j = 0; j < M; j++) cin >> arr[i][j];
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			visited[i][j] = true;
			dfs(i, j, 1, arr[i][j]);
			other(i, j);
			visited[i][j] = false;
		}
	}
	cout << res;
	return 0;
}