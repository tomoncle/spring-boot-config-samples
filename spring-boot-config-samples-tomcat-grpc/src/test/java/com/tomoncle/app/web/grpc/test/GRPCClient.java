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

import com.tomoncle.app.web.grpc.proto.user.Request;
import com.tomoncle.app.web.grpc.proto.user.Response;
import com.tomoncle.app.web.grpc.proto.user.UserGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.concurrent.TimeUnit;


/**
 * @author tomoncle
 */
public class GRPCClient {
    private static final Logger logger = LogManager.getLogger(GRPCClient.class);

    @Test
    public void testUserGrpc() throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 19090).usePlaintext().build();
        UserGrpc.UserBlockingStub stub = UserGrpc.newBlockingStub(channel);

        String[] names = new String[]{"Golang", "Python", "Java", "Rust", "Shell"};
        for (String name : names) {
            Request request = Request.newBuilder().setName(name).build();
            Response response = stub.sayHi(request);
            logger.info(response);
        }
        channel.awaitTermination(5, TimeUnit.SECONDS);
    }

}
