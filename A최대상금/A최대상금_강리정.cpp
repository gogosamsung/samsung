#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int card[6];
int N;
int len = 0, max = 0;
bool visited[1000000][11];

void change(int cnt) {
	int sum = 0;
	for (int i = 0; i < len; i++) {
		sum = sum * 10 + card[i];
	}
	
	// 탈출조건: 횟수 종료 시
	if (cnt >= N) {
		if (sum > max) max = sum;
		return;
	}

	// 이미 해봤으면 return
	if (visited[sum][cnt]) return;
	else visited[sum][cnt] = true;


	// 모든 경우에 대해 바꿔본다
	for (int i = 0; i < len-1; i++) {
		for (int j = i + 1; j < len; j++) {
			int temp = card[i];
			card[i] = card[j];
			card[j] = temp;
			change(cnt + 1);
			card[j] = card[i];
			card[i] = temp;
		}
	}
}

int main() {
	int T;
	scanf("%d\n", &T);

	for(int tc = 1; tc<= T; tc++){
		// input
		char temp = '0';
		while (1) {
			scanf("%c", &temp);
			if (temp == ' ') break;
			card[len++] = temp - '0';
		}
		scanf("%d\n", &N);

		// 완전 탐색
		change(0);

		// print
		printf("#%d %d\n", tc, max);

		// 초기화
		len = 0;
		max = 0;
	}

	return 0;
}
