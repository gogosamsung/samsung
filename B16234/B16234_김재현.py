from collections import deque

n, l, r = map(int, input().split())

data = [list(map(int, input().split())) for _ in range(n)]
dx = [0, 0, 1, -1]
dy = [1, -1, 0, 0]


def bfs() -> bool:
    """국경이 열려 있는지를 확인한다. 

    Returns:
        bool: bfs를 수행하는 건 열려있는 국경이 있다는 뜻
    """
    flag = False
    visited = [[False] * n for _ in range(n)]

    for row in range(n): 
        for col in range(n):
            if not visited[row][col]:
                open =[(row, col)]
                q = deque([(row, col)])
                visited[row][col] = True

                while q: # BFS 검사
                    x, y = q.popleft()

                    for i in range(4): # 4방향을 봐서
                        nx = x + dx[i]
                        ny = y + dy[i]

                        if not (0 <= nx < n and 0 <= ny < n): # 범위 제한
                            continue

                        if not visited[nx][ny] and (l <= abs(data[nx][ny] - data[x][y]) <= r): # 범위 안에 드면
                            q.append([nx, ny])
                            visited[nx][ny] = True
                            open.append([nx, ny]) # 국경이 열린곳 좌표 저장
                            flag = True

                summation = 0
                for x, y in open: # 국경이 열린곳 좌표 저장
                    summation += data[x][y]
                value = summation // len(open)
                for x, y in open:
                    data[x][y] = value

    return flag


time = 0
while True:
    if bfs():
        time += 1
    else:
        break

print(time)
