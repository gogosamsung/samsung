n, m, x, y, k = map(int, input().split())
data = [list(map(int, input().split())) for _ in range(n)]
dice = [0] * 7

dx = [0, 0, -1, 1]
dy = [1, -1, 0, 0]

for command in list(map(int, input().split())):
    command -= 1
    nx, ny = x + dx[command], y + dy[command]
    if not (0 <= nx < n and 0 <= ny < m):
        continue

    if command == 0:
        dice[1], dice[4] = dice[4], dice[1]
        dice[4], dice[6] = dice[6], dice[4]
        dice[6], dice[3] = dice[3], dice[6]
    elif command == 1:
        dice[1], dice[3] = dice[3], dice[1]
        dice[3], dice[6] = dice[6], dice[3]
        dice[6], dice[4] = dice[4], dice[6]

    elif command == 2:
        dice[1], dice[5] = dice[5], dice[1]
        dice[5], dice[6] = dice[6], dice[5]
        dice[6], dice[2] = dice[2], dice[6]
    elif command == 3:
        dice[1], dice[2] = dice[2], dice[1]
        dice[2], dice[6] = dice[6], dice[2]
        dice[6], dice[5] = dice[5], dice[6]

    x, y = nx, ny

    if data[nx][ny] == 0:
        data[nx][ny] = dice[6]
    else:
        dice[6] = data[x][y]
        data[nx][ny] = 0

    print(dice[1])