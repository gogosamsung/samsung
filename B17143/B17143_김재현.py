r, c, k = map(int, input().split())
sharks = []

dx = [-1, 1, 0, 0]
dy = [0, 0, 1, -1]

for _ in range(k):
    x, y, speed, dir, z = map(int, input().split())
    sharks.append([x - 1, y - 1, speed, dir - 1, z])

result = 0


def change_dir(dir: int) -> int:
    """방향을 바꿔주는 함수

    Args:
        dir (int): 기존 방향

    Returns:
        int: 원래 방향
    """
    if dir == 0:
        return 1
    elif dir == 1:
        return 0
    elif dir == 2:
        return 3
    elif dir == 3:
        return 2


def catch(col: int) -> None:
    """ 상어를 잡는 함수

    Args:
        col (int): 열 번호
    """
    for row in range(r):
        for shark in sharks:
            if (shark[0], shark[1]) == (row, col):
                global result
                result += shark[4]
                sharks.remove(shark)
                return


def move() -> None:
    """상어를 움직이는 함수
    """
    check = {}
    for shark in sharks:
        x, y, speed, dir, z = shark
        if dir == 2 or dir == 3:
            speed %= (c-1)*2
        if dir == 0 or dir == 1:
            speed %= (r-1)*2
        for _ in range(speed):
            if dir == 2 and y == c - 1:
                dir = change_dir(dir)
            if dir == 3 and y == 0:
                dir = change_dir(dir)
            if dir == 0 and x == 0:
                dir = change_dir(dir)
            if dir == 1 and x == r - 1:
                dir = change_dir(dir)

            nx, ny = x + dx[dir], y + dy[dir]
            x, y = nx, ny

        if (x, y) not in check:
            check[(x, y)] = [[x, y, speed, dir, z]]
        else:
            check[(x, y)].append([x, y, speed, dir, z])

    sharks.clear()
    for key, value in check.items():
        value.sort(key=lambda x: -x[4])
        sharks.append(value[0])


for col in range(c):
    catch(col)
    move()

print(result)
