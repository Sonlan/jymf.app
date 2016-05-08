package org.jymf.service.impl;

import org.jymf.dao.WXRedMapper;
import org.jymf.entity.WXRed;
import org.jymf.service.IWXRedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wxredService")
public class WXRedService implements IWXRedService {
	
	@Autowired
	private WXRedMapper wxredDao;
	
	@Override
	public void addWXRed(WXRed wxred) {
		wxredDao.insert(wxred);
	}
}
