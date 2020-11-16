CREATE TABLE default.t_flow
(

    `event_date`               Date   DEFAULT CAST(now(), 'Date'),

    `uuid`                     String DEFAULT generateUUIDv4(),

    `create_date`              UInt64,

    `create_time_hour`         Int64,

    `create_time_min`          Int64,

    `create_time_sec`          Int64,

    `value`                    Float64,

    `IN_BYTES`                 Float64,

    `IN_PKTS`                  Float64,

    `PROTOCOL`                 Float64,

    `PROTOCOL_MAP`             String,

    `TCP_FLAGS`                Float64,

    `L4_SRC_PORT`              Float64,

    `IPV4_SRC_ADDR`            String,

    `L4_DST_PORT`              Float64,

    `L4_SRV_PORT`              String,

    `IPV4_DST_ADDR`            String,

    `OUTPUT_SNMP`              String,

    `IPV4_NEXT_HOP`            String,

    `LAST_SWITCHED`            Float64,

    `FIRST_SWITCHED`           Float64,

    `OUT_BYTES`                Float64,

    `OUT_PKTS`                 Float64,

    `IPV6_SRC_ADDR`            String,

    `IPV6_DST_ADDR`            String,

    `ICMP_TYPE`                Float64,

    `IN_SRC_MAC`               String,

    `OUT_DST_MAC`              String,

    `SRC_VLAN`                 Float64,

    `DST_VLAN`                 Float64,

    `IP_PROTOCOL_VERSION`      String,

    `FLOW_ID`                  Float64,

    `CLIENT_NW_LATENCY_MS`     Float64,

    `SERVER_NW_LATENCY_MS`     Float64,

    `SRC_IP_COUNTRY`           String,

    `SRC_IP_CITY`              String,

    `DST_IP_COUNTRY`           String,

    `DST_IP_CITY`              String,

    `SRC_IP_LONG`              String,

    `SRC_IP_LAT`               String,

    `DST_IP_LONG`              String,

    `DST_IP_LAT`               String,

    `RETRANSMITTED_IN_PKTS`    Float64,

    `RETRANSMITTED_OUT_BYTES`  Float64,

    `RETRANSMITTED_OUT_PKTS`   Float64,

    `OOORDER_IN_PKTS`          Float64,

    `OOORDER_OUT_PKTS`         Float64,

    `L7_PROTO`                 Float64,

    `L7_PROTO_NAME`            String,

    `SSL_SERVER_NAME`          String,

    `BITTORRENT_HASH`          String,

    `DNS_QUERY`                String,

    `DNS_QUERY_TYPE`           Float64,

    `DNS_RET_CODE`             String,

    `DNS_NUM_ANSWERS`          Float64,

    `DNS_RESPONSE`             Float64,

    `HTTP_URL`                 String,

    `HTTP_METHOD`              String,

    `HTTP_REFERER`             String,

    `HTTP_UA`                  String,

    `HTTP_MIME`                String,

    `HTTP_HOST`                String,

    `HTTP_SITE`                String,

    `FLOW_TIMESTAMP`           Float64,

    `IN_SRC_OBJECT`            String,

    `OUT_DST_OBJECT`           String,

    `IN_PKTLOST`               Float64,

    `OUT_PKTLOST`              Float64,

    `AGENT_HOST`               String,

    `AGENT_INTERFACE`          String,

    `SENT_PKTS_UPTO64`         Float64,

    `SENT_PKTS_UPTO128`        Float64,

    `SENT_PKTS_UPTO256`        Float64,

    `SENT_PKTS_UPTO512`        Float64,

    `SENT_PKTS_UPTO1024`       Float64,

    `SENT_PKTS_UPTO1518`       Float64,

    `SENT_PKTS_UPTO2500`       Float64,

    `SENT_PKTS_UPTO6500`       Float64,

    `SENT_PKTS_UPTO9000`       Float64,

    `SENT_PKTS_ABOVE9000`      Float64,

    `SENT_PKTS_SYN`            Float64,

    `SENT_PKTS_SYNACK`         Float64,

    `SENT_PKTS_FINACK`         Float64,

    `SENT_PKTS_RST`            Float64,

    `RECV_PKTS_UPTO64`         Float64,

    `RECV_PKTS_UPTO128`        Float64,

    `RECV_PKTS_UPTO256`        Float64,

    `RECV_PKTS_UPTO512`        Float64,

    `RECV_PKTS_UPTO1024`       Float64,

    `RECV_PKTS_UPTO1518`       Float64,

    `RECV_PKTS_UPTO2500`       Float64,

    `RECV_PKTS_UPTO6500`       Float64,

    `RECV_PKTS_UPTO9000`       Float64,

    `RECV_PKTS_ABOVE9000`      Float64,

    `RECV_PKTS_SYN`            Float64,

    `RECV_PKTS_SYNACK`         Float64,

    `RECV_PKTS_FINACK`         Float64,

    `RECV_PKTS_RST`            Float64,

    `ICMP_CODE`                Float64,

    `ICMP_ECHO_ID`             Float64,

    `HTTP_RET_CODE`            Float64,

    `HTTP_REQUEST_VERSION`     Float64,

    `HTTP_REQUEST_HEADERS`     Float64,

    `HTTP_RESPONSE_HEADERS`    Float64,

    `SSH_CLIENT_SIGNATURE`     String,

    `SSH_SERVER_SIGNATURE`     String,

    `SSL_CLIENT_CERTIFICATE`   String,

    `SSL_SERVER_CERTIFICATE`   String,

    `SSL_SERVER_ORGANIZATION`  String,

    `SSL_HS_PACKETS`           Float64,

    `SSL_CLIENT_HELLO_TIME`    Float64,

    `SSL_HS_END_TIME`          Float64,

    `SSL_LASTDATA_TIME`        Float64,

    `SSL_VERSION`              Float64,

    `SSL_SERVER_CIPHER`        Float64,

    `SSL_NUM_UDP_PKTS`         String,

    `SSL_NUM_PROCESSED_PKTS`   String,

    `SSL_NUM_BINDING_REQUESTS` String,

    `DNS_NUM_QUERIES`          Float64,

    `DNS_QUERY_CLASS`          Float64,

    `DNS_RSP_ADDR`             String
)
    ENGINE = MergeTree(event_date,
             (uuid,
              create_date,
              create_time_hour,
              create_time_min,
              create_time_sec),
             8192)

