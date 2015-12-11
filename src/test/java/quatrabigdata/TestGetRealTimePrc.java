package quatrabigdata;

import org.junit.Test;

import edu.zjucst.quatrabigdata.container.ServiceProvider;
import edu.zjucst.quatrabigdata.service.IStockBasicService;

public class TestGetRealTimePrc {

	private IStockBasicService stockBasicService = (IStockBasicService) ServiceProvider
			.getService(IStockBasicService.SERVICE_NAME);
	
	@Test
	public void getCapitalFlowInfo() throws Exception {
		
	}
}
