package algo2109_4;

// idx 이용.. 시뮬레이션은 꼼꼼히 풀어야 한다.
// 파란색 영역 index 제대로 했으면 좋았을듯
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_Gold2_20061_모노미노도미노2 {

	static int N, block[][], greenRIdx[], blueRIdx[], score, cellCnt;
	static boolean green[][], blue[][];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		N = Integer.parseInt(in.readLine());

		green = new boolean[6][4];
		blue = new boolean[6][4];
		block = new int[N][3];
		greenRIdx = new int[4];
		blueRIdx = new int[4];

		// RIdx 초기화
		for (int idx = 0; idx < 4; idx++) {
			greenRIdx[idx] = 6;
			blueRIdx[idx] = 6;
		}

		for (int idx = 0; idx < N; idx++) {
			st = new StringTokenizer(in.readLine(), " ");
			int t = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			block[idx][0] = t;
			block[idx][1] = x;
			block[idx][2] = y;
		}

		for (int blockIdx = 0; blockIdx < N; blockIdx++) {
			// 1. 블럭이 내려온다.
			checkBlock(block[blockIdx][0], block[blockIdx][1], block[blockIdx][2]);

			// 2. 행 체크해서 칸 지우고 점수 추가
			checkArea();

			// 3. 연한칸 체크
			checkLightColorArea();
			
			// 4. rIdx 체크
			updateRIdx();
		}
		
		// 모든 칸 체크
		checkResultArea();
		
		System.out.println(score);
		System.out.println(cellCnt);
	}

	private static void checkResultArea() {
		for (int r = 2; r < 6; r++) {
			for (int c = 0; c < 4; c++) {
				if (green[r][c]) ++cellCnt;
				if (blue[r][c]) ++cellCnt;
			}
		}
		
	}

	private static void updateRIdx() {
		
		Arrays.fill(greenRIdx, 6);
		Arrays.fill(blueRIdx, 6);
		for (int c = 0; c < 4; c++) {
			for (int rIdx = 5; rIdx >= 0; rIdx--) {
				if (green[rIdx][c]) greenRIdx[c] = rIdx;
				if (blue[rIdx][c]) blueRIdx[c] = rIdx;
			}
		}
	}

	private static void checkLightColorArea() {
		// 초록색 영역 체크
		int lineCnt = 0;
		for (int r = 0; r <= 1; r++) {
			for (int c = 0; c < 4; c++) {
				if (green[r][c]) {
					++lineCnt;
					break;
				}
			}
		}

		for (int rIdx = 5; rIdx >= lineCnt; rIdx--) {
			green[rIdx] = green[rIdx-lineCnt].clone();
		}
		Arrays.fill(green[0], false);
		if (lineCnt == 2) {
			Arrays.fill(green[1], false);
		}
		
		// 파란색 영역 체크
		lineCnt = 0;
		for (int r = 0; r <= 1; r++) {
			for (int c = 0; c < 4; c++) {
				if (blue[r][c]) {
					++lineCnt;
					break;
				}
			}
		}

		for (int rIdx = 5; rIdx >= lineCnt; rIdx--) {
			blue[rIdx] = blue[rIdx-lineCnt].clone();
		}
		Arrays.fill(blue[0], false);
		if (lineCnt == 2) {
			Arrays.fill(blue[1], false);
		}
	}

	private static void checkArea() {
		// 초록색 영역 체크
		for (int r = 0; r < 6; r++) {
			int cellCnt = 0;
			for (int c = 0; c < 4; c++) {
				if (green[r][c]) {
					++cellCnt;
				}
			}

			if (cellCnt != 4)
				continue;

			++score;
			// 행 없애고 모든 블럭 아래로 땡기기
			for (int curR = r; curR > 0; curR--) {
				green[curR] = green[curR - 1].clone();
			}
			Arrays.fill(green[0], false);
		}

		// 파란색 영역 체크
		for (int r = 0; r < 6; r++) {
			int cellCnt = 0;
			for (int c = 0; c < 4; c++) {
				if (blue[r][c]) {
					++cellCnt;
				}
			}

			if (cellCnt != 4)
				continue;

			++score;
			// 행 없애고 모든 블럭 아래로 땡기기
			for (int curR = r; curR > 0; curR--) {
				blue[curR] = blue[curR - 1].clone();
			}
			Arrays.fill(blue[0], false);
		}
	}

	private static void checkBlock(int t, int curR, int curC) {
		if (t == 1) {
			// 초록색 체크 => c 체크
			int g_rIdx = greenRIdx[curC];
			green[g_rIdx-1][curC] = true;

			// 파란색 => r 체크
			int b_rIdx = blueRIdx[3-curR];
			blue[b_rIdx-1][3-curR] = true;
		} else if (t == 2) {
			// 초록색 체크 => c 체크
			int gf_rIdx = greenRIdx[curC];
			int gb_rIdx = greenRIdx[curC + 1];
			
			if (gf_rIdx < gb_rIdx) {
				green[gf_rIdx-1][curC] = true;
				green[gf_rIdx-1][curC+1] = true;
			} else {
				green[gb_rIdx-1][curC] = true;
				green[gb_rIdx-1][curC+1] = true;
			}

			// 파란색 => r 체크
			int b_rIdx = blueRIdx[3-curR];
			blue[b_rIdx-1][3-curR] = true;
			blue[b_rIdx-2][3-curR] = true;
		} else if (t == 3) {
			// 초록색 체크 => c 체크
			int g_rIdx = greenRIdx[curC];
			green[g_rIdx-1][curC] = true;
			green[g_rIdx-2][curC] = true;

			// 파란색 => r 체크
			int bf_rIdx = blueRIdx[3-curR];
			int bb_rIdx = blueRIdx[3-curR-1];
			
			if (bf_rIdx < bb_rIdx) {
				blue[bf_rIdx-1][3-curR] = true;
				blue[bf_rIdx-1][3-curR-1] = true;
			} else {
				blue[bb_rIdx-1][3-curR] = true;
				blue[bb_rIdx-1][3-curR-1] = true;
			}
		}
	}

}
