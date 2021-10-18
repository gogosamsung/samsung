package algo211011_17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
10/18 12:24 시작 - 1:05 : 41분 소요

NxN인 지도가 있다. 지도의 각 칸에는 그 곳의 높이가 적혀져 있다.
지도에서 지나갈 수 있는 길이  몇 개 있는지
길이 - 한 행 또는 한 열의 전부(한쪽 끝에서 다른쪽 끝까지 지나가는 것)

# 길을 지나갈 수 있으려면, 길에 속한 모든 칸의 높이가 모두 같아야 한다.
or 경사로를 놓아서 지나갈 수 있는 길을 만들 수 있다.

경사로는 높이가 항상 1이며, 길이는 L이다. : 낮은 칸과 높은 칸을 연결한다
조건
- 경사로는 낮은 칸에 놓으며, L개의 연속된 칸에 경사로의 바닥이 모두 접해야 한다.
- 낮은 칸과 높은 칸의 높이 차이는 1이어야 한다.
- 경사로를 놓을 낮은 칸의 높이는 모두 같아야 하고, L개의 칸이 연속되어 있어야 한다.

아래와 같은 경우에는 경사로를 놓을 수 없다.
- 경사로를 놓은 곳에 또 경사로 놓는 경우
- 낮은 칸과 높은 칸의 높이 차이가 1이 아닌 경우
- 낮은 지점의 칸의 높이가 모두 같지 않거나, L개가 연속되지 않은 경우
- 경사로를 놓다가 범위를 벗어나는 경우
 */
public class BOJ_Gold3_14890_경사로 {
	
	static int N, L, map[][], curArr[], availableRoad;
	static boolean stair[];
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		curArr = new int[N];
		stair = new boolean[N];
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(in.readLine(), " ");
			for (int c = 0; c < N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		// logic 
		// 가로
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				curArr[c] = map[r][c];
			}
			
			if (checkRoad()) ++availableRoad;
		}
		
		// 세로
		for (int c = 0; c < N; c++) {
			for (int r = 0; r < N; r++) {
				curArr[r] = map[r][c];
			}
			
			if (checkRoad()) ++availableRoad;
		}
		
		System.out.println(availableRoad);

	}
	
	private static boolean checkRoad() {
		// 모두 높이가 같은지 체크
		int curIdx = 0;
		int curHeight = curArr[0];
		boolean same = true;
		
		while (++curIdx < N) {
			if (curArr[curIdx] != curHeight) {
				same = false;
				break;
			}
		}
		
		if (same) return true;
		
		// 높이가 다르면
		// 높이 차이가 1이 아니면 끝
		Arrays.fill(stair, false);
		
		curIdx = 1;
		
		while (curIdx < N) {
			
			if (curArr[curIdx] == curArr[curIdx-1]) {
				++curIdx;
				continue;
			}
			
			if (Math.abs(curArr[curIdx] - curArr[curIdx-1]) > 1) return false;
			
			// 높이 차이가 1만큼 낮아질 때
			if (curArr[curIdx] - curArr[curIdx-1] == -1) {
				if (curIdx - 1 + L >= N) return false; // 범위 벗어나면 끝
				
				int lowHeight = curArr[curIdx];
				for (int stairIdx = curIdx; stairIdx < curIdx + L; stairIdx++) {
					if (lowHeight != curArr[stairIdx]) return false; // 낮은 칸의 높이가 L개 연속으로 같지 않으면 끝
					if (stair[stairIdx]) return false; // 이미 경사로를 높은 칸이면 끝
				}
				
				// 경사로 놓기
				for (int stairIdx = curIdx; stairIdx < curIdx + L; stairIdx++) {
					stair[stairIdx] = true;
				}
				
				curIdx += L;
				
				// 높이 차이가 1만큼 높아질 때
			} else if (curArr[curIdx] - curArr[curIdx-1] == 1) {
				if (curIdx - L < 0) return false; // 범위 벗어나면 끝
				
				int lowHeight = curArr[curIdx-1];
				for (int stairIdx = curIdx - 1; stairIdx >= curIdx - L; stairIdx--) {
					if (lowHeight != curArr[stairIdx]) return false; // 낮은 칸의 높이가 L개 연속으로 같지 않으면 끝
					if (stair[stairIdx]) return false; // 이미 경사로를 높은 칸이면 끝
				}
				
				// 경사로 놓기
				for (int stairIdx = curIdx - 1; stairIdx >= curIdx - L; stairIdx--) {
					stair[stairIdx] = true;
				}
				
				++curIdx;
			}
		}
		
		return true;
	}
}
