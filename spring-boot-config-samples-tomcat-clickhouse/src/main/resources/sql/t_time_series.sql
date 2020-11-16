DROP table IF EXISTS default.t_time_series;

CREATE TABLE IF NOT EXISTS default.t_time_series
(
    `event_date`       Date    DEFAULT CAST(now(), 'Date'),
    `uuid`             String  DEFAULT generateUUIDv4(),
-- 时间戳精确到day
    `create_date`      UInt64  DEFAULT toUInt64(toStartOfDay(now(), 'Asia/Hong_Kong')),
-- 时间戳精确到小时
    `create_time_hour` Int64   DEFAULT toInt64(toStartOfHour(now(), 'Asia/Hong_Kong')),
-- 时间戳精确到分钟
    `create_time_min`  Int64   DEFAULT toInt64(toStartOfMinute(now(), 'Asia/Hong_Kong')),
-- 时间戳精确到秒
    `create_time_sec`  Int64   DEFAULT toInt64(now()),
    `value`            Float64 DEFAULT 0
) ENGINE = MergeTree(event_date, (uuid, create_date, create_time_hour, create_time_min, create_time_sec), 8192);

insert into default.t_time_series (value)
values (6);