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

package com.tomoncle.app.web.grpc.test;

import com.tomoncle.app.web.grpc.services.UserService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author tomoncle
 */
public class GRPCServer {
    private static final Logger logger = LogManager.getLogger(GRPCServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 19090;
        final Server server = ServerBuilder.forPort(port)
                .addService(new UserService())
                .build()
                .start();

        Thread shutdownHook = new Thread(() -> {
            logger.warn("Server is graceful shutdown");
            server.shutdown();
            try {
                // Wait for RPCs to complete processing
                if (!server.awaitTermination(30, TimeUnit.SECONDS)) {
                    server.shutdownNow();
                    server.awaitTermination(5, TimeUnit.SECONDS);
                }
            } catch (InterruptedException ex) {
                server.shutdownNow();
            }
        });

        logger.info("Listening on port: " + port);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        server.awaitTermination();
    }

}
