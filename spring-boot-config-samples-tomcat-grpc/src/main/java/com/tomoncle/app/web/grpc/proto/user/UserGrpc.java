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

package com.tomoncle.app.web.grpc.proto.user;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 定义user服务
 * </pre>
 */
@javax.annotation.Generated(
        value = "by gRPC proto compiler (version 1.54.0)",
        comments = "Source: user/user.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class UserGrpc {

    public static final String SERVICE_NAME = "user.User";
    private static final int METHODID_SAY_HI = 0;
    // Static method descriptors that strictly reflect the proto.
    private static volatile io.grpc.MethodDescriptor<com.tomoncle.app.web.grpc.proto.user.Request,
            com.tomoncle.app.web.grpc.proto.user.Response> getSayHiMethod;
    private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

    private UserGrpc() {
    }

    @io.grpc.stub.annotations.RpcMethod(
            fullMethodName = SERVICE_NAME + '/' + "SayHi",
            requestType = com.tomoncle.app.web.grpc.proto.user.Request.class,
            responseType = com.tomoncle.app.web.grpc.proto.user.Response.class,
            methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
    public static io.grpc.MethodDescriptor<com.tomoncle.app.web.grpc.proto.user.Request,
            com.tomoncle.app.web.grpc.proto.user.Response> getSayHiMethod() {
        io.grpc.MethodDescriptor<com.tomoncle.app.web.grpc.proto.user.Request, com.tomoncle.app.web.grpc.proto.user.Response> getSayHiMethod;
        if ((getSayHiMethod = UserGrpc.getSayHiMethod) == null) {
            synchronized (UserGrpc.class) {
                if ((getSayHiMethod = UserGrpc.getSayHiMethod) == null) {
                    UserGrpc.getSayHiMethod = getSayHiMethod =
                            io.grpc.MethodDescriptor.<com.tomoncle.app.web.grpc.proto.user.Request, com.tomoncle.app.web.grpc.proto.user.Response>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SayHi"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            com.tomoncle.app.web.grpc.proto.user.Request.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            com.tomoncle.app.web.grpc.proto.user.Response.getDefaultInstance()))
                                    .setSchemaDescriptor(new UserMethodDescriptorSupplier("SayHi"))
                                    .build();
                }
            }
        }
        return getSayHiMethod;
    }

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static UserStub newStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<UserStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<UserStub>() {
                    @java.lang.Override
                    public UserStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new UserStub(channel, callOptions);
                    }
                };
        return UserStub.newStub(factory, channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static UserBlockingStub newBlockingStub(
            io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<UserBlockingStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<UserBlockingStub>() {
                    @java.lang.Override
                    public UserBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new UserBlockingStub(channel, callOptions);
                    }
                };
        return UserBlockingStub.newStub(factory, channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary calls on the service
     */
    public static UserFutureStub newFutureStub(
            io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<UserFutureStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<UserFutureStub>() {
                    @java.lang.Override
                    public UserFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new UserFutureStub(channel, callOptions);
                    }
                };
        return UserFutureStub.newStub(factory, channel);
    }

    public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
        return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                .addMethod(
                        getSayHiMethod(),
                        io.grpc.stub.ServerCalls.asyncUnaryCall(
                                new MethodHandlers<
                                        com.tomoncle.app.web.grpc.proto.user.Request,
                                        com.tomoncle.app.web.grpc.proto.user.Response>(
                                        service, METHODID_SAY_HI)))
                .build();
    }

    public static io.grpc.ServiceDescriptor getServiceDescriptor() {
        io.grpc.ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            synchronized (UserGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                            .setSchemaDescriptor(new UserFileDescriptorSupplier())
                            .addMethod(getSayHiMethod())
                            .build();
                }
            }
        }
        return result;
    }

    /**
     * <pre>
     * 定义user服务
     * </pre>
     */
    public interface AsyncService {

        /**
         * <pre>
         * 定义的方法入参和出参都为 对象
         * </pre>
         */
        default void sayHi(com.tomoncle.app.web.grpc.proto.user.Request request,
                           io.grpc.stub.StreamObserver<com.tomoncle.app.web.grpc.proto.user.Response> responseObserver) {
            io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSayHiMethod(), responseObserver);
        }
    }

    /**
     * Base class for the server implementation of the service User.
     * <pre>
     * 定义user服务
     * </pre>
     */
    public static abstract class UserImplBase
            implements io.grpc.BindableService, AsyncService {

        @java.lang.Override
        public final io.grpc.ServerServiceDefinition bindService() {
            return UserGrpc.bindService(this);
        }
    }

    /**
     * A stub to allow clients to do asynchronous rpc calls to service User.
     * <pre>
     * 定义user服务
     * </pre>
     */
    public static final class UserStub
            extends io.grpc.stub.AbstractAsyncStub<UserStub> {
        private UserStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected UserStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new UserStub(channel, callOptions);
        }

        /**
         * <pre>
         * 定义的方法入参和出参都为 对象
         * </pre>
         */
        public void sayHi(com.tomoncle.app.web.grpc.proto.user.Request request,
                          io.grpc.stub.StreamObserver<com.tomoncle.app.web.grpc.proto.user.Response> responseObserver) {
            io.grpc.stub.ClientCalls.asyncUnaryCall(
                    getChannel().newCall(getSayHiMethod(), getCallOptions()), request, responseObserver);
        }
    }

    /**
     * A stub to allow clients to do synchronous rpc calls to service User.
     * <pre>
     * 定义user服务
     * </pre>
     */
    public static final class UserBlockingStub
            extends io.grpc.stub.AbstractBlockingStub<UserBlockingStub> {
        private UserBlockingStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected UserBlockingStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new UserBlockingStub(channel, callOptions);
        }

        /**
         * <pre>
         * 定义的方法入参和出参都为 对象
         * </pre>
         */
        public com.tomoncle.app.web.grpc.proto.user.Response sayHi(com.tomoncle.app.web.grpc.proto.user.Request request) {
            return io.grpc.stub.ClientCalls.blockingUnaryCall(
                    getChannel(), getSayHiMethod(), getCallOptions(), request);
        }
    }

    /**
     * A stub to allow clients to do ListenableFuture-style rpc calls to service User.
     * <pre>
     * 定义user服务
     * </pre>
     */
    public static final class UserFutureStub
            extends io.grpc.stub.AbstractFutureStub<UserFutureStub> {
        private UserFutureStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected UserFutureStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new UserFutureStub(channel, callOptions);
        }

        /**
         * <pre>
         * 定义的方法入参和出参都为 对象
         * </pre>
         */
        public com.google.common.util.concurrent.ListenableFuture<com.tomoncle.app.web.grpc.proto.user.Response> sayHi(
                com.tomoncle.app.web.grpc.proto.user.Request request) {
            return io.grpc.stub.ClientCalls.futureUnaryCall(
                    getChannel().newCall(getSayHiMethod(), getCallOptions()), request);
        }
    }

    private static final class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final AsyncService serviceImpl;
        private final int methodId;

        MethodHandlers(AsyncService serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_SAY_HI:
                    serviceImpl.sayHi((com.tomoncle.app.web.grpc.proto.user.Request) request,
                            (io.grpc.stub.StreamObserver<com.tomoncle.app.web.grpc.proto.user.Response>) responseObserver);
                    break;
                default:
                    throw new AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(
                io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new AssertionError();
            }
        }
    }

    private static abstract class UserBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
        UserBaseDescriptorSupplier() {
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
            return com.tomoncle.app.web.grpc.proto.user.UserProto.getDescriptor();
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
            return getFileDescriptor().findServiceByName("User");
        }
    }

    private static final class UserFileDescriptorSupplier
            extends UserBaseDescriptorSupplier {
        UserFileDescriptorSupplier() {
        }
    }

    private static final class UserMethodDescriptorSupplier
            extends UserBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
        private final String methodName;

        UserMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
            return getServiceDescriptor().findMethodByName(methodName);
        }
    }
}
