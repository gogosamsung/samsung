package algo211011_17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
10/12 11:27 시작 - 12:31 끝

칸에는 상어가 최대 한마리. 상어는 크기와 속도를 가지고 있다.

낚시왕 - 1번 열의 한 칸 왼쪽에 있다.

## 낚시왕이 가장 오른쪽 열의 오른쪽 칸에 이동하면 이동을 멈춘다.
낚시왕이 오른쪽으로 한 칸 이동한다.
낚시왕이 있는 열에 있는 상어 중에서 땅과 제일 가까운 상어를 잡는다. 상어를 잡으면 격자판에서 잡은 상어가 사라진다.
상어가 이동한다.

상어는 입력을 주어진 속도로 이동, 속도의 단위는 칸/초
상어가 이동하려고 하는 칸이 격자판의 경계를 넘는 경우, 방향을 반대로 바꿔서 속력읠 유지한 채 이동

이동을 마친 후에, 한 칸에 상어가 두 마리 이상 있으면 크기가 가장 큰 상어가 나머지 상어를 모두 잡아먹는다.

격자판 크기 R, C / 상어의 수 M

둘째 줄부터 M개의 줄에 상어의 정보가 주어진다.

dir -> 1 2 3 4 위 아래 오른쪽 왼쪽

## 낚시왕이 잡은 상어 크기의 합을 출력
 */
class Shark {
	int idx;
	int r;
	int c;
	int s;
	int d;
	int z;
	boolean alive;
	
	public Shark(int idx, int r, int c, int s, int d, int z) {
		super();
		this.idx = idx;
		this.r = r;
		this.c = c;
		this.s = s;
		this.d = d;
		this.z = z;
		this.alive = true;
	}
}

public class BOJ_Gold2_17143_낚시왕 {

	static ArrayList<Shark> sharks;
	static int R, C, M, map[][], tempMap[][], personCIdx, totalSize;
	static int[] dr = {0,-1,1,0,0};
	static int[] dc = {0,0,0,1,-1};
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		sharks = new ArrayList<>();
		
		map = new int[R+1][C+1];
		tempMap = new int[R+1][C+1];
		
		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(in.readLine(), " ");
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int z = Integer.parseInt(st.nextToken());
			sharks.add(new Shark(m+1, r, c, s, d, z));
			map[r][c] = m+1;
		}
		
		personCIdx = 0;
		// logic
		while (++personCIdx <= C) {
			// 1. 낚시왕이 오른쪽으로 한 칸 이동한다.
			// 2. 낚시왕이 있는 열에 있는 상어 중에서 땅과 제일 가까운 상어를 잡는다. 
			getShark();
			// 상어를 잡으면 격자판에서 잡은 상어가 사라진다.
			
			// 3. 상어가 이동한다.
			moveShark();			
		}
		
		System.out.println(totalSize);
	}
	
	private static void moveShark() {
		
		for (int r = 0; r <= R; r++) {
			Arrays.fill(tempMap[r], 0);
		}
		
		int sharkSize = sharks.size();
		for (int i = 0; i < sharkSize; i++) {
			Shark curShark = sharks.get(i);
			
			if (!curShark.alive) continue;
			
			int curR = curShark.r;
			int curC = curShark.c;
			int curS = curShark.s;
			int curD = curShark.d;
			
			for (int cntS = 0; cntS < curS; cntS++) {
				int nr = curR + dr[curD];
				int nc = curC + dc[curD];
				
				if (!checkBoundary(nr, nc)) {
					curD = getReverseDir(curD);
					nr = curR + dr[curD];
					nc = curC + dc[curD];
				}
				
				curR = nr;
				curC = nc;
			}
			
			curShark.r = curR;
			curShark.c = curC;
			curShark.d = curD;
			
			if (tempMap[curR][curC] != 0) {
				Shark existShark = sharks.get(tempMap[curR][curC] - 1);
				
				if (curShark.z > existShark.z) {
					tempMap[curR][curC] = curShark.idx;
					existShark.alive = false;
				} else {
					curShark.alive = false;
				}
			} else { // 해당 자리에 상어가 없을 때
				tempMap[curR][curC] = curShark.idx;
			}
		}
		
		for (int r = 0; r <= R; r++) {
			map[r] = tempMap[r].clone();
		}
		
	}

	private static int getReverseDir(int curD) {
		if (curD == 1) return 2;
		else if (curD == 2) return 1;
		else if (curD == 3) return 4;
		else return 3;
	}

	private static boolean checkBoundary(int r, int c) {
		return r>=1 && r<=R && c>=1 && c<=C;
	}

	private static void getShark() {
		
		for (int r = 1; r <= R; r++) {
			if (map[r][personCIdx] != 0) {
				int sharkIdx = map[r][personCIdx];

				Shark caughtShark = sharks.get(sharkIdx-1);
				if (caughtShark.alive) {
					totalSize += caughtShark.z;
					caughtShark.alive = false;
				}
				
				// 맵 초기화
				map[r][personCIdx] = 0;
				break;
			}
		}
		
	}
}
