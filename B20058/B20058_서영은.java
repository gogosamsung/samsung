package algo211006_10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
10/10 10:48-12:02 -> 1시간 14분

파이어스톰을 시전하려면 시전할 때마다 단계 L을 결정해야 한다. 
1) 파이어스톰은 먼저 격자를 2L × 2L 크기의 부분 격자로 나눈다. 
2) 그 후, 모든 부분 격자를 시계 방향으로 90도 회전시킨다. 
3) 이후 얼음이 있는 칸 3개 또는 그 이상과 인접해있지 않은 칸은 얼음의 양이 1 줄어든다. 
4) (r, c)와 인접한 칸은 (r-1, c), (r+1, c), (r, c-1), (r, c+1)이다. 
아래 그림의 칸에 적힌 정수는 칸을 구분하기 위해 적은 정수이다.

구할 것
남아있는 얼음 A[r][c]의 합
남아있는 얼음 중 가장 큰 덩어리가 차지하는 칸의 개수

 */
public class BOJ_Gold4_20058_마법사상어와파이어스톰 {

	static int N, Q, A[][], L[], tempA[][], edgeLen;
	static Queue<int[]> queue;
	static int dr[] = {-1,1,0,0};
	static int dc[] = {0,0,-1,1};
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		edgeLen = (int) Math.pow(2, N);
		
		A = new int[edgeLen][edgeLen];
		L = new int[Q];
		
		for (int r = 0; r < edgeLen; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 0; c < edgeLen; c++) {
				A[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		st = new StringTokenizer(in.readLine(), " ");
		for (int l = 0; l < Q; l++) {
			L[l] = Integer.parseInt(st.nextToken());
		}
		
		// 로직
		for (int lIdx = 0; lIdx < Q; lIdx++) {
			int curL = (int) Math.pow(2, L[lIdx]);
			
			tempA = new int[edgeLen][edgeLen];
			//1. 모든 격자를 나눈 후, 시계 방향으로 90도 회전
			seperate(curL);
			
//			for (int idx = 0; idx < edgeLen; idx++) {
//				System.out.println(Arrays.toString(A[idx]));
//			}
			// 2. 이후 얼음이 있는 칸 3개 또는 그 이상과 인접해있지 않은 칸은 얼음의 양이 1 줄어든다. 
			checkIce(); // ## 동시에 일어난다!!
		}
		
		System.out.println(sumIce());
		System.out.println(cellCnt());
	}
	
	private static int cellCnt() {
		
		int maxCell = Integer.MIN_VALUE;
		Queue<int[]> queue = new LinkedList<int[]>();
		boolean visited[][] = new boolean[edgeLen][edgeLen];
		
		for (int r = 0; r < edgeLen; r++) {
			for (int c = 0; c < edgeLen; c++) {
				if (visited[r][c] || A[r][c] == 0) continue;
				
				int cellCnt = 0;
				queue.offer(new int[] {r,c});
				visited[r][c] = true;
				
				while (!queue.isEmpty()) {
					int curR = queue.peek()[0];
					int curC = queue.peek()[1];
					queue.poll();
					++cellCnt;
					
					for (int dir = 0; dir < 4; dir++) {
						int nr = curR + dr[dir];
						int nc = curC + dc[dir];
						
						if (!checkBoundary(nr, nc)) continue;
						
						if (A[nr][nc] != 0 && !visited[nr][nc]) {
							visited[nr][nc] = true;
							queue.offer(new int[] {nr, nc});
						}
					}
				}
				
				maxCell = Math.max(maxCell, cellCnt);
			}
		}
		
		return maxCell == Integer.MIN_VALUE ? 0 : maxCell;
	}

	private static boolean checkBoundary(int r, int c) {
		return r>=0 && r<edgeLen && c>=0 && c<edgeLen;
	}

	private static int sumIce() {
		int sum = 0;
		for (int r = 0; r < edgeLen; r++) {
			for (int c = 0; c < edgeLen; c++) {
				sum += A[r][c];
			}
		}
		return sum;
	}

	private static void checkIce() {
		Queue<int[]> meltIcePos = new LinkedList<>();
		for (int r = 0; r < edgeLen; r++) {
			for (int c = 0; c < edgeLen; c++) {
				if (A[r][c] == 0) continue;
				int cnt = 0;
				for (int dir = 0; dir < 4; dir++) {
					int nr = r + dr[dir];
					int nc = c + dc[dir];
					
					if (!checkBoundary(nr, nc)) continue;
					if (A[nr][nc] != 0) ++cnt;
				}
				
				if (cnt < 3) {
					meltIcePos.offer(new int[] {r, c});
				}
			}
		}
		
		while (!meltIcePos.isEmpty()) {
			int curR = meltIcePos.peek()[0];
			int curC = meltIcePos.peek()[1];
			meltIcePos.poll();
			
			--A[curR][curC];
		}
	}

	private static void seperate(int curL) {
		int r = curL;
		int c = curL;
		
		while (r <= edgeLen) {
			while (c <= edgeLen) {
				rotate(r-curL, r, c-curL, c, curL);
				c += curL;
			}
			r += curL;
			c = curL;
		}
		
		for (int idx = 0; idx < edgeLen; idx++) {
			A[idx] = tempA[idx].clone();
		}
	}

	private static void rotate(int rStart, int rEnd, int cStart, int cEnd, int curL) {
		for (int r = rStart; r < rEnd; r++) {
			for (int c = cStart; c < cEnd; c++) {
				tempA[c - cStart + rStart][cEnd-1 - r + rStart] = A[r][c];
			}
		}
	}

}
