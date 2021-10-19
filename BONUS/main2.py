def injeop(sx: tuple, sy: tuple) -> bool:
    i1, v1 = sx
    i2, v2 = sy
    if i1 > i2:
        i1, i2 = i2, i1

    return False if abs(i1 - i2) == 1 or abs(i1 - i2) >= (len(data) - 1) else True


def cross(a: tuple, b: tuple, c: tuple, d: tuple) -> bool:
    i1, v1 = a
    i2, v2 = b
    i3, v3 = c
    i4, v4 = d
    if i1 > i2:
        i1, i2 = i2, i1
    if i3 > i4:
        i3, i4 = i4, i3

    if i1 < i3 < i2 < i4:
        return False
    if i3 < i1 < i4 < i2:
        return False
    return True


def check(a: tuple, b: tuple, c: tuple, d: tuple) -> list:
    flag = []
    # a, b // c, d
    if cross(a, b, c, d) and injeop(a, b) and injeop(c, d) and injeop(a, c) and injeop(a, d) and injeop(b,
                                                                                                        c) and injeop(
            b, d):
        flag.append(1)
    # a. c // b, d
    if cross(a, c, b, d) and injeop(a, c) and injeop(b, d) and injeop(a, b) and injeop(a, d) and injeop(c,
                                                                                                        b) and injeop(
            c, d):
        flag.append(2)
    # a, d // b, c
    if cross(a, d, b, c) and injeop(a, d) and injeop(b, c) and injeop(a, b) and injeop(a, c) and injeop(d,
                                                                                                        b) and injeop(
            d, c):
        flag.append(3)

    return flag


def calc(a: int, b: int, c: int, d: int, flags: list) -> list:
    results = []
    for sel in flags:
        if sel == 1:
            results.append((a[1] + b[1]) ** 2 + (c[1] + d[1]) ** 2)
        elif sel == 2:
            results.append((a[1] + c[1]) ** 2 + (b[1] + d[1]) ** 2)
        else:
            results.append((a[1] + d[1]) ** 2 + (b[1] + c[1]) ** 2)
    return results


line = []


def go(index: int, cnt: int):
    global answer
    if cnt == 4:
        a = data[line[0]]
        b = data[line[1]]
        c = data[line[2]]
        d = data[line[3]]
        flags = check(a, b, c, d)
        if flags:
            re = calc(a, b, c, d, flags)
            if re:
                answer = max(answer, max(re))
        return
    if index >= len(data) or cnt >= 4:
        return
    line.append(index)
    go(index + 1, cnt + 1)
    line.pop()

    go(index + 1, cnt)


for test in range(1, int(input()) + 1):
    n = int(input())
    data = list(map(int, input().split()))
    for i in range(len(data)):
        data[i] = (i, data[i])

    answer = 0
    go(0, 0)

    print('#{} {}'.format(test, answer))
