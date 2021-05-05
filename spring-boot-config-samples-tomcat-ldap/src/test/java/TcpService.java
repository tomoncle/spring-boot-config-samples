/*
 * Copyright 2018 tomoncle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * >javac TcpService.java
 * >java TcpService 6996
 *
 * @author tomoncle
 */
@SuppressWarnings("InfiniteLoopStatement")
public class TcpService {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter the bound port, value type int! Port rang (1~65535)");
            System.exit(0);
        }
        int port = Integer.valueOf(args[0]);
        if (port > 65535 || port < 1) {
            System.out.println("Please enter the bound port, value type int! Port rang (1~65535)");
            System.exit(0);
        }
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            Socket socket = serverSocket.accept();
            while (true) {
                try {
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("ok".getBytes());
                } catch (java.net.SocketException e) {
                    socket = serverSocket.accept();
                }
            }
        } catch (IOException ex) {
            System.out.println("Server  : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
