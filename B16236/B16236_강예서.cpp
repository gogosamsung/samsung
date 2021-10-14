#include <iostream>
#include <queue>
#include <vector>
#include <algorithm>
using namespace std;

class info {
public:
	int x, y, dist;
	info() {}
	info(int x, int y, int dist) { this->x = x; this->y = y; this->dist = dist; }
};

int N, res, X, Y, babysize = 2, eatcnt = 0;
int arr[21][21];
int dx[4] = { -1,1,0,0 }, dy[4] = { 0,0,-1,1 };

bool compare(info a, info b) {
	if (a.dist == b.dist) {
		if (a.x == b.x) return a.y < b.y;
		return a.x < b.x;
	}
	return a.dist < b.dist;
}

int main() {
	cin >> N;
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			cin >> arr[i][j];
			if (arr[i][j] == 9) X = i, Y = j; // 아기 상어 위치
		}
	}
	while (1) {
		vector <info> picked;
		queue <info> moved;
		bool visited[21][21] = { 0 };
		moved.push({ X,Y,0 });
		visited[X][Y] = true;
		while (!moved.empty()) {
			if (picked.size() > 0 && moved.front().dist == picked.front().dist) break;
			for (int i = 0; i < 4; i++) {
				int x = moved.front().x + dx[i], y = moved.front().y + dy[i];
				if (x < 0 || x >= N || y < 0 || y >= N || visited[x][y] || arr[x][y] > babysize) continue;
				if (arr[x][y] && arr[x][y] < babysize) picked.push_back({ x,y,moved.front().dist + 1 });
				moved.push({ x,y,moved.front().dist + 1 });
				visited[x][y] = true;
			}
			moved.pop();
		}
		if (picked.size() == 0) break;
		sort(picked.begin(), picked.end(), compare);
		res += picked[0].dist;
		arr[X][Y] = 0;
		X = picked[0].x, Y = picked[0].y;
		arr[X][Y] = 9;
		eatcnt++;
		if (eatcnt == babysize) {
			babysize++;
			eatcnt = 0;
		}
	}
	cout << res;
	return 0;
}
