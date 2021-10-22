// 미완성
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

char cmd[100001];
int arr[100001];
int n;
int front = 0, end;
int flag = 0;
int R = 0;

int main() {
	int T;
	scanf("%d", &T);
	for (int tc = 1; tc <= T; tc++) {
		scanf("%s", cmd);
		scanf("%d", &n);
		char temp;
		scanf(" %c", &temp);
		for (int i = 0; i < n; i++) {
			scanf("%d%c", &arr[i], &temp);
		}
		end = n - 1;

		int cnt = 0;
		while (1) {
			temp = cmd[cnt];
			if (temp == '\0') break;
			if (temp == 'R') {
				R++;
			}
			else if (temp == 'D') {
				if (front > end) {
					flag = 1;
					break;
				}
				if (R % 2 == 0) {
					front++;
				}
				else {
					end--;
				}
			}
			cnt++;

		}

		// print: front~end
		if (flag == 1) {
			printf("error\n");
		}
		else if (n == 0) {
			printf("[]\n");
		}
		else if (front > end) {
			printf("[]\n");
		}
		else {
			printf("[");
			if (R % 2 == 0) {
				for (int i = front; i < end; i++) {
					printf("%d,", arr[i]);
				}
				if (n != 0)
					printf("%d", arr[end]);
			}
			else {
				for (int i = end; i > front; i--) {
					printf("%d,", arr[i]);
				}
				if(n != 0)
					printf("%d", arr[front]);
			}
			printf("]\n");
		}

		front = 0;
		flag = 0;
		R = 0;
	}

	return 0;
}
