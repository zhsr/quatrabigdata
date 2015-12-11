package quatrabigdata;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import edu.zjucst.quatrabigdata.container.ServiceProvider;
import edu.zjucst.quatrabigdata.model.CorpFinancialInfo;
import edu.zjucst.quatrabigdata.service.ICorpFinancialInfoService;
import edu.zjucst.quatrabigdata.service.ICrawlerService;
import edu.zjucst.quatrabigdata.service.IStockBasicService;

public class TestGetCorpFinancialInfo {

	private IStockBasicService stockBasicService = (IStockBasicService) ServiceProvider
			.getService(IStockBasicService.SERVICE_NAME);
	private ICrawlerService crawlerService = (ICrawlerService) ServiceProvider
			.getService(ICrawlerService.SERVICE_NAME);
	private ICorpFinancialInfoService corpFinancialInfoService = (ICorpFinancialInfoService) ServiceProvider
			.getService(ICorpFinancialInfoService.SERVICE_NAME);

	@Test
	public void addCorpFinancialInfo() throws Exception {

		Map<String,String> psNameMap = stockBasicService.getPSMapInStock();
		//Iterator<HashMap> it = psNameMap.entrySet().iterator();
		// System.out.println(place_symbolList.get(1));
		for (String ps : psNameMap.keySet()) {
			for (int panelMark = 0; panelMark < 3; panelMark++) {
				String url = crawlerService.genCFIURL(ps, panelMark);
				String name = psNameMap.get(ps);
				List<CorpFinancialInfo> cFIList= crawlerService.genCFIList(url,panelMark,ps,name);
				corpFinancialInfoService.addList(cFIList); // 插入数据库
			}
		}
	}
}
