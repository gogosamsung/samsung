#include <iostream>
#include <queue>
using namespace std;
int N, K, L, a, b;
int board[101][101];
int dx[4] = { -1,0,1,0 }, dy[4] = { 0,1,0,-1 }; // »ó¿ìÇÏÁÂ
char c;
char times[10001];
queue<pair<int, int> > snake;

int main() {
	cin >> N >> K;
	for (int i = 0; i < K; i++) {
		cin >> a >> b;
		board[a - 1][b - 1] = 1;
	}
	cin >> L;
	for (int i = 0; i < L; i++) {
		cin >> a >> c;
		times[a] = c;
	}
	int t = 0, d = 1, X = 0, Y = 0;
	snake.push({ 0,0 });
	while (1) {
		int x = X + dx[d], y = Y + dy[d];
		t++, X = x, Y = y;
		snake.push({ x,y });
		if (x < 0 || x >= N || y < 0 || y >= N || board[x][y] == -1) break;
		else if (board[x][y] == 0) {
			board[snake.front().first][snake.front().second] = 0;
			snake.pop();
		}
		board[x][y] = -1;
		if (times[t] == 'D') d = (d + 1) % 4;
		else if (times[t] == 'L') d = (d + 3) % 4;
	}
	cout << t;

	return 0;
}