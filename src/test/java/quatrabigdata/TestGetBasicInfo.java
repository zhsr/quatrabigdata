package quatrabigdata;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import edu.zjucst.quatrabigdata.container.ServiceProvider;
import edu.zjucst.quatrabigdata.model.BasicInfo;
import edu.zjucst.quatrabigdata.service.IBasicInfoService;
import edu.zjucst.quatrabigdata.service.ICrawlerService;
import edu.zjucst.quatrabigdata.service.IStockBasicService;

public class TestGetBasicInfo {

	private IStockBasicService stockBasicService = (IStockBasicService) ServiceProvider
			.getService(IStockBasicService.SERVICE_NAME);
	private IBasicInfoService basicInfoService = (IBasicInfoService) ServiceProvider
			.getService(IBasicInfoService.SERVICE_NAME);
	private ICrawlerService crawlerService = (ICrawlerService) ServiceProvider
			.getService(ICrawlerService.SERVICE_NAME);

	@Test
	public void addBasicInfo() throws Exception {
		Map<String,Integer> psStateMap = stockBasicService.getPSStateMap();
		// System.out.println(place_symbolList.get(1));
		for (String ps : psStateMap.keySet()) {
			String url = crawlerService.genBIURL(ps);
			int state = psStateMap.get(ps);
			BasicInfo bi = crawlerService.genBI(url,ps,state);
			basicInfoService.add(bi); // 插入数据库

		}
	}
}
