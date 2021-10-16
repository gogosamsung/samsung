#include <iostream>
#include <cmath>
#include <vector>
#include <algorithm>
using namespace std;
int res;
int dice[10], map[4][40];
vector<int> order;
vector<pair<int, int> > horse;

int play() {
	int val = 0;
	bool finish[5] = { 0 };
	for (int i = 0; i < order.size(); i++) {
		if (finish[order[i]]) return -1; // 비정상적으로 끝나는 경우임
		int x = horse[order[i]].first, y = horse[order[i]].second + dice[i];
		if (x == 0) {
			if (y == 5) x = 3;
			else if (y == 10) x = 2;
			else if (y == 15) x = 1;
		}
		else if (x == 1 && y == 10) x = 2;
		if (map[x][y] == 0) finish[order[i]] = true;
		else {
			for (int k = 1; k <= 4; k++) { // 말이 겹치는지 알아보기
				if (k == order[i]) continue;
				int X = horse[k].first, Y = horse[k].second;
				if (map[x][y] == map[X][Y]) {
					if (map[x][y] == 16) {
						if (!(x == 3 && X == 3) && (x == 3 || X == 3)) continue;
						else return -1;
					}
					else if (map[x][y] == 22 || map[x][y] == 24) {
						if ((x == 2 && X == 0) || (x == 2 && X == 1) || (x == 0 && X == 2) || (x == 1 && X == 2)) continue;
						else return -1;
					}
					else if (map[x][y] == 26) {
						if ((x == 0 && X == 1 && Y == 18) || (X == 0 && x == 1 && y == 18)) continue;
						else return -1;
					}
					else if (map[x][y] == 30) {
						if (!(y == 15 && Y == 15) && (y == 15 || Y == 15)) continue;
						else return -1;
					}
					else return -1;
				}
			}
		}
		horse[order[i]] = { x,y };
		val += map[x][y];
	}
	return val;
}

void permutation(int r) {
	if (r == 10) {
		for (int i = 0; i <= 4; i++) horse.push_back({ 0,0 });
		res = max(res, play());
		horse.clear();
		return;
	}
	for (int i = 1; i <= 4; i++) {
		order.push_back(i);
		permutation(r + 1);
		order.pop_back();
	}
}

int main() {
	for (int i = 0; i < 10; i++) cin >> dice[i];
	for (int i = 1; i <= 20; i++) map[0][i] = i * 2;

	for (int i = 1; i <= 15; i++) map[1][i] = i * 2;
	map[1][16] = 28, map[1][17] = 27, map[1][18] = 26, map[1][19] = 25;
	for (int i = 20; i < 23; i++) map[1][i] = map[1][i - 1] + 5;

	for (int i = 1; i <= 12; i++) map[2][i] = i * 2;
	map[2][13] = 25;
	for (int i = 14; i < 17; i++) map[2][i] = map[2][i - 1] + 5;

	for (int i = 1; i <= 5; i++) map[3][i] = i * 2;
	map[3][6] = 13, map[3][7] = 16, map[3][8] = 19, map[3][9] = 25;
	for (int i = 10; i < 13; i++) map[3][i] = map[3][i - 1] + 5;

	permutation(0);
	cout << res;
	return 0;
}
