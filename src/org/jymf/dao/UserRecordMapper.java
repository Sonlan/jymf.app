package org.jymf.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.jymf.entity.UserRecord;

/**
 * 用户查询记录
 * @author Administrator
 *
 */
public interface UserRecordMapper {
     
	/**
	 * 根据追溯标签更新status
	 */
	void updateStatusByLabelId(Map<Object,Object> map);
    int getStatusByLabelId(@Param("labelid") String labelId);
	void insertUserRecord(UserRecord userRecord);
	List<UserRecord> getRecordsByUserId(@Param("userid") String userid);
}
