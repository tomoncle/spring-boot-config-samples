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

package com.tomoncle.app.web.grpc.services;

import com.tomoncle.app.web.grpc.proto.user.Request;
import com.tomoncle.app.web.grpc.proto.user.Response;
import com.tomoncle.app.web.grpc.proto.user.UserGrpc;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author tomoncle
 */
public class UserService extends UserGrpc.UserImplBase {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Override
    public void sayHi(Request request, StreamObserver<Response> responseObserver) {
        String name = request.getName();
        logger.info("get name by request: " + name);
        Response response = Response.newBuilder()
                .setCode(200)
                .setData("Java GRPC UserService: Hello, " + name)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
