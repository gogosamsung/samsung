#include <iostream>
#include <string>
#include <algorithm>
using namespace std;
int T, N;
char cube[12][9];
char color[12] = { 'g','w','b','.','r','.','.','y','.','.','o','.' };

void paintSquare(int x, int y, int idx) {
	for (int i = x; i < x + 3; i++) for (int j = y; j < y + 3; j++) cube[i][j] = color[idx];
}

void initCube() {
	int idx = 0;
	for (int i = 0; i <= 9; i += 3) for (int j = 0; j <= 6; j += 3) paintSquare(i, j, idx++);
}

void rotateCurrentPlane(int x, int y) {
	int idx = 0;
	char tmp[12][9];
	for (int i = x, n = y + 2; i < x + 3; i++, n--) {
		for (int j = y, m = x; j < y + 3; j++, m++) {
			tmp[m][n] = cube[i][j];
		}
	}
	for (int i = x; i < x + 3; i++)for (int j = y; j < y + 3; j++) cube[i][j] = tmp[i][j];
}

void rotateSide(char tmp[]) {
	for (int t = 0; t < 3; t++) for (int i = 0; i < 11; i++) swap(tmp[i], tmp[11]);
}

void findSide(char plane) {
	int idx = 0;
	char tmp[12];
	if (plane == 'U') {
		for (int i = 2; i >= 0; i--) tmp[idx++] = cube[i][2];
		for (int i = 3; i < 6; i++) tmp[idx++] = cube[11][i];
		for (int i = 0; i < 3; i++) tmp[idx++] = cube[i][6];
		for (int i = 5; i >= 3; i--) tmp[idx++] = cube[3][i];
		rotateSide(tmp), idx = 0;
		for (int i = 2; i >= 0; i--) cube[i][2] = tmp[idx++];
		for (int i = 3; i < 6; i++) cube[11][i] = tmp[idx++];
		for (int i = 0; i < 3; i++) cube[i][6] = tmp[idx++];
		for (int i = 5; i >= 3; i--) cube[3][i] = tmp[idx++];
	}
	else if (plane == 'D') {
		for (int i = 0; i < 3; i++) tmp[idx++] = cube[i][0];
		for (int i = 3; i < 6; i++) tmp[idx++] = cube[5][i];
		for (int i = 2; i >= 0; i--) tmp[idx++] = cube[i][8];
		for (int i = 5; i >= 3; i--) tmp[idx++] = cube[9][i];
		rotateSide(tmp), idx = 0;
		for (int i = 0; i < 3; i++) cube[i][0] = tmp[idx++];
		for (int i = 3; i < 6; i++) cube[5][i] = tmp[idx++];
		for (int i = 2; i >= 0; i--) cube[i][8] = tmp[idx++];
		for (int i = 5; i >= 3; i--) cube[9][i] = tmp[idx++];
	}
	else if (plane == 'F') {
		for (int i = 0; i < 9; i++) tmp[idx++] = cube[2][i];
		for (int i = 5; i >= 3; i--) tmp[idx++] = cube[6][i];
		rotateSide(tmp), idx = 0;
		for (int i = 0; i < 9; i++) cube[2][i] = tmp[idx++];
		for (int i = 5; i >= 3; i--) cube[6][i] = tmp[idx++];
	}
	else if (plane == 'B') {
		for (int i = 8; i >= 0; i--) tmp[idx++] = cube[0][i];
		for (int i = 3; i < 6; i++) tmp[idx++] = cube[8][i];
		rotateSide(tmp), idx = 0;
		for (int i = 8; i >= 0; i--) cube[0][i] = tmp[idx++];
		for (int i = 3; i < 6; i++) cube[8][i] = tmp[idx++];
	}
	else if (plane == 'L') {
		for (int i = 0; i < 12; i++) tmp[idx++] = cube[i][3];
		rotateSide(tmp), idx = 0;
		for (int i = 0; i < 12; i++) cube[i][3] = tmp[idx++];
	}
	else if (plane == 'R') {
		for (int i = 11; i >= 0; i--) tmp[idx++] = cube[i][5];
		rotateSide(tmp), idx = 0;
		for (int i = 11; i >= 0; i--) cube[i][5] = tmp[idx++];
	}
}

int main() {
	cin >> T;
	for (int t = 0; t < T; t++) {
		cin >> N;
		initCube();
		for (int n = 0; n < N; n++) {
			string input;
			cin >> input;
			int x = 0, y = 0, cnt = (input[1] == '+' ? 1 : 3);
			if (input[0] == 'U') y = 3;
			else if (input[0] == 'D') x = 6, y = 3;
			else if (input[0] == 'F') x = 3, y = 3;
			else if (input[0] == 'B') x = 9, y = 3;
			else if (input[0] == 'R') y = 6;
			for (int i = 0; i < cnt; i++) { // 시계 방향이면 1번, 반시계 방향이면 3번 반복
				rotateCurrentPlane(x, y);
				findSide(input[0]);
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 3; j < 6; j++) cout << cube[i][j];
			cout << "\n";
		}
	}
	return 0;
}
