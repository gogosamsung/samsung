from copy import deepcopy

dx = [0, 0, 1, -1]
dy = [1, -1, 0, 0]


def expand() -> None:
    """
    미세먼지 확장
    """
    global data
    copy = deepcopy(data)
    for row in range(n):
        for col in range(m):
            if data[row][col] >= 5:
                # 5보다 크면
                cnt = 0  # 얼마나 확산했는지
                for i in range(4):
                    nx = row + dx[i]
                    ny = col + dy[i]

                    if not (0 <= nx < n and 0 <= ny < m):
                        continue
                    # 범위 안에 없으면 종료

                    if data[nx][ny] != -1:
                        copy[nx][ny] += data[row][col] // 5
                        cnt += data[row][col] // 5

                copy[row][col] -= cnt  # 확산값 빼기

    data = deepcopy(copy)


dx1 = [0, -1, 0, 1]
dy1 = [1, 0, -1, 0]
dx2 = [0, 1, 0, -1]
dy2 = [1, 0, -1, 0]


def move() -> None:
    """
    먼지가 이동한다.
    """
    # 1 - 아래
    x, y = first
    dir = 0
    before = data[x][y]
    while True:
        nx = x + dx1[dir]
        ny = y + dy1[dir]

        if not (0 <= nx < n and 0 <= ny < m):
            dir += 1
            continue
        data[nx][ny], before = before, data[nx][ny]
        if first == (nx, ny):
            break
        x, y = nx, ny
    data[first[0]][first[1]], data[first[0]][first[1]+1] = -1, 0

    x, y = second
    dir = 0
    before = data[x][y]
    while True:
        nx = x + dx2[dir]
        ny = y + dy2[dir]

        if not (0 <= nx < n and 0 <= ny < m):
            dir += 1
            continue
        data[nx][ny], before = before, data[nx][ny]
        if second == (nx, ny):
            break
        x, y = nx, ny
    data[second[0]][second[1]], data[second[0]][second[1] + 1] = -1, 0


n, m, t = map(int, input().split())
# n, m, t 를 입력 받는다
data = [list(map(int, input().split())) for _ in range(n)]
# 2차원 데이터 입력

first, second = (0, 0), (0, 0)
# 위에 있는 것, 아래에 있는 것

for ele in range(n):
    # 0 열에서 데이터를 검색
    if data[ele][0] == -1:
        first = (ele, 0)
        second = (ele + 1, 0)
        break

while t:
    expand()  # 확산
    move()  # 이동
    t -= 1

result = 0
for row in range(n):
    for col in range(m):
        if data[row][col] > 0:
            # 0보다 큰 값들은 전부 더한다
            result += data[row][col]
print(result)
