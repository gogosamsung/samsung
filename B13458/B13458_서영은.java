package algo2110_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
11:58 시작 - 12:11 끝 -> 13분 소요

N개의 시험장 - 시험장마다 응시자들이 있다.
i번 시험장에 있는 응시자 수 = Ai명

감독관 - 총감독관 : B명, 부감독관 : C명 감시 가능

각각 시험장에 총감독관은 오직 1명, 부감독관은 여러명 가능

응시생들을 시험장마다 모두 감시해야 한다.
필요한 감독관 수의 최솟값 구하기

## 오직 한명!

### 범위가 너무너무너무 중요하다!! 
 */
public class BOJ_Bronze2_13458_시험감독 {

	static int N, A[], B, C; 
	static long totalCnt;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(in.readLine());
		
		A = new int[N];
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		for (int idx = 0; idx < N; idx++) {
			A[idx] = Integer.parseInt(st.nextToken());
		}
		
		st = new StringTokenizer(in.readLine(), " ");
		B = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		checkMain();
		checkSecond();
		
		System.out.println(totalCnt);
	}
	
	private static void checkSecond() {
		for (int idx = 0; idx < N; idx++) {
			if (A[idx] > 0) {
				if (A[idx] % C != 0) {
					totalCnt += A[idx]/C + 1;
				} else {
					totalCnt += A[idx]/C;
				}
			}
		}
	}

	private static void checkMain() {
		for (int idx = 0; idx < N; idx++) {
			if (A[idx] > B) {
				A[idx] -= B;
			} else {
				A[idx] = 0;
			}
			++totalCnt;
		}
	}
}
