package org.jymf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jymf.dao.UserRecordMapper;
import org.jymf.entity.UserRecord;
import org.jymf.service.UserRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRecordServiceImpl implements UserRecordService{
	@Autowired
	private UserRecordMapper userrecordDao;

	@Override
	public void updateStateByLabelId(int status, String labelId) {
        Map<Object,Object> map = new HashMap<Object,Object>();
        map.put("status",status) ;
        map.put("labelid",labelId);
        userrecordDao.updateStatusByLabelId(map);
	}

	@Override
	public void insertUserRecord(UserRecord userRecord) {
		userrecordDao.insertUserRecord(userRecord);
	}

	@Override
	public int getStateByLabelId(String labelId) {
		return userrecordDao.getStatusByLabelId(labelId);
	}

	@Override
	public List<UserRecord> getRecordByUserId(String userid) {
		// TODO Auto-generated method stub
		return userrecordDao.getRecordsByUserId(userid);
	}
}
