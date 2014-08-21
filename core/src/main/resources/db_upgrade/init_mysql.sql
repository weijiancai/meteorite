DELIMITER $$

DROP PROCEDURE IF EXISTS del_idx $$
CREATE PROCEDURE del_idx(IN p_tableName VARCHAR(200), IN p_idxName VARCHAR(200))
  BEGIN
    DECLARE str VARCHAR(250);
    SET @str = concat(' drop index ', p_idxName, ' on ', p_tableName);

    SELECT
      count(*)
    INTO @cnt
    FROM information_schema.statistics
    WHERE table_schema = SCHEMA() and table_name = p_tableName AND index_name = p_idxName;
    IF @cnt > 0
    THEN
      PREPARE stmt FROM @str;
      EXECUTE stmt;
    END IF;

  END $$

DELIMITER ;