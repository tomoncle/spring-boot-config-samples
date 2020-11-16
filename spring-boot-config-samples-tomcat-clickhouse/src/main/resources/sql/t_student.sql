CREATE TABLE IF NOT EXISTS default.t_student
(
    `user_id`    UInt64,
    `name`       String,
    `age`        Int32,
    `event_date` Date DEFAULT CAST(now(), 'Date')
)
    ENGINE = MergeTree(event_date, intHash32(user_id), 8192);