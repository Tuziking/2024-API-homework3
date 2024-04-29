package com.homework.api3.Interceptor;

import com.homework.api3.Other.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class RequestCounterInterceptor implements HandlerInterceptor {
    private AtomicInteger QPS = new AtomicInteger(0);
    private Timer timer = new Timer();

    private static final int BUCKET_SIZE = 10;
    private long[] totalExecuteTime = new long[BUCKET_SIZE];
    private int[] requestCount = new int[BUCKET_SIZE];
    private CopyOnWriteArrayList<String> logList = new CopyOnWriteArrayList<>();

    public RequestCounterInterceptor() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                log.info("当前接口的QPS: {}条/秒", QPS.get());
                QPS.set(0);
                for (String item : logList){
                    log.info(item);
                }
                for (int i = 0; i < BUCKET_SIZE; i++) {
                    log.info("查询数目为{}~{}的平均响应时间: {}ms", i*100, (i+1) * 100, getAverageExecuteTime(i));
                }
                // reset totalExecuteTime and requestCount
                for (int i = 0; i < BUCKET_SIZE; i++) {
                    totalExecuteTime[i] = 0;
                    requestCount[i] = 0;
                }
                logList.clear();

            }
        }, 1000, 1000);
    }

    @Override
    public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (QPS.get() >= 10) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return false;
        }
        QPS.incrementAndGet();
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
        public  void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        long endTime = System.currentTimeMillis();
        long startTime = (Long) request.getAttribute("startTime");
        long executeTime = endTime - startTime;
        StringBuilder sb = new StringBuilder();
        LocalDateTime currentDateTime = LocalDateTime.now();
        // 添加当前时间
        sb.append("【").append(currentDateTime).append("】");
        if (response.getStatus() == HttpServletResponse.SC_OK){

            sb.append("响应状态 : ").append("成功").append("; ")
                    .append("返回数据").append(request.getParameter("num")).append("条; ")
                    .append("响应时间").append(executeTime).append("ms");

        } else {
            sb.append("接口的响应状态 : ").append("失败").append(";")
                    .append("响应时间").append(executeTime).append("ms");
        }

        logList.add(sb.toString());
//             update total execute time and request count
        int num = Integer.parseInt(request.getParameter("num"));
        int bucketIndex = num / 100;
        totalExecuteTime[bucketIndex] += executeTime;
        requestCount[bucketIndex]++;
    }

//    public int getRequestCountPerSecond() {
//        return QPS.get();
//    }

    public double getAverageExecuteTime(int bucketIndex) {
        if (requestCount[bucketIndex] == 0) {
            return 0;
        }
        double averageTime = (double) totalExecuteTime[bucketIndex] / requestCount[bucketIndex];
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(averageTime));
    }
}