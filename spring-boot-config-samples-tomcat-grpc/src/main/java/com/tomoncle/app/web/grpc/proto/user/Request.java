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

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: user/user.proto

package com.tomoncle.app.web.grpc.proto.user;

/**
 * <pre>
 * 结构体 定义格式：message Xxx{}
 * </pre>
 * <p>
 * Protobuf type {@code user.Request}
 */
public final class Request extends
        com.google.protobuf.GeneratedMessageV3 implements
        // @@protoc_insertion_point(message_implements:user.Request)
        RequestOrBuilder {
    public static final int NAME_FIELD_NUMBER = 1;
    private static final long serialVersionUID = 0L;
    // @@protoc_insertion_point(class_scope:user.Request)
    private static final com.tomoncle.app.web.grpc.proto.user.Request DEFAULT_INSTANCE;
    private static final com.google.protobuf.Parser<Request>
            PARSER = new com.google.protobuf.AbstractParser<Request>() {
        @java.lang.Override
        public Request parsePartialFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            Builder builder = newBuilder();
            try {
                builder.mergeFrom(input, extensionRegistry);
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(builder.buildPartial());
            } catch (com.google.protobuf.UninitializedMessageException e) {
                throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(e)
                        .setUnfinishedMessage(builder.buildPartial());
            }
            return builder.buildPartial();
        }
    };

    static {
        DEFAULT_INSTANCE = new com.tomoncle.app.web.grpc.proto.user.Request();
    }

    @SuppressWarnings("serial")
    private volatile java.lang.Object name_ = "";
    private byte memoizedIsInitialized = -1;

    // Use Request.newBuilder() to construct.
    private Request(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Request() {
        name_ = "";
    }

    public static final com.google.protobuf.Descriptors.Descriptor
    getDescriptor() {
        return com.tomoncle.app.web.grpc.proto.user.UserProto.internal_static_user_Request_descriptor;
    }

    public static com.tomoncle.app.web.grpc.proto.user.Request parseFrom(
            java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static com.tomoncle.app.web.grpc.proto.user.Request parseFrom(
            java.nio.ByteBuffer data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.tomoncle.app.web.grpc.proto.user.Request parseFrom(
            com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static com.tomoncle.app.web.grpc.proto.user.Request parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.tomoncle.app.web.grpc.proto.user.Request parseFrom(byte[] data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static com.tomoncle.app.web.grpc.proto.user.Request parseFrom(
            byte[] data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static com.tomoncle.app.web.grpc.proto.user.Request parseFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }

    public static com.tomoncle.app.web.grpc.proto.user.Request parseFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static com.tomoncle.app.web.grpc.proto.user.Request parseDelimitedFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input);
    }

    public static com.tomoncle.app.web.grpc.proto.user.Request parseDelimitedFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static com.tomoncle.app.web.grpc.proto.user.Request parseFrom(
            com.google.protobuf.CodedInputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }

    public static com.tomoncle.app.web.grpc.proto.user.Request parseFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(com.tomoncle.app.web.grpc.proto.user.Request prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    public static com.tomoncle.app.web.grpc.proto.user.Request getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static com.google.protobuf.Parser<Request> parser() {
        return PARSER;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
            UnusedPrivateParameter unused) {
        return new Request();
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
    internalGetFieldAccessorTable() {
        return com.tomoncle.app.web.grpc.proto.user.UserProto.internal_static_user_Request_fieldAccessorTable
                .ensureFieldAccessorsInitialized(
                        com.tomoncle.app.web.grpc.proto.user.Request.class, com.tomoncle.app.web.grpc.proto.user.Request.Builder.class);
    }

    /**
     * <pre>
     * 第一个参数 name,  = 1 表示第一个参数位置
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @return The name.
     */
    @java.lang.Override
    public java.lang.String getName() {
        java.lang.Object ref = name_;
        if (ref instanceof java.lang.String) {
            return (java.lang.String) ref;
        } else {
            com.google.protobuf.ByteString bs =
                    (com.google.protobuf.ByteString) ref;
            java.lang.String s = bs.toStringUtf8();
            name_ = s;
            return s;
        }
    }

    /**
     * <pre>
     * 第一个参数 name,  = 1 表示第一个参数位置
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @return The bytes for name.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
    getNameBytes() {
        java.lang.Object ref = name_;
        if (ref instanceof java.lang.String) {
            com.google.protobuf.ByteString b =
                    com.google.protobuf.ByteString.copyFromUtf8(
                            (java.lang.String) ref);
            name_ = b;
            return b;
        } else {
            return (com.google.protobuf.ByteString) ref;
        }
    }

    @java.lang.Override
    public final boolean isInitialized() {
        byte isInitialized = memoizedIsInitialized;
        if (isInitialized == 1) return true;
        if (isInitialized == 0) return false;

        memoizedIsInitialized = 1;
        return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
            throws java.io.IOException {
        if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(name_)) {
            com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
        }
        getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
        int size = memoizedSize;
        if (size != -1) return size;

        size = 0;
        if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(name_)) {
            size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
        }
        size += getUnknownFields().getSerializedSize();
        memoizedSize = size;
        return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.tomoncle.app.web.grpc.proto.user.Request)) {
            return super.equals(obj);
        }
        com.tomoncle.app.web.grpc.proto.user.Request other = (com.tomoncle.app.web.grpc.proto.user.Request) obj;

        if (!getName()
                .equals(other.getName())) return false;
        if (!getUnknownFields().equals(other.getUnknownFields())) return false;
        return true;
    }

    @java.lang.Override
    public int hashCode() {
        if (memoizedHashCode != 0) {
            return memoizedHashCode;
        }
        int hash = 41;
        hash = (19 * hash) + getDescriptor().hashCode();
        hash = (37 * hash) + NAME_FIELD_NUMBER;
        hash = (53 * hash) + getName().hashCode();
        hash = (29 * hash) + getUnknownFields().hashCode();
        memoizedHashCode = hash;
        return hash;
    }

    @java.lang.Override
    public Builder newBuilderForType() {
        return newBuilder();
    }

    @java.lang.Override
    public Builder toBuilder() {
        return this == DEFAULT_INSTANCE
                ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
            com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        Builder builder = new Builder(parent);
        return builder;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Request> getParserForType() {
        return PARSER;
    }

    @java.lang.Override
    public com.tomoncle.app.web.grpc.proto.user.Request getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

    /**
     * <pre>
     * 结构体 定义格式：message Xxx{}
     * </pre>
     * <p>
     * Protobuf type {@code user.Request}
     */
    public static final class Builder extends
            com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
            // @@protoc_insertion_point(builder_implements:user.Request)
            com.tomoncle.app.web.grpc.proto.user.RequestOrBuilder {
        private int bitField0_;
        private java.lang.Object name_ = "";

        // Construct using com.tomoncle.app.web.grpc.proto.user.Request.newBuilder()
        private Builder() {

        }

        private Builder(
                com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
            super(parent);

        }

        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return com.tomoncle.app.web.grpc.proto.user.UserProto.internal_static_user_Request_descriptor;
        }

        @java.lang.Override
        protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return com.tomoncle.app.web.grpc.proto.user.UserProto.internal_static_user_Request_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            com.tomoncle.app.web.grpc.proto.user.Request.class, com.tomoncle.app.web.grpc.proto.user.Request.Builder.class);
        }

        @java.lang.Override
        public Builder clear() {
            super.clear();
            bitField0_ = 0;
            name_ = "";
            return this;
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
            return com.tomoncle.app.web.grpc.proto.user.UserProto.internal_static_user_Request_descriptor;
        }

        @java.lang.Override
        public com.tomoncle.app.web.grpc.proto.user.Request getDefaultInstanceForType() {
            return com.tomoncle.app.web.grpc.proto.user.Request.getDefaultInstance();
        }

        @java.lang.Override
        public com.tomoncle.app.web.grpc.proto.user.Request build() {
            com.tomoncle.app.web.grpc.proto.user.Request result = buildPartial();
            if (!result.isInitialized()) {
                throw newUninitializedMessageException(result);
            }
            return result;
        }

        @java.lang.Override
        public com.tomoncle.app.web.grpc.proto.user.Request buildPartial() {
            com.tomoncle.app.web.grpc.proto.user.Request result = new com.tomoncle.app.web.grpc.proto.user.Request(this);
            if (bitField0_ != 0) {
                buildPartial0(result);
            }
            onBuilt();
            return result;
        }

        private void buildPartial0(com.tomoncle.app.web.grpc.proto.user.Request result) {
            int from_bitField0_ = bitField0_;
            if (((from_bitField0_ & 0x00000001) != 0)) {
                result.name_ = name_;
            }
        }

        @java.lang.Override
        public Builder mergeFrom(com.google.protobuf.Message other) {
            if (other instanceof com.tomoncle.app.web.grpc.proto.user.Request) {
                return mergeFrom((com.tomoncle.app.web.grpc.proto.user.Request) other);
            } else {
                super.mergeFrom(other);
                return this;
            }
        }

        public Builder mergeFrom(com.tomoncle.app.web.grpc.proto.user.Request other) {
            if (other == com.tomoncle.app.web.grpc.proto.user.Request.getDefaultInstance()) return this;
            if (!other.getName().isEmpty()) {
                name_ = other.name_;
                bitField0_ |= 0x00000001;
                onChanged();
            }
            this.mergeUnknownFields(other.getUnknownFields());
            onChanged();
            return this;
        }

        @java.lang.Override
        public final boolean isInitialized() {
            return true;
        }

        @java.lang.Override
        public Builder mergeFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            if (extensionRegistry == null) {
                throw new java.lang.NullPointerException();
            }
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10: {
                            name_ = input.readStringRequireUtf8();
                            bitField0_ |= 0x00000001;
                            break;
                        } // case 10
                        default: {
                            if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                                done = true; // was an endgroup tag
                            }
                            break;
                        } // default:
                    } // switch (tag)
                } // while (!done)
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.unwrapIOException();
            } finally {
                onChanged();
            } // finally
            return this;
        }

        /**
         * <pre>
         * 第一个参数 name,  = 1 表示第一个参数位置
         * </pre>
         *
         * <code>string name = 1;</code>
         *
         * @return The name.
         */
        public java.lang.String getName() {
            java.lang.Object ref = name_;
            if (!(ref instanceof java.lang.String)) {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                java.lang.String s = bs.toStringUtf8();
                name_ = s;
                return s;
            } else {
                return (java.lang.String) ref;
            }
        }

        /**
         * <pre>
         * 第一个参数 name,  = 1 表示第一个参数位置
         * </pre>
         *
         * <code>string name = 1;</code>
         *
         * @param value The name to set.
         * @return This builder for chaining.
         */
        public Builder setName(
                java.lang.String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            name_ = value;
            bitField0_ |= 0x00000001;
            onChanged();
            return this;
        }

        /**
         * <pre>
         * 第一个参数 name,  = 1 表示第一个参数位置
         * </pre>
         *
         * <code>string name = 1;</code>
         *
         * @return The bytes for name.
         */
        public com.google.protobuf.ByteString
        getNameBytes() {
            java.lang.Object ref = name_;
            if (ref instanceof String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (java.lang.String) ref);
                name_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        /**
         * <pre>
         * 第一个参数 name,  = 1 表示第一个参数位置
         * </pre>
         *
         * <code>string name = 1;</code>
         *
         * @param value The bytes for name to set.
         * @return This builder for chaining.
         */
        public Builder setNameBytes(
                com.google.protobuf.ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }
            checkByteStringIsUtf8(value);
            name_ = value;
            bitField0_ |= 0x00000001;
            onChanged();
            return this;
        }

        /**
         * <pre>
         * 第一个参数 name,  = 1 表示第一个参数位置
         * </pre>
         *
         * <code>string name = 1;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearName() {
            name_ = getDefaultInstance().getName();
            bitField0_ = (bitField0_ & ~0x00000001);
            onChanged();
            return this;
        }

        @java.lang.Override
        public final Builder setUnknownFields(
                final com.google.protobuf.UnknownFieldSet unknownFields) {
            return super.setUnknownFields(unknownFields);
        }

        @java.lang.Override
        public final Builder mergeUnknownFields(
                final com.google.protobuf.UnknownFieldSet unknownFields) {
            return super.mergeUnknownFields(unknownFields);
        }


        // @@protoc_insertion_point(builder_scope:user.Request)
    }

}

