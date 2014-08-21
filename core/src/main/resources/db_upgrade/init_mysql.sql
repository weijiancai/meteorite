DELIMITER $$

DROP PROCEDURE IF EXISTS del_idx;
CREATE PROCEDURE del_idx(IN p_tablename VARCHAR(200), IN p_idxname VARCHAR(200))
  BEGIN
    DECLARE str VARCHAR(250);
    SET @str = concat(' drop index ', p_idxname, ' on ', p_tablename);

    SELECT
      count(*)
    INTO @cnt
    FROM information_schema.statistics
    WHERE table_name = p_tablename AND index_name = p_idxname;
    IF @cnt > 0
    THEN
      PREPARE stmt FROM @str;
      EXECUTE stmt;
    END IF;

  END;$$

DELIMITER ;