package quatrabigdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import edu.zjucst.quatrabigdata.container.ServiceProvider;
import edu.zjucst.quatrabigdata.model.CapitalFlow;
import edu.zjucst.quatrabigdata.service.ICapitalFlowService;
import edu.zjucst.quatrabigdata.service.IStockBasicService;

public class TestGetCapitalFlow {   //

	private IStockBasicService stockBasicService = (IStockBasicService) ServiceProvider
			.getService(IStockBasicService.SERVICE_NAME);
	private ICapitalFlowService capitalFlowService = (ICapitalFlowService) ServiceProvider
			.getService(ICapitalFlowService.SERVICE_NAME);

	@Test
	public void getCapitalFlowInfo() throws Exception {

		List<String> place_symbolList = stockBasicService.getPlace_symbol();
		String place_symbol;
		String url = "http://qt.gtimg.cn/q=ff_";
		String urlFinalString;
		String symbol;
		BigDecimal mainFlowIn;
		BigDecimal mainFlowOut;
		BigDecimal mainNetFlowIn;
		BigDecimal mainMoneyInOutTotalRate;
		BigDecimal retFlowIn;
		BigDecimal retFlowOut;
		BigDecimal retNetFlowIn;
		BigDecimal retMoneyInOutTotalRate;
		BigDecimal mainRetMoneyInOutTotal;
		String name;
		Date date;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		for (Iterator<String> i = place_symbolList.iterator(); i.hasNext();) {
			place_symbol = i.next(); // http://qt.gtimg.cn/q=ff_sz000003有问题，没有array数据。。属于退市了的股票
			urlFinalString = url + place_symbol; // 将所有退市的股票代码存储到一个数组，然后考虑在数据库存一个标识字段
			try {
				URL urlFinal = new URL(urlFinalString);
				HttpURLConnection urlcon = (HttpURLConnection) urlFinal.openConnection();
				urlcon.connect();
				InputStream is = urlcon.getInputStream();
				BufferedReader buffer = new BufferedReader(new InputStreamReader(is, "GBK"));
				StringBuffer bs = new StringBuffer();
				String str = null;
				while ((str = buffer.readLine()) != null) {
					bs.append(str);
				}

				String[] strArray = bs.toString().split("~");
				symbol = place_symbol.substring(2);
				mainFlowIn = BigDecimal.valueOf(Double.parseDouble(strArray[1]));
				mainFlowOut = BigDecimal.valueOf(Double.parseDouble(strArray[2]));
				mainNetFlowIn = BigDecimal.valueOf(Double.parseDouble(strArray[3]));
				mainMoneyInOutTotalRate = BigDecimal.valueOf(Double.parseDouble(strArray[4]));
				retFlowIn = BigDecimal.valueOf(Double.parseDouble(strArray[5]));
				retFlowOut = BigDecimal.valueOf(Double.parseDouble(strArray[6]));
				retNetFlowIn = BigDecimal.valueOf(Double.parseDouble(strArray[7]));
				retMoneyInOutTotalRate = BigDecimal.valueOf(Double.parseDouble(strArray[8]));
				mainRetMoneyInOutTotal = BigDecimal.valueOf(Double.parseDouble(strArray[9]));
				name = strArray[12]; // 前面间隔的2个属性是未知量
				date = sdf.parse(strArray[13]);

				CapitalFlow cf = new CapitalFlow(symbol, mainFlowIn, mainFlowOut, mainNetFlowIn,
						mainMoneyInOutTotalRate, retFlowIn, retFlowOut, retNetFlowIn, retMoneyInOutTotalRate,
						mainRetMoneyInOutTotal, name, date);
				capitalFlowService.add(cf);       //插入数据库

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
