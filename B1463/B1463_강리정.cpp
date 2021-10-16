#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int N;
int memo[1000000] = { 0 };

void gogo(int n, int cnt) {
	if (n == 1) {
		if (memo[1] == 0) {
			memo[1] = cnt;
		}
		else if (memo[1] > cnt) {
			memo[1] = cnt;
		}
		return;
	}
	
	if (memo[n]!=0 && memo[n] <= cnt)
		return;

	memo[n] = cnt;

	if (n % 3 == 0) {
		gogo(n / 3, cnt + 1);
	}
	if (n % 2 == 0) {
		gogo(n / 2, cnt + 1);
	}
	gogo(n - 1, cnt + 1);

}

int main() {
	scanf("%d", &N);

	gogo(N, 0);

	printf("%d", memo[1]);

	return 0;
}
