package algo2110_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 선거구 공평하게
 * 
 * 재현시 NxN
 * 
 * 구역을 다섯개의 선거구로 나눠야 함. 각 구역은 다섯 선거구 중 하나에 포함되어야 한다. 선거구 - 구역을 적어도 하나 포함 한 선거구에
 * 포함되어 있는 구역은 모두 연결되어 있어야 한다.
 * 
 * 구역 A에서 인접한 구역을 통해서 구역 B로 갈 수 있을 때, 두 구역은 "연결되어 있다."
 * 
 * 중간에 통하는 인접한 구역은 0개 이상이어야하고, 모두 같은 선거구에 포함된 구역이어야 한다.
 * 
 * ### 선거구를 나누는 방법 1. 기준점 (x,y)와 경계의 길이 d1, d2를 정한다.
 *** 
 * 구해야할 것 - 인구가 가장 많은 선거구와 가장 적은 선거구의 인구 차이의 최솟값
 */
public class BOJ_Gold4_17779_게리맨더링2 {

	static int map[][], A[][];
	static int N, minDiff = Integer.MAX_VALUE, population[];
	static int[] dr = { 1, -1, 0, 0 };
	static int[] dc = { 0, 0, 1, -1 };

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(in.readLine());

		map = new int[N + 1][N + 1];
		A = new int[N + 1][N + 1];
		population = new int[6];
		StringTokenizer st = null;

		for (int r = 1; r <= N; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 1; c <= N; c++) {
				A[r][c] = Integer.parseInt(st.nextToken());
			}
		}

		for (int x = 1; x <= N; x++) {
			for (int y = 1; y <= N; y++) {
				for (int d1 = 1; d1 <= N; d1++) {
					for (int d2 = 1; d2 <= N; d2++) {

						if (x + d1 + d2 <= N && x < x+d1+d2 && x+d1+d2 <= N && 1 <= y - d1 && y - d1 < y && y < y + d2 && y + d2 <= N) {
							initMap();
							seperateSeongegu(x, y, d1, d2);
							getMinDiff();
						}
					}
				}
			}
		}

		System.out.println(minDiff);
	}

	private static void getMinDiff() {
		Arrays.fill(population, 0);

		for (int r = 1; r <= N; r++) {
			for (int c = 1; c <= N; c++) {
				population[map[r][c]] += A[r][c];
			}
		}

		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int idx = 1; idx <= 5; idx++) {
			if (min > population[idx])
				min = population[idx];
			if (max < population[idx])
				max = population[idx];
		}

		minDiff = Math.min(minDiff, max - min);
	}

	private static void seperateSeongegu(int x, int y, int d1, int d2) {

		// 기준점 나누기
		// 1.
		int curR = x;
		int curC = y;
		while (curR <= x + d1) {
			map[curR][curC] = 5;
			++curR;
			--curC;
		}

		curR = x;
		curC = y;
		while (curR <= x + d2) {
			map[curR][curC] = 5;
			++curR;
			++curC;
		}

		curR = x + d1;
		curC = y - d1;
		while (curR <= x + d1 + d2) {
			map[curR][curC] = 5;
			++curR;
			++curC;
		}

		curR = x + d2;
		curC = y + d2;
		while (curR <= x + d2 + d1) {
			map[curR][curC] = 5;
			++curR;
			--curC;
		}

		// 나머지 구간 체크
		for (int r = 1; r < x + d1; r++) {
			for (int c = 1; c <= y; c++) {
				if (map[r][c] != 5) {
					map[r][c] = 1;
				} else if (map[r][c] == 5) {
					break;
				}
			}
		}

		for (int r = 1; r <= x + d2; r++) {
			for (int c = N; c > y; c--) {
				if (map[r][c] != 5) {
					map[r][c] = 2;
				} else if (map[r][c] == 5) {
					break;
				}
			}
		}

		for (int r = x + d1; r <= N; r++) {
			for (int c = 1; c < y - d1 + d2; c++) {
				if (map[r][c] != 5) {
					map[r][c] = 3;
				} else if (map[r][c] == 5) {
					break;
				}
			}
		}

		for (int r = x + d2 + 1; r <= N; r++) {
			for (int c = N; c >= y - d1 + d2; c--) {
				if (map[r][c] != 5) {
					map[r][c] = 4;
				} else if (map[r][c] == 5) {
					break;
				}
			}
		}

		// 경계선 안에 포함된 구간 5로 세팅
		for (int r = 1; r <= N; r++) {
			for (int c = 1; c <= N; c++) {
				if (map[r][c] == 0) map[r][c] = 5;
			}
		}
	}

	private static void initMap() {
		for (int r = 1; r <= N; r++) {
			Arrays.fill(map[r], 0);
		}
	}
}
