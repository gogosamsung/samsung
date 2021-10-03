#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int N, M;
int map[500][500];
int ans = 0;
int sum;

// 1. ****
void tet1() {
	for (int i = 0; i <= N-1; i++) {
		sum = map[i][0] + map[i][1] + map[i][2] + map[i][3];
		if (sum > ans) ans = sum;
		for (int j = 0; j < M - 4; j++) {
			sum -= map[i][j];
			sum += map[i][j + 4];
			if (sum > ans) ans = sum;
		}
	}

	for (int j = 0; j <= M - 1; j++) {
		sum = map[0][j] + map[1][j] + map[2][j] + map[3][j];
		if (sum > ans) ans = sum;
		for (int i = 0; i < N - 4; i++) {
			sum -= map[i][j];
			sum += map[i + 4][j];
			if (sum > ans) ans = sum;
		}
	}
}

// 2. **
//    **
void tet2() {
	for (int i = 0; i <= N - 2; i++) {
		sum = map[i][0] + map[i][1] + map[i + 1][0] + map[i + 1][1];
		for (int j = 0; j < M - 2; j++) {
			sum = sum - map[i][j] - map[i + 1][j] + map[i][j + 2] + map[i + 1][j + 2];
			if (sum > ans) ans = sum;
		}
	}
}

// 3. *
//    *
//    **
void tet3() {
	for (int i = 0; i <= N - 2; i++) {
		for (int j = 0; j <= M - 3; j++) {
			sum = map[i][j] + map[i][j + 1] + map[i][j + 2] + map[i + 1][j + 2];
			if (sum > ans) ans = sum;
			sum = sum - map[i + 1][j + 2] + map[i + 1][j];
			if (sum > ans) ans = sum;
			sum = map[i+1][j] + map[i+1][j + 1] + map[i+1][j + 2] + map[i][j + 2];
			if (sum > ans) ans = sum;
			sum = sum - map[i][j + 2] + map[i][j];
			if (sum > ans) ans = sum;
		}
	}
	for (int i = 0; i <= N - 3; i++) {
		for (int j = 0; j <= M - 2; j++) {
			sum = map[i][j] + map[i + 1][j] + map[i + 2][j] + map[i][j + 1];
			if (sum > ans) ans = sum;
			sum = sum - map[i][j + 1] + map[i + 2][j + 1];
			if (sum > ans) ans = sum;
			sum = map[i][j+1] + map[i + 1][j+1] + map[i + 2][j+1] + map[i][j];
			if (sum > ans) ans = sum;
			sum = sum - map[i][j] + map[i + 2][j];
			if (sum > ans) ans = sum;
		}
	}

}

// 4. *
//    **
//     *
void tet4() {
	for (int i = 0; i <= N - 3; i++) {
		for (int j = 0; j <= M - 2; j++) {
			sum = map[i][j] + map[i + 1][j] + map[i + 1][j + 1] + map[i + 2][j + 1];
			if (sum > ans) ans = sum;
			sum = sum - map[i][j] + map[i][j + 1] + map[i + 2][j] - map[i + 2][j + 1];
			if (sum > ans) ans = sum;
		}
	}
	for (int i = 0; i <= N - 2; i++) {
		for (int j = 0; j <= M - 3; j++) {
			sum = map[i][j] + map[i][j + 1] + map[i + 1][j + 1] + map[i + 1][j + 2];
			if (sum > ans) ans = sum;
			sum = sum - map[i][j] + map[i + 1][j] + map[i][j + 2] - map[i + 1][j + 2];
			if (sum > ans) ans = sum;
		}
	}
}

// 5. ***
//     *
void tet5() {
	for (int i = 0; i <= N - 2; i++) {
		for (int j = 0; j <= M - 3; j++) {
			sum = map[i][j] + map[i][j + 1] + map[i][j + 2] + map[i + 1][j + 1];
			if (sum > ans) ans = sum;
			sum = map[i][j + 1] + map[i + 1][j] + map[i + 1][j + 1] + map[i + 1][j + 2];
			if (sum > ans) ans = sum;
		}
	}
	for (int i = 0; i <= N - 3; i++) {
		for (int j = 0; j <= M - 2; j++) {
			sum = map[i][j] + map[i + 1][j] + map[i + 2][j] + map[i + 1][j + 1];
			if (sum > ans) ans = sum;
			sum = map[i][j + 1] + map[i + 1][j + 1] + map[i + 2][j + 1] + map[i + 1][j];
			if (sum > ans) ans = sum;
		}
	}
}

int main() {
	// Input
	scanf("%d %d", &N, &M);
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			scanf("%d", &map[i][j]);
		}
	}

	tet1();
	tet2();
	tet3();
	tet4();
	tet5();

	printf("%d", ans);

	return 0;
}
