from concurrent.futures import ThreadPoolExecutor
import random
import time
import requests
import matplotlib.pyplot as plt
def send_request(url, delay):
    try:
        start_time = time.time()
        response = requests.get(url)
        end_time = time.time()
        time.sleep(delay)  # 暂停一段时间
        if response.status_code != 200:
            raise requests.exceptions.HTTPError(f'HTTP {response.status_code}: {response.reason}')
        return response.status_code, end_time - start_time
    except requests.exceptions.RequestException as e:
        end_time = time.time()
        return e, end_time - start_time

def batch_curl(urls, rate):
    delay = 1.0 / rate  # 计算每个请求之间的延迟
    success_times = []
    fail_times = []
    with ThreadPoolExecutor(max_workers=100) as executor:
        # 将参数打包成元组
        results = executor.map(send_request, urls, [delay]*len(urls))
    for i, result in enumerate(results):
        if isinstance(result[0], requests.exceptions.RequestException):
            print(f"Request failed with error: {result[0]}")
            fail_times.append((i, result[1]))
        else:
            print(f"Request succeeded with status code: {result[0]}")
            success_times.append((i, result[1]))
    # 计算成功率
    success_rate = len(success_times) / (len(success_times) + len(fail_times))
    # 绘制响应时间的条形图
    if success_times:
        plt.bar(*zip(*success_times), color='green', label='Success')
    if fail_times:
        plt.bar(*zip(*fail_times), color='red', label='Fail')
    plt.legend(loc='upper right')
    plt.xlabel('Request Number')
    plt.ylabel('Response Time (s)')
    # 在图中添加成功率
    plt.text(0.5, 0.5, f'Success Rate: {success_rate*100:.2f}%', horizontalalignment='center', verticalalignment='center', transform=plt.gca().transAxes)
    plt.show()

# 每秒的请求数量      
qps = 20
url = "http://localhost:8888/students"
urls = []
# 使用示例
for i in range(qps):
    num = 999
    urls.append(url + "?num=" + str(num))

batch_curl(urls, qps)
