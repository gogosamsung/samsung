package algo2110_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * NxN 크기의 땅, 1x1개의 칸으로 나뉘어짐
 * A[r][c]명이 살고 있다.
 * 국경선은 정사각형 형태
 * 
 * 인구 이동
 * 1. 국경선을 공유하는 두 나라의 인구 차이가 L명 이상, R명 이하라면, 두 나라가 공유하는 국경선을 하루동안 연다.
 * 2. 위의 조건에 의해 열어야하는 국경선을 모두 열렸다면, 인구 이동을 시작한다.
 * 3. 국경선이 열려있어 인접한 칸만을 이용해 이동할 수 있으면, 그 나라를 오늘 하루 동안은 연합이라고 한다.
 * 4. 연합을 이루고 있는 각 칸의 인구수는 (연합의 인구수) / (연합을 이루고 있는 칸의 개수)가 된다. 편의상 소수점은 버린다.
 * 5. 연합을 해체하고, 모든 국경선을 닫는다.
 * 
 * 구해야 하는 것 - 인구 이동이 며칠 동안 발생하는지
 * 
 * N개의 줄에 각 나라의 인구수가 주어진다.
 * 
 * N , L , R
 * N개의 줄에 각 나라의 인구수
 * 
 * 1<=N<=50
 * 1<=L<=R<=100
 * 0<=A[r][c]<=100
 * 
 * => 40분만에 해결!
 */
public class BOJ_Gold5_16234_인구이동 {
	
	static int N, L, R, A[][], check[][], moveCnt;
	static ArrayList<int[]> sameArea;
	static Queue<int[]> queue;
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		A = new int[N][N];
		check = new int[N][N];
		
		queue = new LinkedList<>();
		sameArea = new ArrayList<>();
		
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 0; c < N; c++) {
				A[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		while (true) {
			// 1. 사방 체크해서 국경선 공유하는 나라 영역 표시
			checkLine();
			
			// 2. 국경선 공유하는 나라가 없는지 확인
			if (checkFinish()) {
				break;
			}
			++moveCnt;
			
			// 3. 연합한 인구수 분배
			sharePolulation();
		}
		
		System.out.println(moveCnt);
	}
	
	private static void sharePolulation() {
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (check[r][c] == 0) continue;
				
				int curCell = check[r][c];
				int totalPopul = A[r][c];
				check[r][c] = 0;
				queue.clear();
				sameArea.clear();
				queue.offer(new int[] {r, c});
				sameArea.add(new int[] {r, c});
				while (!queue.isEmpty()) {
					int curR = queue.peek()[0];
					int curC = queue.peek()[1];
					queue.poll();
					
					for (int dir = 0; dir < 4; dir++) {
						int nr = curR + dr[dir];
						int nc = curC + dc[dir];
						
						if (!checkRange(nr, nc) || check[nr][nc] != curCell) continue;

						// 같은 영역에 속하면
						queue.offer(new int[] {nr, nc});
						sameArea.add(new int[] {nr, nc});
						totalPopul += A[nr][nc];
						check[nr][nc] = 0;
					}
				}
				
				int sharePopul = totalPopul / sameArea.size();
				for (int[] curPos : sameArea) {
					A[curPos[0]][curPos[1]] = sharePopul;
				}
			}
		}
	}

	private static boolean checkFinish() {
		if (check[N-1][N-1] == N*N) {
			return true;
		} else {
			return false;
		}
	}

	static int dr[] = {1,-1,0,0};
	static int dc[] = {0,0,1,-1};
	private static void checkLine() {
		int cell = 1;
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (check[r][c] != 0) continue;
				queue.clear();
				queue.offer(new int[] {r, c});
				
				check[r][c] = cell;
				while (!queue.isEmpty()) {
					int curR = queue.peek()[0];
					int curC = queue.peek()[1];
					queue.poll();
					for (int dir = 0; dir < 4; dir++) {
						int nr = curR + dr[dir];
						int nc = curC + dc[dir];
						
						if (!checkRange(nr, nc) || check[nr][nc] != 0) continue;

						// 범위를 충족하면
						if (Math.abs(A[curR][curC] - A[nr][nc]) >= L && Math.abs(A[curR][curC] - A[nr][nc]) <= R) {
							check[nr][nc] = cell;
							queue.offer(new int[] {nr, nc});
						}
					}
				}
				++cell;
			}
		}
	}
	
	private static boolean checkRange(int r, int c) {
		return r>=0 && r<N && c>=0 && c<N;
	}
}
