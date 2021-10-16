#include <iostream>
#include <algorithm>
using namespace std;
int N, T, X, Y, score, res;
int green[6][4], blue[4][6];

void dropT1() {
	int idx = 5;
	for (int i = 0; i < 6; i++) {
		if (green[i][Y]) {
			idx = i - 1;
			break;
		}
	}
	green[idx][Y] = 1;
	idx = 5;
	for (int i = 0; i < 6; i++) {
		if (blue[X][i]) {
			idx = i - 1;
			break;
		}
	}
	blue[X][idx] = 1;
}

void dropT2() {
	int idx = 5, idx2 = 5;
	for (int i = 0; i < 6; i++) {
		if (green[i][Y]) {
			idx = i - 1;
			break;
		}
	}
	for (int i = 0; i < 6; i++) {
		if (green[i][Y + 1]) {
			idx2 = i - 1;
			break;
		}
	}
	idx = min(idx, idx2);
	green[idx][Y] = green[idx][Y + 1] = 1;
	idx = 5;
	for (int i = 0; i < 6; i++) {
		if (blue[X][i]) {
			idx = i - 1;
			break;
		}
	}
	blue[X][idx - 1] = blue[X][idx] = 1;
}

void dropT3() {
	int idx = 5, idx2 = 5;
	for (int i = 0; i < 6; i++) {
		if (green[i][Y]) {
			idx = i - 1;
			break;
		}
	}
	green[idx - 1][Y] = green[idx][Y] = 1;
	idx = 5;
	for (int i = 0; i < 6; i++) {
		if (blue[X][i]) {
			idx = i - 1;
			break;
		}
	}
	for (int i = 0; i < 6; i++) {
		if (blue[X + 1][i]) {
			idx2 = i - 1;
			break;
		}
	}
	idx = min(idx, idx2);
	blue[X][idx] = blue[X + 1][idx] = 1;
}

void findFull() {
	bool emptyRow[6] = { 0 };
	for (int i = 0; i < 6; i++) { // 초록 판 꽉찬 행 다 비우기
		int cnt = 0;
		for (int j = 0; j < 4; j++) if (green[i][j]) cnt++;
		if (cnt == 4) {
			for (int j = 0; j < 4; j++) green[i][j] = 0;
			emptyRow[i] = true, score++;
		}
	}
	int idx = 5;
	for (int i = 5; i >= 0; i--) { // 빈 행 메꾸기
		if (emptyRow[i]) continue;
		for (int j = 0; j < 4; j++) green[idx][j] = green[i][j];
		idx--;
	}
	while (idx >= 0) {
		for (int i = 0; i < 4; i++) green[idx][i] = 0;
		idx--;
	}

	bool emptyCol[6] = { 0 };
	for (int i = 0; i < 6; i++) { // 파란 판 꽉찬 열 다 비우기
		int cnt = 0;
		for (int j = 0; j < 4; j++) if (blue[j][i]) cnt++;
		if (cnt == 4) {
			for (int j = 0; j < 4; j++) blue[j][i] = 0;
			emptyCol[i] = true, score++;
		}
	}
	idx = 5;
	for (int i = 5; i >= 0; i--) { // 빈 열 메꾸기
		if (emptyCol[i]) continue;
		for (int j = 0; j < 4; j++) blue[j][idx] = blue[j][i];
		idx--;
	}
	while (idx >= 0) {
		for (int i = 0; i < 4; i++) blue[i][idx] = 0;
		idx--;
	}
}

void destroyBlocks() {
	int cnt = 0;
	for (int i = 0; i < 2; i++) { // 초록 판 연한 부분 행 개수 카운트
		for (int j = 0; j < 4; j++) {
			if (green[i][j]) {
				cnt++;
				break;
			}
		}
	}
	for (int i = 5; i > 5 - cnt; i--) for (int j = 0; j < 4; j++) green[i][j] = 0;
	int idx = 5;
	for (int i = 5 - cnt; i >= 0; i--) { // 빈 행 메꾸기
		for (int j = 0; j < 4; j++) green[idx][j] = green[i][j];
		idx--;
	}
	while (idx >= 0) {
		for (int i = 0; i < 4; i++) green[idx][i] = 0;
		idx--;
	}

	cnt = 0;
	for (int i = 0; i < 2; i++) { // 파란 판 연한 부분 열 개수 카운트
		for (int j = 0; j < 4; j++) {
			if (blue[j][i]) {
				cnt++;
				break;
			}
		}
	}
	for (int i = 5; i > 5 - cnt; i--) for (int j = 0; j < 4; j++) blue[j][i] = 0;
	idx = 5;
	for (int i = 5 - cnt; i >= 0; i--) { // 빈 열 메꾸기
		for (int j = 0; j < 4; j++) blue[j][idx] = blue[j][i];
		idx--;
	}
	while (idx >= 0) {
		for (int i = 0; i < 4; i++) blue[i][idx] = 0;
		idx--;
	}
}

int main() {
	cin >> N;
	for (int n = 0; n < N; n++) {
		cin >> T >> X >> Y;
		if (T == 1) dropT1();
		else if (T == 2) dropT2();
		else dropT3();
		findFull();
		destroyBlocks();
	}
	for (int i = 0; i < 6; i++) {
		for (int j = 0; j < 4; j++) {
			if (green[i][j]) res++;
			if (blue[j][i]) res++;
		}
	}
	cout << score << "\n" << res;
	return 0;
}
