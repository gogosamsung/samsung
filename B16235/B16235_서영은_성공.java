package algo211011_17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/*
10/16 11:16 시작 - 12:09 끝

NxN 크기의 땅 - 1,1부터 시작

S2D2 : 양분을 조사해 상도에게 전송
처음에 양분은 모든 칸에 5만큼 들어있다.

##나무재테크
M개의 나무를 심음
-1x1 칸에 여러 개의 나무가 심어져 있을 수도

사계절
-봄
나무: 자신의 나이만큼 양분을 먹고, 나이가 1 증가
자기 칸에 있는 양분만 먹을 수 있음
여러개의 나무가 있다면, 나이가 어린 나무부터 양분을 먹음
양분이 부족해 자신의 나이만큼 양분 못먹으면 양분 못먹고 바로 죽음

-여름
봄에 죽은 나무가 양분으로 변함
각각의 죽은 나무마다 나이를 2로 나눈 값이 나무가 있던 칸에 양분으로 추가된다.
소수점 아래는 버림

- 가을
나무가 번식
번식하는 나무: 나이가 5의 배수
인접한 8개의 칸에 나이가 1인 나무가 생긴다.
땅을 벗어나는 칸에는 나무가 생기지 않는다.

- 겨울
S2D2가 땅을 돌아다니면서 땅에 양분을 추가한다.
각 칸에 추가되는 양분의 양 A[r][c]

### K년이 지난 후 상도의 땅에 살아있는 나무의 개수


 */
class Tree implements Comparable<Tree>{
	int age;
	boolean alive;
	public Tree(int age) {
		super();
		this.age = age;
		this.alive = true;
	}
	
	@Override
	public int compareTo(Tree o) {
		return Integer.compare(this.age, o.age);
	}
}

public class BOJ_Gold4_16235_나무재테크 {

	static int N, M, K, food[][], A[][];
	static ArrayList<Tree>[][] map;
	static int dr[] = {-1,-1,-1,0,0,1,1,1};
	static int dc[] = {-1,0,1,-1,1,-1,0,1};
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		food = new int[N+1][N+1];
		A = new int[N+1][N+1];
		map = new ArrayList[N+1][N+1];
		
		// 초기 양분 5
		for (int r = 1; r <= N; r++) {
			for (int c = 1; c <= N; c++) {
				food[r][c] = 5;
			}
		}
		for (int r = 0; r <= N; r++) {
			for (int c = 0; c <= N; c++) {
				map[r][c] = new ArrayList<Tree>();
			}
		}
		
		for (int r = 1; r <= N; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 1; c <= N; c++) {
				A[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		// tree 입력
		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(in.readLine(), " ");
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int age = Integer.parseInt(st.nextToken());
			map[r][c].add(new Tree(age));
		}
		
		for (int k = 0; k < K; k++) {
			//봄
			spring();
			
			//여름
			summer();
			
			//가을
			Autumn();
			
			//겨울
			Winter();
		}
		
		System.out.println(checkTree());

	}
	
	private static int checkTree() {
		int cnt = 0;
		for (int r = 1; r <= N; r++) {
			for (int c = 1; c <= N; c++) {
				
				int cellLen = map[r][c].size();
				
				cnt += cellLen;
			}
		}
		return cnt;
	}

	private static void Winter() {
		for (int r = 1; r <= N; r++) {
			for (int c = 1; c <= N; c++) {
				food[r][c] += A[r][c];
			}
		}
	}

	private static void Autumn() {
		for (int r = 1; r <= N; r++) {
			for (int c = 1; c <= N; c++) {
				
				int cellLen = map[r][c].size();
				for (int idx = cellLen - 1; idx >= 0; idx--) {
					Tree curTree = map[r][c].get(idx);
					
					if (curTree.age % 5 == 0) {
						for (int dir = 0; dir < 8; dir++) {
							int nr = r + dr[dir];
							int nc = c + dc[dir];
							
							if (!checkBoundary(nr, nc)) continue;
							
							map[nr][nc].add(new Tree(1));
						}
					}
				}
			}
		}
	}

	private static boolean checkBoundary(int r, int c) {
		return r>=1 && r<=N && c>=1 && c<=N;
	}

	private static void summer() {
		for (int r = 1; r <= N; r++) {
			for (int c = 1; c <= N; c++) {
				
				int cellLen = map[r][c].size();
				for (int idx = cellLen - 1; idx >= 0; idx--) {
					Tree curTree = map[r][c].get(idx);
					
					if (!curTree.alive) {
						food[r][c] += curTree.age/2;
						map[r][c].remove(curTree);
					}
				}
			}
		}
	}
	
	private static void spring() {
		for (int r = 1; r <= N; r++) {
			for (int c = 1; c <= N; c++) {
				Collections.sort(map[r][c]);
				
				int cellLen = map[r][c].size();
				for (int idx = 0; idx < cellLen; idx++) {
					Tree curTree = map[r][c].get(idx);
					
					if (food[r][c] < curTree.age) {
						curTree.alive = false;
					} else {
						food[r][c] -= curTree.age;
						curTree.age += 1;
					}
				}
			}
		}
	}
}
