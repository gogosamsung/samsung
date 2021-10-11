package algo211006_10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
시작: 10/9 11:59 - 12:10 풀엇으나 틀림...

반지름 1,2,...,N 작아지는 순으로 바닥에 놓여있고, 중심은 같다.
각각의 원판에는 M개의 정수가 적혀있다.

원판 회전 -> 수의 위치를 기준으로 한다.
회전시킨 후의 수의 위치 - 회전시키기 전과 일치

원판에 적힌 수의 합

## arraylist로 하니까 틀렸고, 배열로 하니까 맞았음... 왜 arraylist는 안될까..
맞는 풀이!

 */
public class BOJ_Gold3_17822_원판돌리기_배열 {

	static int circles[][];
	static Queue<Integer> circleIdx;
	static Queue<int[]> queue;
	static int N, M, T, order[][];
	static int visited[][];
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		
		circles = new int[N+1][M];
		circleIdx = new LinkedList<Integer>();
		queue = new LinkedList<int[]>();
		
		for (int idx = 1; idx <= N; idx++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int m = 0; m < M; m++) {
				circles[idx][m] = Integer.parseInt(st.nextToken());
			}
		}
		
		order = new int[T][3];
		for (int t = 0; t < T; t++) {
			st = new StringTokenizer(in.readLine(), " ");
			order[t][0] = Integer.parseInt(st.nextToken());
			order[t][1] = Integer.parseInt(st.nextToken());
			order[t][2] = Integer.parseInt(st.nextToken());
		}
		
		// logic
		for (int t = 0; t < T; t++) {
			
			// 1. 방 번호가 x배수인 원판을 d방향으로 k칸 회전시킨다.
			rotate(t);
			
			// 2. 원판에 수가 남아 있으면, 인접하면서 수가 같은 것을 모두 찾는다.
			if (isRemain()) { // 원판에 수가 남아 있으면
				goSecond();
			} else {
				break;
			}
		}
		
		System.out.println(countSum());
	}
	
	private static boolean isRemain() {
		for (int circleIdx = 1; circleIdx <= N; circleIdx++) {
			for (int cIdx = 0; cIdx < M; cIdx++) {
				if (circles[circleIdx][cIdx] != 0) {
					return true;
				}
			}
		}
		return false;
	}

	static int dr[] = {1,0,-1,0};
	static int dc[] = {0,1,0,-1};
	private static void goSecond() {
		queue.clear();

		visited = new int[N+1][M];
		
		boolean isAdj = false;
		for (int r = 1; r <= N; r++) {
			for (int c = 0; c < M; c++) {
				if (visited[r][c] != 0) continue;
				queue.offer(new int[] {r,c});
				visited[r][c] = -1;
				
				while (!queue.isEmpty()) {
					int curCircle = queue.peek()[0];
					int curNumIdx = queue.peek()[1];
					queue.poll();
					int curNum = circles[curCircle][curNumIdx];
					if (curNum == 0) continue;
					
					for (int dir = 0; dir < 4; dir++) {
						int nCircle = curCircle + dr[dir];
						int nNumIdx = curNumIdx + dc[dir];
						nNumIdx = nNumIdx < 0 ? M + nNumIdx : nNumIdx % M;
						
						if (nCircle < 1 || nCircle > N || visited[nCircle][nNumIdx] != 0) continue;
						
						if (circles[nCircle][nNumIdx] == curNum) {
							queue.offer(new int[] {nCircle, nNumIdx});
							visited[curCircle][curNumIdx] = 1;
							visited[nCircle][nNumIdx] = 1;
							isAdj = true;
						}
					}
				}
				
			}
		}
		
		if (isAdj) { // 인접한 수 있는 경우
			for (int r = 1; r <= N; r++) {
				for (int c = 0; c < M; c++) {
					if (visited[r][c] == 1) {
						circles[r][c] = 0;
					}
				}
			}
		} else { // 인접한 수 없는 경우
			int sum = 0;
			int cnt = 0;
			
			for (int circleIdx = 1; circleIdx <= N; circleIdx++) {
				for (int cIdx = 0; cIdx < M; cIdx++) {
					int curNum = circles[circleIdx][cIdx];
					if (curNum != 0) {
						++cnt;
						sum += curNum;
					}
				}
			}
			
			double avg = (double) sum / cnt;
			
			for (int circleIdx = 1; circleIdx <= N; circleIdx++) {
				for (int cIdx = 0; cIdx < M; cIdx++) {
					int curNum = circles[circleIdx][cIdx];
					if (curNum != 0) {
						if (curNum > avg) {
							circles[circleIdx][cIdx] = curNum - 1;
						} else if (curNum < avg) {
							circles[circleIdx][cIdx] = curNum + 1;
						}
					}
				}
			}
		}
	}
	
	private static void rotate(int t) {
		circleIdx.clear();

		// 속하는 원판 다 모으기
//		int num = order[t][0];
//		int tempNum = order[t][0];
//		while (num <= N) {
//			circleIdx.add(num);
//			num += tempNum;
//		}
		for (int idx = 1; idx <= N; idx++) { // 속하는 원판 다 모으기
		if (idx % order[t][0] == 0) {
			circleIdx.add(idx);
		}
	}
	
		
		while (!circleIdx.isEmpty()) {
			int curCircleIdx = circleIdx.poll();
			
			int totalCnt = order[t][2];
			if (order[t][1] == 0) { // 방향이 시계방향일 때
				for (int cnt = 0; cnt < totalCnt; cnt++) {
					int lastNum = circles[curCircleIdx][M-1];
					for (int idx = M-1; idx > 0; idx--) {
						circles[curCircleIdx][idx] = circles[curCircleIdx][idx-1];
					}
					circles[curCircleIdx][0] = lastNum;
				}
			} else if (order[t][1] == 1) { // 방향이 반시계
				for (int cnt = 0; cnt < totalCnt; cnt++) {
					int firstNum = circles[curCircleIdx][0];
					for (int idx = 0; idx < M-1; idx++) {
						circles[curCircleIdx][idx] = circles[curCircleIdx][idx+1];
					}
					circles[curCircleIdx][M-1] = firstNum;
				}
			}
		}
	}

	private static int countSum() {
		int sum = 0;
		for (int circleIdx = 1; circleIdx <= N; circleIdx++) {
			for (int cIdx = 0; cIdx < M; cIdx++) {
				sum += circles[circleIdx][cIdx];
			}
		}
		return sum;
	}
}
