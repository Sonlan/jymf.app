package org.jymf.service;


import java.util.List;

import org.jymf.entity.UserRecord;

public interface UserRecordService {

	void updateStateByLabelId(int status,String labelId);
    int getStateByLabelId(String labelId);
	void insertUserRecord(UserRecord userRecord);
	List<UserRecord> getRecordByUserId(String userid);

}
