#include <iostream>
#include <queue>
using namespace std;
int N, M, D, S, X, Y;
int arr[50][50], res[4];
int dx[5] = { 0,-1,1,0,0 }, dy[5] = { 0,0,0,-1,1 }, tx[4] = { 0,1,0,-1 }, ty[4] = { -1,0,1,0 };

void move() {
	queue<int> q;
	int d = 0, x = X, y = Y, chk = 0;
	for (int n = 1; n <= N; n++) {
		for (int m = 0; m < 2; m++) {
			for (int i = 0; i < n; i++) {
				x += tx[d], y += ty[d];
				if (x < 0 || x >= N || y < 0 || y >= N) {
					chk = 1;
					break;
				}
				if (!arr[x][y]) continue;
				q.push(arr[x][y]);
			}
			if (chk) break;
			d = (d + 1) % 4;
		}
		if (chk) break;
	}
	chk = d = 0, x = X, y = Y;
	for (int n = 1; n <= N; n++) {
		for (int m = 0; m < 2; m++) {
			for (int i = 0; i < n; i++) {
				x += tx[d], y += ty[d];
				if (x < 0 || x >= N || y < 0 || y >= N) {
					chk = 1;
					break;
				}
				if (q.empty()) arr[x][y] = 0;
				else {
					arr[x][y] = q.front();
					q.pop();
				}
			}
			if (chk) break;
			d = (d + 1) % 4;
		}
		if (chk) break;
	}
}

bool bomb() {
	queue<pair<pair<int,int>,int> > q;
	int d = 0, x = X, y = Y, chk = 0, finish = 0;
	for (int n = 1; n <= N; n++) {
		for (int m = 0; m < 2; m++) {
			for (int i = 0; i < n; i++) {
				x += tx[d], y += ty[d];
				if (x < 0 || x >= N || y < 0 || y >= N || !arr[x][y]) {
					chk = 1;
					break;
				}
				if (q.empty() || arr[x][y] == q.front().second) q.push({ {x, y}, arr[x][y] });
				else {
					int qsize = q.size();
					while (!q.empty()) {
						if (qsize >= 4) {
							arr[q.front().first.first][q.front().first.second] = 0;
							res[q.front().second]++, finish++;
						}
						q.pop();
					}
					q.push({ {x, y}, arr[x][y] });
				}
			}
			if (chk) break;
			d = (d + 1) % 4;
		}
		if (chk) break;
	}
	int qsize = q.size();
	while (!q.empty()) {
		if (qsize >= 4) {
			arr[q.front().first.first][q.front().first.second] = 0;
			res[q.front().second]++, finish++;
		}
		q.pop();
	}
	if (finish) return true;
	return false;
}

void change() {
	queue<pair<int, int> > group;
	int d = 0, x = X, y = Y, chk = 0, cnt = 0, num = -1;
	for (int n = 1; n <= N; n++) {
		for (int m = 0; m < 2; m++) {
			for (int i = 0; i < n; i++) {
				x += tx[d], y += ty[d];
				if (x < 0 || x >= N || y < 0 || y >= N) {
					chk = 1;
					break;
				}
				if (num == -1 || arr[x][y] == num) cnt++, num = arr[x][y];
				else {
					group.push({ num, cnt });
					num = arr[x][y], cnt = 1;
				}
			}
			if (chk) break;
			d = (d + 1) % 4;
		}
		if (chk) break;
	}
	if (num == -1 || arr[x][y] == num) cnt++;
	else {
		group.push({ num, cnt });
		num = arr[x][y], cnt = 1;
	}
	chk = d = 0, x = X, y = Y, cnt = 1, num = 0;
	for (int n = 1; n <= N; n++) {
		for (int m = 0; m < 2; m++) {
			for (int i = 0; i < n; i++) {
				x += tx[d], y += ty[d];
				if (x < 0 || x >= N || y < 0 || y >= N) {
					chk = 1;
					break;
				}
				if (cnt) {
					if (group.empty()) num = 0, cnt = 0;
					else {
						num = group.front().first;
						cnt = group.front().second;
						group.pop();
					}
					arr[x][y] = cnt, cnt = 0;
				}
				else {
					arr[x][y] = num;
					cnt = 1;
				}
			}
			if (chk) break;
			d = (d + 1) % 4;
		}
		if (chk) break;
	}
}

int main() {
	cin >> N >> M;
	for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) cin >> arr[i][j];
	X = N / 2, Y = N / 2;
	for (int m = 0; m < M; m++) {
		cin >> D >> S;
		// 1. ÆÄ±«
		for (int s = 1; s <= S; s++) {
			int x = X + dx[D] * s, y = Y + dy[D] * s;
			if (x < 0 || x >= N || y < 0 || y >= N) break;
			arr[x][y] = 0;
		}

		// 2. ÀÌµ¿
		move();

		while (bomb()) { // 3. Æø¹ß
			move(); // 4. ÀÌµ¿
		}

		// 5. º¯È­
		change();
	}
	cout << res[1] + res[2] * 2 + res[3] * 3;
	return 0;
}
