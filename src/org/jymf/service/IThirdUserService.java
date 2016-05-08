package org.jymf.service;

import org.jymf.entity.ThirdUser;

public interface IThirdUserService {

	public boolean insertUser(ThirdUser user);
	
	public ThirdUser getUserInfoByOpenId(String token);
}
