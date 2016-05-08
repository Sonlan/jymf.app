package org.jymf.service.impl;

import org.jymf.dao.ThirdUserMapper;
import org.jymf.entity.ThirdUser;
import org.jymf.service.IThirdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("ThirdUserServ")
public class ThirdUserService implements IThirdUserService {
	@Autowired
	ThirdUserMapper mapper;
	/**
	 * 插入第三方用户信息到数据库
	 * 成功返回true
	 */
	@Override
	public boolean  insertUser(ThirdUser user) {
		// TODO Auto-generated method stub
		try {
			mapper.insert(user);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	@Override
	public ThirdUser getUserInfoByOpenId(String openId) {
		return mapper.getUserInfoByOpenId(openId);
	}

}
