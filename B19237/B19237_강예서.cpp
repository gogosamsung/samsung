#include <iostream>
#include <vector>
#include <queue>
using namespace std;
class arrInfo {
public:
	int num, smell, time; // 칸에 있는 상어 번호(없으면 0), 향기 번호(없으면 0), 향기 남은 시간
	arrInfo(){}
	arrInfo(int num, int smell, int time) { this->num = num; this->smell = smell; this->time = time; }
};
class sharkInfo {
public:
	int x, y, num, dir; // x, y, 방향
	sharkInfo() {}
	sharkInfo(int x, int y, int num, int dir) { this->x = x; this->y = y; this->num = num; this->dir = dir; }
};
int N, M, K, input, res = -1;
int sharkPrior[450][5][4];
int dx[5] = { 0,-1,1,0,0 }, dy[5] = { 0,0,0,-1,1 }; // 상하좌우
arrInfo arr[21][21];
vector<sharkInfo> shark;
queue<sharkInfo> moved;
int main() {
	// 입력
	cin >> N >> M >> K;
	for (int i = 0; i <= M; i++) shark.push_back({ -1,-1,-1,-1 });
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			cin >> input;
			arr[i][j] = { input,input,K }; // 처음에 자기 위치에 냄새 뿌림
			if (input) shark[input] = { i,j,input,-1 };
		}
	}
	for (int i = 1; i <= M; i++) {
		cin >> input;
		shark[i].dir = input;
	}
	for (int i = 1; i <= M; i++) for (int j = 1; j <= 4; j++) for (int k = 0; k < 4; k++) cin >> sharkPrior[i][j][k];

	for (int t = 1; t <= 1000; t++) {
		// 상어 이동할 곳 탐색
		for (int i = 1; i <= M; i++) {
			if (shark[i].x == -1) continue;
			bool chk = false;
			for (int k = 0; k < 4; k++) { // 아무 냄새도 없는 칸으로 이동
				int d = sharkPrior[i][shark[i].dir][k];
				int x = shark[i].x + dx[d], y = shark[i].y + dy[d];
				if (x < 0 || x >= N || y < 0 || y >= N || arr[x][y].smell) continue;
				moved.push({ x,y,i,d }), chk = true;
				arr[shark[i].x][shark[i].y].num = 0;
				break;
			}
			if (chk) continue;
			for (int k = 0; k < 4; k++) { // 자기 냄새가 있는 칸으로 이동
				int d = sharkPrior[i][shark[i].dir][k];
				int x = shark[i].x + dx[d], y = shark[i].y + dy[d];
				if (x < 0 || x >= N || y < 0 || y >= N || arr[x][y].smell != i) continue;
				moved.push({ x,y,i,d });
				arr[shark[i].x][shark[i].y].num = 0;
				break;
			}
		}
		// 상어 이동
		while (!moved.empty()) {
			int x = moved.front().x, y = moved.front().y, num = moved.front().num;
			if ((arr[x][y].num && arr[x][y].num > num) || !arr[x][y].num) { // 작은 번호의 상어만 살아남을 수 있음
				shark[arr[x][y].num] = { -1,-1,-1,-1 };
				shark[num] = { x,y,num,moved.front().dir };
				arr[x][y] = { num,num,K };
			}
			else shark[num] = { -1,-1,-1,-1 };
			moved.pop();
		}
		// 냄새 갱신
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (arr[i][j].num) continue;
				if (--arr[i][j].time == 0) arr[i][j].smell = 0;
			}
		}
		// 남은 상어 카운트
		int cnt = 0;
		for (int i = 1; i <= M; i++) if (shark[i].x != -1) cnt++;
		if (cnt == 1) { // 한 마리(1번 상어)만 남은 경우 종료
			res = t;
			break;
		}
	}
	cout << res;
	return 0;
}
