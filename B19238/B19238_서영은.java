package algo211018_24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/*
10/23 8:00 시작 - 9:30 끝

손님을 도착지로 데려다줄 때마다 연료 충전, 연료가 바닥나면 그 날의 업무가 끝난다.

택시 기사 최백준 - 오늘 M명의 승객을 태우는 것이 목표
NxN : 각 칸은 비어있거나 벽이 놓여 있다.
-> 항상 최단 경로로만 이동

M명의 승객은 빈칸 중 하나에 있으며, 다른 빈칸 중 하나로 이동하려고 한다.

#태울 승객 고를 때 : 현재 위치에서 최단거리가 가장 짧은 승객을 고른다.
=> 이러한 승객이 여러 명이면, 행 번호가 가장 작은 승객을, -> 열 번호가 작은 승객

연료 : 한 칸 이동할 때마다 1만큼 소모
한 승객을 목적지로 성공적으로 이동시키면, 소모한 연료 양의 두 배가 충전된다.

이동하는 도중에 연료가 바닥나면 이동에 실패하고, 그 날의 업무가 끝난다.
승객을 목적지로 이동시킨 동시에 연료가 바닥나는 경우는 성공

### 모든 승객을 성공적으로 데려다줄 수 있는지
### 데려다줄 수 있을 경우 최종적으로 남는 연료의 양은? => 구해야할 것

연료가 바닥나서 다음 출발지나 목적지로 이동할 수 없으면 -1,
모든 손님을 이동시킬 수 없는 경우, -1을 출력

### 택시 위치랑 가장 가까운 승객 거리가 0일 수도 있음을 놓침!!!
 */
class Guest implements Comparable<Guest> {
	int startR;
	int startC;
	int destR;
	int destC;

	public Guest(int startR, int startC, int destR, int destC) {
		super();
		this.startR = startR;
		this.startC = startC;
		this.destR = destR;
		this.destC = destC;
	}

	@Override
	public int compareTo(Guest g) {
		if (this.startR == g.startR) {
			return this.startC - g.startC;
		} else
			return this.startR - g.startR;
	}
}

public class BOJ_Gold4_19238_스타트택시 {

	static int N, M, oil, map[][], dist[][], taxiR, taxiC;
	static ArrayList<Guest> guests;
	static PriorityQueue<Guest> tempGuest;
	static Queue<int[]> queue;
	static Guest nowGuest;
	static boolean visited[][];

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		oil = Integer.parseInt(st.nextToken());

		map = new int[N + 1][N + 1];
		dist = new int[N + 1][N + 1];
		visited = new boolean[N+1][N+1];

		guests = new ArrayList<>();
		tempGuest = new PriorityQueue<>();
		queue = new LinkedList<int[]>();
		for (int r = 1; r <= N; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 1; c <= N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}

		st = new StringTokenizer(in.readLine(), " ");
		taxiR = Integer.parseInt(st.nextToken());
		taxiC = Integer.parseInt(st.nextToken());

		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(in.readLine(), " ");
			int startR = Integer.parseInt(st.nextToken());
			int startC = Integer.parseInt(st.nextToken());
			int destR = Integer.parseInt(st.nextToken());
			int destC = Integer.parseInt(st.nextToken());

			guests.add(new Guest(startR, startC, destR, destC));
		}

		// logic
		while (!guests.isEmpty()) {
			// 1. bfs로 최단거리 표시해주기
			findShortPath();

			// 2. 출발 승객 찾기
			if (!findGuest()) {
				System.out.println("-1");
				return;
			}

			// 3. oil 체크
			if (oil - dist[nowGuest.startR][nowGuest.startC] < 0) {
				System.out.println("-1");
				return;
			} else {
				oil -= dist[nowGuest.startR][nowGuest.startC];
			}

			// 4. 출발지 - 도착지 BFS and oil 체크
			if (!findDestPath()) {
				System.out.println("-1");
				return;
			}
			
			if (oil - dist[nowGuest.destR][nowGuest.destC] < 0) {
				System.out.println("-1");
				return;
			} else {
				oil = oil - dist[nowGuest.destR][nowGuest.destC] + 2*dist[nowGuest.destR][nowGuest.destC];
			}
			
			// 5. 현재 위치 갱신, oil update
			taxiR = nowGuest.destR;
			taxiC = nowGuest.destC;
		}
		
		System.out.println(oil);
	}

	private static boolean findDestPath() {
		// dist 초기화
		initDist();

		queue.clear();
		
		taxiR = nowGuest.startR;
		taxiC = nowGuest.startC;

		queue.offer(new int[] { taxiR, taxiC });

		while (!queue.isEmpty()) {
			int curR = queue.peek()[0];
			int curC = queue.peek()[1];
			queue.poll();

			for (int dir = 0; dir < 4; dir++) {
				int nr = curR + dr[dir];
				int nc = curC + dc[dir];

				if (!checkBoundary(nr, nc) || dist[nr][nc] != 0 || map[nr][nc] == 1
						|| (nr == taxiR && nc == taxiC))
					continue;

				dist[nr][nc] = dist[curR][curC] + 1;
				queue.offer(new int[] { nr, nc });
				
				if (nowGuest.destR == nr && nowGuest.destC == nc) return true;
			}
		}
		
		return false;
	}

	private static boolean findGuest() {
		tempGuest.clear();
		
		// 승객 이동 가능한지 체크 *** 여기가 중요.. 문제 제대로 안읽음
		int size = guests.size();
		for (int idx = 0; idx < size; idx++) {
			Guest tempGuest = guests.get(idx);
			
			if (!visited[tempGuest.startR][tempGuest.startC]) {
				return false;
			}
		}

		Guest curGuest = guests.get(0);
		tempGuest.offer(curGuest);
		int minDist = dist[curGuest.startR][curGuest.startC];

		for (int idx = 1; idx < size; idx++) {
			curGuest = guests.get(idx);
			int curDist = dist[curGuest.startR][curGuest.startC];

			if (minDist > curDist) {
				tempGuest.clear();
				tempGuest.offer(curGuest);
				minDist = curDist;
			} else if (minDist == curDist) {
				tempGuest.offer(curGuest);
			}
		}

		nowGuest = tempGuest.poll();
		guests.remove(nowGuest);
		
		return true;
	}

	static int dr[] = { 1, -1, 0, 0 };
	static int dc[] = { 0, 0, 1, -1 };

	private static void findShortPath() {
		// dist 초기화
		initDist();

		queue.clear();

		queue.offer(new int[] { taxiR, taxiC });
		visited[taxiR][taxiC] = true;

		while (!queue.isEmpty()) {
			int curR = queue.peek()[0];
			int curC = queue.peek()[1];
			queue.poll();

			for (int dir = 0; dir < 4; dir++) {
				int nr = curR + dr[dir];
				int nc = curC + dc[dir];

				if (!checkBoundary(nr, nc) || dist[nr][nc] != 0 || map[nr][nc] == 1
						|| (nr == taxiR && nc == taxiC))
					continue;

				dist[nr][nc] = dist[curR][curC] + 1;
				visited[nr][nc] = true;
				queue.offer(new int[] { nr, nc });
			}
		}
	}

	private static boolean checkBoundary(int r, int c) {
		return r >= 1 && r <= N && c >= 1 && c <= N;
	}

	private static void initDist() {
		for (int r = 1; r <= N; r++) {
			Arrays.fill(dist[r], 0);
			Arrays.fill(visited[r], false);
		}
	}

}
