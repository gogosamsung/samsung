r, c, k = map(int, input().split())
r -= 1
c -= 1

data = [[0] * 100 for _ in range(100)]


def check(row: int, col: int, k: int) -> bool:
    return False if data[row][col] == k else True

def calc() -> int:
    time, x, y = 0, 3, 3
    while check(r, c, k):
        time += 1
        if time > 100:
            return -1
        if x >= y:
            # R 연산
            max_len = 0
            for row in range(x):
                counter = {}
                for col in range(y):
                    if data[row][col] != 0:
                        counter[data[row][col]] = counter.get(data[row][col], 0) + 1
                        data[row][col] = 0

                v = [(key, value) for key, value in counter.items()]
                index = 0
                for ele1, ele2 in sorted(v, key=lambda x: (x[1], x[0])):
                    data[row][index] = ele1
                    index += 1
                    data[row][index] = ele2
                    index += 1
                max_len = max(max_len, index)
            y = max_len

        else:
            max_len = 0
            for col in range(y):
                counter = {}
                for row in range(x):
                    if data[row][col] != 0:
                        counter[data[row][col]] = counter.get(data[row][col], 0) + 1
                        data[row][col] = 0

                v = [(key, value) for key, value in counter.items()]
                index = 0

                for ele1, ele2 in sorted(v, key=lambda x: (x[1], x[0])):
                    data[index][col] = ele1
                    index += 1
                    data[index][col] = ele2
                    index += 1
                max_len = max(max_len, index)
            x = max_len

    else:
        return time


for i in range(3):
    temp = list(map(int, input().split()))
    for j in range(3):
        data[i][j] = temp[j]

print(calc())