def combia(index: int, honey: list) -> None:
    """
    worker1 을 조합을 재귀 적으로 구했을 때 값을 나타낸다.
    :param index:int
    :param honey:list
    :return:
    """
    if index <= len(honey):  # 더하고 있는데 값이 크면 백트랙킹
        temp = sum(a)
        if temp > c:
            return

    if index == len(honey):  # 수익 값을 더해준다.
        temp = sum(map(lambda x: x * x, a))
        ta.append(temp)
        return

    if index > len(honey):
        return

    # 조합을 구하는 방법
    a.append(honey[index])
    combia(index + 1, honey)
    a.pop()

    combia(index + 1, honey)


def combib(index: int, honey: list) -> None:
    """
    worker2를 조합을 재귀 적으로 구했을 때 값을 나타낸다.
    :param index:int
    :param honey:list
    :return:
    """
    if index <= len(honey):  # 더하고 있는데 값이 크면 백트랙킹
        temp = sum(b)
        if temp > c:
            return

    if index == len(honey):
        temp = sum(map(lambda x: x * x, b))
        tb.append(temp)
        return

    if index > len(honey):
        return

    # 조합을 구하는 방법
    b.append(honey[index])
    combib(index + 1, honey)
    b.pop()

    combib(index + 1, honey)


def calc(worker1: list, worker2: list) -> None:
    """
    일꾼 1하고 일꾼 2의 값을 구하고 전체적인 합을 나타낸다
    :param worker1:list
    :param worker2:list
    """
    global result
    combia(0, worker1)
    combib(0, worker2)
    # 조합을 통해서 C 이하의 꿀을 채취를 하여서 max 값을 계산
    result = max(result, max(ta) + max(tb))


for test in range(1, int(input()) + 1):
    n, m, c = map(int, input().split())
    data = [list(map(int, input().split())) for _ in range(n)]
    result = 0

    for row1 in range(n):
        for col1 in range(n):
            if col1 + m >= n-1:
                continue
            for row2 in range(n):
                for col2 in range(n - m + 1):
                    if row1 == row2 and (col1 <= col2 <= col1 + m - 1 or col1 <= col2 + m - 1 <= col1 + m - 1):
                        continue
                    ta, a = [], []
                    tb, b = [], []

                    worker1 = data[row1][col1:col1 + m]
                    worker2 = data[row2][col2:col2 + m]
                    calc(worker1, worker2)

    print('#{} {}'.format(test, result))
