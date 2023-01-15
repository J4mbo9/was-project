package org.example;

import org.example.calculator.Calculator;
import org.example.calculator.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CustomApplicationServer {

    private final int port;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final Logger logger = LoggerFactory.getLogger(CustomApplicationServer.class);

    public CustomApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer ] started {} port.", port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServer ] waiting for client.");

            while ((clientSocket = serverSocket.accept()) != null) {
                logger.info("[CustomWebApplicationServer ] client connected!");

                /**
                 *  Step2 - 사용자 요청이 들어올 때마다 Thread를 새로 생성해서 사용자 요청을 처리하도록 한다.
                 *  ㄴ 쓰레드풀 개념 도입 : 일정 갯수 미리 만들어놈
                 */
                new Thread(new ClientRequestHandler(clientSocket)).start();

                /**
                 * Step3 - 쓰레드풀 적용하여 안정적인 서비스 가능하도록 함
                 */
                executorService.execute(new ClientRequestHandler(clientSocket));

            }
        }
    }
}

