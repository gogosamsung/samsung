from collections import deque

def bfs(virus_list: list) -> None:
    """bfs를 통해 조건 식을 확인

    Args:
        virus_list (list): q에 제일 먼저 들어가는 시작 값들
    """
    dist = [[-1] * n for _ in range(n)]

    q = deque(virus_list)
    for row, col in virus_list:
        dist[row][col] = 0

    max_dist = 0
    while q:
        x, y = q.popleft()

        if data[x][y] == 0:
            max_dist = max(max_dist, dist[x][y])

        for i in range(4):
            nx = x + dx[i]
            ny = y + dy[i]

            if not (0 <= nx < n and 0 <= ny < n):
                continue

            if data[nx][ny] != 1 and dist[nx][ny] == -1:
                dist[nx][ny] = dist[x][y] + 1

                q.append([nx, ny])

    a = list(sum(dist, []))
    if a.count(-1) == list(sum(data, [])).count(1):  # 방문 안 한 곳이 벽의 개수와 동일한지
        ans.append(max_dist)  # 리스트없이 바로 min값 구해도 됨


n, m = map(int, input().split())
dx = [1, -1, 0, 0]
dy = [0, 0, 1, -1]

ans = []
sel = []
data =[list(map(int, input().split())) for _ in range(n)]
virus = [(row, col) for row in range(n) for col in range(n) if data[row][col] == 2]


def go(index: int, cnt: int) -> None:
    """조합을 구하기 위한 재귀 함수

    Args:
        index (int): 현재 선택하고 있는 인덱스
        cnt (int): 현재 선택한 개수
    """
    global virus
    if cnt == m:
        bfs(sel[::])
        return

    if index >= len(virus):
        return

    sel.append(virus[index])
    go(index+1, cnt+1)
    sel.pop()

    go(index+1, cnt)

go(0, 0)
print(min(ans) if ans else -1)
