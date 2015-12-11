package edu.zjucst.quatrabigdata.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.zjucst.quatrabigdata.dao.ICorpFinancialInfoDao;
import edu.zjucst.quatrabigdata.model.CorpFinancialInfo;
import edu.zjucst.quatrabigdata.service.ICorpFinancialInfoService;

@SuppressWarnings("restriction")
@Transactional(readOnly = true)
@Service(ICorpFinancialInfoService.SERVICE_NAME)
public class CorpFinancialInfoServiceImpl implements ICorpFinancialInfoService{

	@Resource(name = ICorpFinancialInfoDao.SERVICE_NAME)
	private ICorpFinancialInfoDao CorpFinancialInfoDao;
	
	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public void add(CorpFinancialInfo corpFinancialInfo){
		CorpFinancialInfoDao.save(corpFinancialInfo);
	}
	
	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public List<CorpFinancialInfo> findAll(){
		return CorpFinancialInfoDao.findAllObjects();
	}
	
	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public void addList(List<CorpFinancialInfo> cFIList){
		for(CorpFinancialInfo cFI : cFIList)
			add(cFI);
	}
}
