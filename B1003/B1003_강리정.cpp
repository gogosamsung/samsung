#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int cnt[41][2] = { 0 };

int main() {
	int T;
	scanf("%d", &T);

	cnt[0][0] = 1;
	cnt[0][1] = 0;
	cnt[1][0] = 0;
	cnt[1][1] = 1;

	for (int i = 2; i <= 40; i++) {
		cnt[i][0] = cnt[i - 1][0] + cnt[i - 2][0];
		cnt[i][1] = cnt[i - 1][1] + cnt[i - 2][1];
	}

	for (int tc = 1; tc <= T; tc++) {
		int num;
		scanf("%d", &num);

		printf("%d %d\n", cnt[num][0], cnt[num][1]);
	}

	return 0;
}
