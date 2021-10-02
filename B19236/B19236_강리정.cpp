#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int map[4][4];
int fish[17][3];
int shark[3] = { 0, 0, 0 };
int ans = 0;

int dx[8] = { 0, -1, -1, -1, 0, 1, 1, 1 };
int dy[8] = { -1, -1, 0, 1, 1, 1, 0, -1 };


void move() {
	// 1번 물고기부터 움직인다
	for (int n = 1; n <= 16; n++) {
		// 먹힌 물고기는 패스
		if (fish[n][0] == -1) continue;

		int x = fish[n][0];
		int y = fish[n][1];

		// 갈 수 있을 때까지 회전
		int cnt = 0;
		while (cnt < 8) {
			int newX = x + dx[fish[n][2] - 1];
			int newY = y + dy[fish[n][2] - 1];
			
			// 경계를 넘나요?
			if (newX >= 0 && newX < 4 && newY >= 0 && newY < 4) {
				// 상어가 있나요
				if (!((shark[0] == newY) && (shark[1] == newX))) {
					// 교체
					int temp = map[newY][newX];
					map[newY][newX] = n;
					map[y][x] = temp;

					fish[n][0] = newX;
					fish[n][1] = newY;
					fish[temp][0] = x;
					fish[temp][1] = y;

					break;
				}
			}
			// 다음 방향이 가능할 때까지 회전
			if (fish[n][2] == 8) fish[n][2] = 0;
			else fish[n][2]++;
			cnt++;
		}
	}
}

int eat(int sum) {
	// 상어가 물고기를 먹는다
	int num = map[shark[1]][shark[0]];
	sum += num;
	shark[2] = fish[num][2];
	
	// 물고기는 사라진다
	map[shark[1]][shark[0]] = 0;
	fish[num][0] = -1;

	// 물고기 대이동
	move();

	// 복사본 저장
	int old_shark[3];
	old_shark[2] = shark[2];
	int old_map[4][4];
	int old_fish[17][3];
	for (int y = 0; y < 4; y++) {
		for (int x = 0; x < 4; x++) {
			int n = map[y][x];
			old_map[y][x] = map[y][x];
			old_fish[n][0] = fish[n][0];
			old_fish[n][1] = fish[n][1];
			old_fish[n][2] = fish[n][2];
		}
	}
	
	// 상어가 이동한다 - 경우의 수 / 재귀
	int newX = shark[0] + dx[shark[2] - 1];
	int newY = shark[1] + dy[shark[2] - 1];
	while (newX >= 0 && newX < 4 && newY >= 0 && newY < 4) {
		// 물고기가 있어야만 이동 가능
		if (map[newY][newX] != 0) {
			shark[0] = newX;
			shark[1] = newY;
			old_shark[0] = shark[0];
			old_shark[1] = shark[1];
			eat(sum);
			// map 복원
			shark[0] = old_shark[0];
			shark[1] = old_shark[1];
			shark[2] = old_shark[2];
			for (int y = 0; y < 4; y++) {
				for (int x = 0; x < 4; x++) {
					int n = old_map[y][x];
					map[y][x] = old_map[y][x];
					fish[n][0] = old_fish[n][0];
					fish[n][1] = old_fish[n][1];
					fish[n][2] = old_fish[n][2];
				}
			}
		}
		newX += dx[shark[2] - 1];
		newY += dy[shark[2] - 1];
	}

	if (sum > ans) ans = sum;

	return sum;
}

int main(){
	// input
	int num, dir;
	for (int y = 0; y < 4; y++) {
		for (int x = 0; x < 4; x++) {
			scanf("%d %d", &num, &dir);
			map[y][x] = num;
			fish[num][0] = x;
			fish[num][1] = y;
			fish[num][2] = dir;
		}
	}

	eat(0);
	
	printf("%d", ans);

	return 0;
}
