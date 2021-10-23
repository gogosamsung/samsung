package algo211018_24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_Silver3_14501_퇴사 {
	
	static int N, dp[], t[], p[];

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		N = Integer.parseInt(in.readLine());
		
		dp = new int[N+2];
		t = new int[N+1];
		p = new int[N+1];
		
		for (int idx = 1; idx <= N; idx++) {
			st = new StringTokenizer(in.readLine(), " ");
			t[idx] = Integer.parseInt(st.nextToken());
			p[idx] = Integer.parseInt(st.nextToken());
		}
		
		for (int idx = 1; idx <= N; idx++) {
			if (idx + t[idx] <= N+1) {
				dp[idx + t[idx]] = Math.max(dp[idx] + p[idx], dp[idx + t[idx]]);
			}
			
			dp[idx + 1] = Math.max(dp[idx + 1], dp[idx]);
		}
		
		System.out.println(dp[N+1]);
		
	}
}
