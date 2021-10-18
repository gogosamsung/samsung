#include <iostream>
#include <vector>
#include <queue>
using namespace std;
class arrInfo {
public:
	int num, smell, time; // ĭ�� �ִ� ��� ��ȣ(������ 0), ��� ��ȣ(������ 0), ��� ���� �ð�
	arrInfo(){}
	arrInfo(int num, int smell, int time) { this->num = num; this->smell = smell; this->time = time; }
};
class sharkInfo {
public:
	int x, y, num, dir; // x, y, ����
	sharkInfo() {}
	sharkInfo(int x, int y, int num, int dir) { this->x = x; this->y = y; this->num = num; this->dir = dir; }
};
int N, M, K, input, res = -1;
int sharkPrior[450][5][4];
int dx[5] = { 0,-1,1,0,0 }, dy[5] = { 0,0,0,-1,1 }; // �����¿�
arrInfo arr[21][21];
vector<sharkInfo> shark;
queue<sharkInfo> moved;
int main() {
	// �Է�
	cin >> N >> M >> K;
	for (int i = 0; i <= M; i++) shark.push_back({ -1,-1,-1,-1 });
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			cin >> input;
			arr[i][j] = { input,input,K }; // ó���� �ڱ� ��ġ�� ���� �Ѹ�
			if (input) shark[input] = { i,j,input,-1 };
		}
	}
	for (int i = 1; i <= M; i++) {
		cin >> input;
		shark[i].dir = input;
	}
	for (int i = 1; i <= M; i++) for (int j = 1; j <= 4; j++) for (int k = 0; k < 4; k++) cin >> sharkPrior[i][j][k];

	for (int t = 1; t <= 1000; t++) {
		// ��� �̵��� �� Ž��
		for (int i = 1; i <= M; i++) {
			if (shark[i].x == -1) continue;
			bool chk = false;
			for (int k = 0; k < 4; k++) { // �ƹ� ������ ���� ĭ���� �̵�
				int d = sharkPrior[i][shark[i].dir][k];
				int x = shark[i].x + dx[d], y = shark[i].y + dy[d];
				if (x < 0 || x >= N || y < 0 || y >= N || arr[x][y].smell) continue;
				moved.push({ x,y,i,d }), chk = true;
				arr[shark[i].x][shark[i].y].num = 0;
				break;
			}
			if (chk) continue;
			for (int k = 0; k < 4; k++) { // �ڱ� ������ �ִ� ĭ���� �̵�
				int d = sharkPrior[i][shark[i].dir][k];
				int x = shark[i].x + dx[d], y = shark[i].y + dy[d];
				if (x < 0 || x >= N || y < 0 || y >= N || arr[x][y].smell != i) continue;
				moved.push({ x,y,i,d });
				arr[shark[i].x][shark[i].y].num = 0;
				break;
			}
		}
		// ��� �̵�
		while (!moved.empty()) {
			int x = moved.front().x, y = moved.front().y, num = moved.front().num;
			if ((arr[x][y].num && arr[x][y].num > num) || !arr[x][y].num) { // ���� ��ȣ�� �� ��Ƴ��� �� ����
				shark[arr[x][y].num] = { -1,-1,-1,-1 };
				shark[num] = { x,y,num,moved.front().dir };
				arr[x][y] = { num,num,K };
			}
			else shark[num] = { -1,-1,-1,-1 };
			moved.pop();
		}
		// ���� ����
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (arr[i][j].num) continue;
				if (--arr[i][j].time == 0) arr[i][j].smell = 0;
			}
		}
		// ���� ��� ī��Ʈ
		int cnt = 0;
		for (int i = 1; i <= M; i++) if (shark[i].x != -1) cnt++;
		if (cnt == 1) { // �� ����(1�� ���)�� ���� ��� ����
			res = t;
			break;
		}
	}
	cout << res;
	return 0;
}
