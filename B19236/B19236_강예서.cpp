#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

class info {
public:
	int x, y, d;
	info(){}
	info(int x, int y, int d) { this->x = x; this->y = y; this->d = d; }
};
int res;
int dx[9] = { 0,-1,-1,0,1,1,1,0,-1 }, dy[9] = { 0,0,-1,-1,-1,0,1,1,1 };

void goShark(int tmpArr[][4], int tmpShark[3], vector <info> tmpFish, int val);

void goFish(int tmpArr[][4], int tmpShark[3], vector <info> tmpFish, int val) {
	int arr[4][4], shark[3];
	vector<info> fish;
	copy(&tmpArr[0][0], &tmpArr[0][0] + 4 * 4, &arr[0][0]);
	copy(&tmpShark[0], &tmpShark[0] + 3, &shark[0]);
	for (int i = 0; i < 17; i++) fish.push_back(tmpFish[i]);
	res = max(res, val);
	for (int f = 1; f <= 16; f++) {
		if (fish[f].x == -1) continue;
		int d = fish[f].d, fromx = fish[f].x, fromy = fish[f].y;
		for (int i = 0; i < 8; i++) {
			int tox = fromx + dx[d], toy = fromy + dy[d];
			if (tox < 0 || tox >= 4 || toy < 0 || toy >= 4 || (tox == shark[0] && toy == shark[1])) {
				d = (d + 1) % 9;
				if (d == 0) d = 1;
				continue;
			}
			int tonum = arr[tox][toy];
			fish[f] = { tox,toy,d };
			fish[tonum].x = fromx, fish[tonum].y = fromy;
			arr[fromx][fromy] = tonum;
			arr[tox][toy] = f;
			break;
		}
	}
	goShark(arr, shark, fish, val);
}

void goShark(int tmpArr[][4], int tmpShark[3], vector <info> tmpFish, int val) {
	int arr[4][4], shark[3];
	vector<info> fish;
	copy(&tmpArr[0][0], &tmpArr[0][0] + 4 * 4, &arr[0][0]);
	copy(&tmpShark[0], &tmpShark[0] + 3, &shark[0]);
	for (int i = 0; i < 17; i++) fish.push_back(tmpFish[i]);
	int x = shark[0], y = shark[1], d = shark[2];
	while (1) {
		x += dx[d], y += dy[d];
		if (x < 0 || x >= 4 || y < 0 || y >= 4) break;
		if (!arr[x][y]) continue;
		int sx = shark[0], sy = shark[1], fnum = arr[x][y], fd = fish[fnum].d;
		fish[fnum] = { -1,-1,-1 }, arr[x][y] = 0;
		shark[0] = x, shark[1] = y, shark[2] = fd;
		goFish(arr, shark, fish, val + fnum);
		shark[0] = sx, shark[1] = sy, shark[2] = d;
		fish[fnum] = { x,y,fd }, arr[x][y] = fnum;
	}
}

int main() {
	int val;
	int arr[4][4] = { 0 }, shark[3] = { 0 };
	vector <info> fish;
	for (int i = 0; i < 17; i++) fish.push_back({ -1,-1,-1 });
	for (int i = 0; i < 4; i++) { // ют╥б
		for (int j = 0; j < 4; j++) {
			int a, b;
			cin >> a >> b;
			arr[i][j] = a;
			fish[a] = { i,j,b };
		}
	}
	val = arr[0][0];
	shark[2] = fish[val].d;
	fish[val] = { -1,-1,-1 };
	arr[0][0] = 0;
	goFish(arr, shark, fish, val);
	cout << res;
	return 0;
}
