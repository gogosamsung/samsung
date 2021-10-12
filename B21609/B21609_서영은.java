package algo211011_17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/*
10/12 8:20 시작 - 10:10 끝
코딩 동아리에서 게임을 만듬
NxN인 격자에서 진행된다. 초기에 격자의 모든칸에는 블록이 하나씩 들어있다.

블록 - 검은색 블록, 무지개 블록, 일반 블록

일반블록 - M가지 색상 : M이하의 자연수로 표현
검은색 블록 - -1
무지개 블록 - 0

(i, j)는 격자의 i번 행, j번 열을 의미

상하좌우 = 인접한 칸

블록 그룹 - 연결된 블록의 집합
- 그룹에는 일반 블록이 적어도 하나 있어야 하고, 일반 블록의 색은 모두 같아야 한다.
- 검은색 블록은 포함되면 안되고, 무지개 블록은 얼마나 들어있든 상관없다.
- 그룹에 속한 블록의 개수는 2보다 크거나 같아야 하며
- 임의의 한 블록에서 그룹에 속한 인접한 칸으로 이동해서 그룹에 속한 다른 모든 칸으로 이동할 수 있어야 한다.
- 블록 그룹의 기준 블록: 무지개 블록이 아닌 블록 중에서 행의 번호가 가장 작은 블록, 그러한 블록이 여러개면 열의 번호가 가장 작은 블록

오토 플레이 기능 - 블록 그룹이 존재하는 동안 계속해서 반복되어야 한다.
1. 크기가 가장 큰 블록 그룹을 찾는다. 
그러한 블록 그룹이 여러 개라면 포함된 무지개 블록의 수가 가장 많은 블록 그룹, 
-> 여러개라면 기준 블록의 행이 가장 큰 것
-> 여러개라면 열이 가장 큰 것
2. 1에서 찾은 블록 그룹의 모든 블록을 제거한다. 블록 그룹에 포함된 블록의 수를 B라고 했을 때, B^2점을 획득한다.
3. 격자에 중력이 작용한다.
4. 격자가 90도 반시계 방향으로 회전한다.
5. 다시 격자에 중력이 작용한다.

격자에 중력 작용 -> "검은색 블록을 제외한" 모든 블록이 행의 번호가 큰 칸으로 이동한다. 이동은 다른 블록이나 격자의 경계를 만나기 전까지 계속된다.

##구해야할 것 : 오토 플레이가 모두 끝났을 때 획득한 점수의 합!!

1<=N<=20
1<=M<=5
 */

class Group implements Comparable<Group>{
	int cellCnt;
	int rainbow;
	int centerR;
	int centerC;

	public Group(int cellCnt, int rainbow, int centerR, int centerC) {
		super();
		this.cellCnt = cellCnt;
		this.rainbow = rainbow;
		this.centerR = centerR;
		this.centerC = centerC;
	}

	@Override
	public int compareTo(Group o) {
		if (this.cellCnt == o.cellCnt) {
			if (this.rainbow == o.rainbow) {
				if (this.centerR == o.centerR) {
					return o.centerC - this.centerC;
				} else {
					return o.centerR - this.centerR;
				}
			} else {
				return o.rainbow - this.rainbow;
			}
		} else {
			return o.cellCnt - this.cellCnt;
		}
	}
}
public class BOJ_Gold2_21609_상어중학교 {
	
	static int N, M, totalScore, map[][];
	static PriorityQueue<Group> blockGroups;
	static Queue<int[]> queue;
	static boolean visited[][];
	static Stack<Integer> stack;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		queue = new LinkedList<int[]>();
		blockGroups = new PriorityQueue<>();
		stack = new Stack<>();
		
		map = new int[N][N];
		
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 0; c < N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		// logic
		
		while (true) {
			blockGroups.clear();
			// 1. 가장 큰 블록 그룹을 찾는다.
			findBigGroup();
			
			//블록 그룹이 없으면 끝낸다.
			if (blockGroups.size() == 0) break;
			
			// 2. 1에서 찾은 블록 그룹의 모든 블록을 제거한다. 점수체크
			removeBlockGroup();
			
			// 3. 격자에 중력이 작용한다.
			workGravity();
			
			// 4. 90도 반시계방향으로 돌리기
			rotate();
			
			// 5. 격자에 중력이 작용한다.
			workGravity();
		}
		
		System.out.println(totalScore);
	}

	private static void rotate() {
		int number[] = new int[N*N];
		
		int numberIdx = 0;
		for (int r = 0; r < N; r++) {
			for (int c = N-1; c >= 0; c--) {
				number[numberIdx++] = map[r][c];
			}
		}
		
		numberIdx = 0;
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				map[c][r] = number[numberIdx++];
			}
		}
		
	}

	private static void workGravity() {
		
		for (int c = 0; c < N; c++) {
			stack.clear();
			
			// 위에서부터
			int r = 0;
			while (r < N) {
				if (map[r][c] == -1) {
					int curR = r;
					while (!stack.isEmpty()) {
						map[--curR][c] = stack.pop();
					}
					while (curR>0) {
						if (map[--curR][c] == -1) break;
						map[curR][c] = -2;
					}
					++r;
					continue;
				}
				
				if (map[r][c] != -2) {
					stack.push(map[r][c]);
				}
				++r;
			}
			
			// 중력으로 떨어뜨리기
			while (!stack.isEmpty()) {
				map[--r][c] = stack.pop();
			}
			while (r > 0) {
				if (map[--r][c] == -1) break;
				map[r][c] = -2;
			}
		}
	}

	private static void removeBlockGroup() {
		Group curG = blockGroups.poll();
		
		queue.clear();

		int score = 0;
		visited = new boolean[N][N];
		queue.offer(new int[] {curG.centerR, curG.centerC});
		visited[curG.centerR][curG.centerC] = true;

		
		int curColor = map[curG.centerR][curG.centerC];
		while (!queue.isEmpty()) {
			int curR = queue.peek()[0];
			int curC = queue.peek()[1];
			queue.poll();
			map[curR][curC] = -2; // 빈 공간
			++score;
			
			for (int dir = 0; dir < 4; dir++) {
				int nr = curR + dr[dir];
				int nc = curC + dc[dir];
			
				// 빼먹은 조건 -> -2
				if (!checkBoundary(nr, nc) || visited[nr][nc] || map[nr][nc] == -1 || map[nr][nc] == -2) continue;
				
				if (map[nr][nc] == curColor || map[nr][nc] == 0) {
					queue.offer(new int[] {nr, nc});
					visited[nr][nc] = true;
				}
			}
		}
		
		totalScore += score * score;
	}

	static int dr[] = {1,-1,0,0}; // 아위오왼
	static int dc[] = {0,0,1,-1};
	private static void findBigGroup() {
		queue.clear();
		visited = new boolean[N][N];
		
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (map[r][c] == 0 || map[r][c] == -1 || visited[r][c] || map[r][c] == -2) continue;
				
				queue.offer(new int[] {r, c});
				visited[r][c] = true;
				int cellCnt = 0;
				int rainbowCnt = 0;
				int centerR = r;
				int centerC = c;
				int curColor = map[r][c];
				
				while (!queue.isEmpty()) {
					int curR = queue.peek()[0];
					int curC = queue.peek()[1];
					queue.poll();
					++cellCnt;
					
					for (int dir = 0; dir < 4; dir++) {
						int nr = curR + dr[dir];
						int nc = curC + dc[dir];
						
						if (!checkBoundary(nr, nc) || visited[nr][nc] || map[nr][nc] == -1 
								|| map[nr][nc] == -2) continue;
						
						if (map[nr][nc] == 0) { // 무지개 블럭이면
							++rainbowCnt;
							queue.offer(new int[] {nr, nc});
							visited[nr][nc] = true;
						} else if (map[nr][nc] == curColor){
							if (centerR == nr) {
								if (centerC > nc) {
									centerC = nc;
								}
							} else if (centerR > nr) {
								centerR = nr;
								centerC = nc;
							}
							queue.offer(new int[] {nr, nc});
							visited[nr][nc] = true;
						}
						
					}
				}
				
				if (cellCnt >= 2) {
					blockGroups.offer(new Group(cellCnt, rainbowCnt, centerR, centerC));
				}
				
				// 끝날 때마다 무지개블록의 소유권은 되돌려주어야 한다. -> 빼먹은 조건
				for (int curR = 0; curR < N; curR++) {
					for (int curC = 0; curC < N; curC++) {
						if (map[curR][curC] == 0) {
							visited[curR][curC] = false;
						}
					}
				}
			}
		}
		
	}
	private static boolean checkBoundary(int r, int c) {
		return r>=0 && r<N && c>=0 && c<N;
	}

}
