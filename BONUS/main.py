import sys
sys.stdin = open('input.txt')

dx = [0, 0, 1, -1]
dy = [1, -1, 0, 0]


def po(x: int, y: int) -> list:
    sel = []
    for i in range(4):
        cnt = 0
        (rx, ry) = (x, y)
        while cnt < 2:
            nx = rx + dx[i]
            ny = ry + dy[i]
            if not (0 <= nx < n and 0 <= ny < n):
                break
            if cnt == 1:
                sel.append((nx, ny))
            if data[nx][ny] == 1:
                cnt += 1
                if cnt == 2:
                    break
            (rx, ry) = (nx, ny)
    return sel


def go(cnt: int, sx: int, sy: int) -> None:
    global answer
    if cnt == 3:
        return

    sel = po(sx, sy)
    potential.update(sel)

    for row, col in sel:
        data[sx][sy] = 0
        temp = data[row][col]
        data[row][col] = 2
        go(cnt + 1, row, col)
        data[row][col] = temp
        data[sx][sy] = 2


for test in range(1, int(input()) + 1):
    n = int(input())
    data = [list(map(int, input().split())) for row in range(n)]
    (px, py) = (-1, -1)
    for row in range(n):
        for col in range(n):
            if data[row][col] == 2:
                px = row
                py = col
                break

    potential = set()
    go(0, px, py)
    answer = 0
    for row, col in potential:
        if data[row][col] == 1:
            answer += 1

    print('#{} {}'.format(test, answer))
