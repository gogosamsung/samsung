#include <iostream>
#include <cstdlib>
#include <queue>
using namespace std;
int N, L, R, val, res, cnt, teamNum;
int arr[51][51];
int dx[4] = { -1,1,0,0 }, dy[4] = { 0,0,-1,1 };
queue<pair<int, int> > q;

void bfs(int visited[][51], int teamVal[]) {
	cnt = 1;
	while (!q.empty()) {
		int X = q.front().first, Y = q.front().second;
		for (int i = 0; i < 4; i++) {
			int x = X + dx[i], y = Y + dy[i];
			if (x < 0 || x >= N || y < 0 || y >= N || visited[x][y] || abs(arr[X][Y] - arr[x][y]) < L || abs(arr[X][Y] - arr[x][y]) > R) continue;
			q.push({ x,y });
			visited[x][y] = teamNum;
			val += arr[x][y], cnt++;
		}
		q.pop();
	}
	if (cnt > 1) { // ���� ����
		val /= cnt;
		teamVal[teamNum++] = val;
	}
	val = 0;
}

int main() {
	cin >> N >> L >> R;
	for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) cin >> arr[i][j];
	while (1) {
		teamNum = 1;
		int visited[51][51] = { 0 }, teamVal[3000] = { 0 };
		for (int i = 0; i < N; i++) { // ���� ã��
			for (int j = 0; j < N; j++) {
				if (visited[i][j]) continue;
				q.push({ i,j });
				visited[i][j] = teamNum, val = arr[i][j];
				bfs(visited, teamVal);
				if (cnt == 1) visited[i][j] = 0;
			}
		}
		if (teamNum == 1) break; // �� �̻� ������ ������ ��
		res++;
		for (int i = 0; i < N; i++) { // �α� �̵�
			for (int j = 0; j < N; j++) {
				if (!visited[i][j]) continue;
				arr[i][j] = teamVal[visited[i][j]];
			}
		}
	}
	cout << res;
	return 0;
}
