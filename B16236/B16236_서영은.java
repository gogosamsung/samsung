package algo211018_24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/*
10/23 10:14 시작 - 10:58 종료
44분 소요

NxN 공간에 물고기 M마리와 아기 상어 1마리가 있다.
한 칸에는 물고기가 최대 1마리 존재

아기 상어, 물고기는 모두 크기를 갖고 있다.

가장 처음 아기 상어의 크기 = 2
1초에 상하좌우로 인접한 한 칸씩 이동 가능

** 아기 상어 : 자신의 크기보다 큰 물고기가 있는 칸은 지나갈 수 없고, 나머지 칸은 모두 지나갈 수 있다.
(자신의 크기와 같거나 작은 칸은 지나갈 수 있다.)
+ 자신의 크기보다 작은 물고기만 먹을 수 있다.

## 아기 상어가 어디로 이동할지 결정하는 방법
더 이상 먹을 수 있는 물고기가 공간에 없다면 아기 상어는 엄마 상어에게 도움을 요청한다.
먹을 수 있는 물고기가 1마리라면, 그 물고기를 먹으러 간다.
먹을 수 있는 물고기가 1마리보다 많다면, 거리가 가장 가까운 물고기를 먹으러 간다.
거리는 아기 상어가 있는 칸에서 물고기가 있는 칸으로 이동할 때, 지나야하는 칸의 개수의 최솟값이다.
거리가 가까운 물고기가 많다면, 가장 위에 있는 물고기, 그러한 물고기가 여러마리라면, 가장 왼쪽에 있는 물고기를 먹는다.
=> r, c가 작은 물고기

아기 상어가 먹을 수 있는 물고기가 있는 칸으로 이동했다면, 이동과 동시에 물고기를 먹고 그 칸은 빈칸이 된다.

자신의 크기와 같은 수의 물고기를 먹을때마다 크기가 1 증가

아기 상어는 몇 초동안 엄마 상어에게 도움을 요청하지 않고 물고기를 잡아먹을 수 있는지?
## 상어 index 제대로 안돌려줬음 그래서 틀림
 */
public class BOJ_Gold4_16236_아기상어 {

	static int N, sharkR, sharkC, sharkSize, eatCnt, map[][], dist[][], time;
	static Queue<int[]> queue;
	static PriorityQueue<int[]> possibleFishes;
	static int dr[] = {1,-1,0,0};
	static int dc[] = {0,0,1,-1};
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(in.readLine());
		
		StringTokenizer st = null;
		
		map = new int[N][N];
		dist = new int[N][N];
		queue = new LinkedList<int[]>();
		possibleFishes = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if (o1[0] == o2[0]) {
					return o1[1] - o2[1];
				} else {
					return o1[0] - o2[0];
				}
			}
		});

		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 0; c < N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
				
				if (map[r][c] == 9) {
					sharkR = r;
					sharkC = c;
					sharkSize = 2;
				}
			}
		}
		
		// logic
		while (true) {
			// 1. bfs 먹을 물고기 찾기
			if (!findEatFish()) {
				break;
			}
			
			int[] curEatFish = possibleFishes.poll();
			time += dist[curEatFish[0]][curEatFish[1]];
			map[curEatFish[0]][curEatFish[1]] = 9;
			++eatCnt;
			
			map[sharkR][sharkC] = 0;
			sharkR = curEatFish[0];
			sharkC = curEatFish[1];
			
			if (eatCnt == sharkSize) {
				eatCnt = 0;
				++sharkSize;
			}
			
		}
		
		System.out.println(time);
		
		
	}
	
	private static boolean findEatFish() {
		initDist();
		queue.clear();
		possibleFishes.clear();
		
		queue.offer(new int[] {sharkR, sharkC});
		
		while (!queue.isEmpty()) {
			int qSize = queue.size();
			
			for (int s = 0; s < qSize; s++) {
				int curR = queue.peek()[0];
				int curC = queue.peek()[1];
				queue.poll();
				
				for (int dir = 0; dir < 4; dir++) {
					int nr = curR + dr[dir];
					int nc = curC + dc[dir];
					
					if (!checkBoundary(nr, nc) || dist[nr][nc] != 0 
							|| (nr == sharkR && nc == sharkC)) continue;
					
					if (map[nr][nc] <= sharkSize) { // 지나간다.
						queue.offer(new int[] {nr, nc});
						dist[nr][nc] = dist[curR][curC] + 1;
					}
					
					if (map[nr][nc] != 0 && map[nr][nc] < sharkSize) { // 먹는다.
						possibleFishes.offer(new int[] {nr,nc});
					}
				}
				
			}
			if (possibleFishes.size() != 0) return true;
		}
		
		if (possibleFishes.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	private static void initDist() {
		for (int r = 0; r < N; r++) {
			Arrays.fill(dist[r], 0);
		}
		
	}

	private static boolean checkBoundary(int r, int c) {
		return r>=0 && r<N && c>=0 && c<N;
	}

}
