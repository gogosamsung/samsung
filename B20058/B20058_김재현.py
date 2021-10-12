from collections import deque

n, q = map(int, input().split())
nn = 2 ** n
data = [list(map(int, input().split())) for _ in range(nn)]

commands = list(map(int, input().split()))
dx = [0, 0, 1, -1]
dy = [1, -1, 0, 0]


def bfs(a: int, b: int) -> int:
    """bfs 를 통해 가장 큰 얼음을 찾는다

    Args:
        a (int): 행 위치
        b (int): 열 위치

    Returns:
        int: 가장 큰 얼음 덩어리
    """
    q = deque([(a, b)])
    visited[a][b] = 1
    cnt = 1
    while q:
        x, y = q.popleft()
        for i in range(4):
            nx = x + dx[i]
            ny = y + dy[i]

            if not (0 <= nx < nn and 0 <= ny < nn):
                continue
            if not visited[nx][ny] and data[nx][ny]:
                q.append([nx, ny])
                visited[nx][ny] = 1
                cnt += 1
    return cnt


for command in commands:
    l = 2 ** command
    for row in range(0, nn, l):
        for col in range(0, nn, l):
            # 시작 점 정하기
            temp1 = []
            for srow in range(row, row + l):
                temp = []
                for scol in range(col, col + l):
                    temp.append(data[srow][scol])
                temp1.append(temp)
            rotate = [list(line) for line in zip(*temp1[::-1])]

            for srow in range(l):
                for scol in range(l):
                    data[row + srow][col + scol] = rotate[srow][scol]
    vc = []
    for row in range(nn):
        for col in range(nn):
            cnt = 0
            for srow in range(4):
                nx = row + dx[srow]
                ny = col + dy[srow]
                if not (0 <= nx < nn and 0 <= ny < nn) or data[nx][ny] == 0:
                    continue
                cnt += 1
            if cnt < 3:
                vc.append((row, col))

    for row, col in vc:
        if data[row][col]:
            data[row][col] -= 1

visited = [[0] * nn for _ in range(nn)]
answer = 0
result = 0
for row in range(nn):
    for col in range(nn):
        if data[row][col]:
            result += data[row][col]
            if not visited[row][col]:
                area = bfs(row, col)
                answer = max(answer, area)

print(result)
print(answer)
