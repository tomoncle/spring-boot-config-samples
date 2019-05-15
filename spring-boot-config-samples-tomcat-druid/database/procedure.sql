DROP PROCEDURE IF EXISTS `majorCount`;

DELIMITER $$

CREATE
PROCEDURE `springboot`.`majorCount`(OUT major_count INT)
    BEGIN
        SELECT COUNT(*) INTO major_count FROM t_major;
    END$$

DELIMITER ;
# https://www.cnblogs.com/pengzijun/p/6929949.html
#
# mysql> call majorCount(@major_count);
# Query OK, 1 row affected (0.02 sec)
#
# mysql> select @major_count;
# +--------------+
# | @major_count |
# +--------------+
# |            3 |
# +--------------+
# 1 row in set (0.00 sec)
#
# mysql> select @major_count as major_count;
# +-------------+
# | major_count |
# +-------------+
# |           3 |
# +-------------+
# 1 row in set (0.00 sec)