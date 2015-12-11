package quatrabigdata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import edu.zjucst.quatrabigdata.container.ServiceProvider;
import edu.zjucst.quatrabigdata.model.StockBasic;
import edu.zjucst.quatrabigdata.service.IStockBasicService;
import edu.zjucst.quatrabigdata.utils.URLUtils;

public class TestGetAllStock {

    private IStockBasicService stockBasicService = (IStockBasicService) ServiceProvider
	    .getService(IStockBasicService.SERVICE_NAME);

    @Test
    public void getStock() {
	String content = URLUtils
		.getContent("http://quote.eastmoney.com/stocklist.html");
	// String[] content1 = content
	// .split("<div class=\"sltit\"><a name=\"sz\"/>深圳股票</div>");
	// StreamUtils.write2File(content1[0], "D:/stock1.txt");
	// StreamUtils.write2File(content1[1], "D:/stock2.txt");
	String regxString = " <li><a target=\"_blank\" href=\"http://quote.eastmoney.com/(.*?).html\">(.*?)((.*?))</a></li>";
	Pattern p = Pattern.compile(regxString);
	Matcher m = p.matcher(content);

	String tmp1, tmp2;
	String place, symbol, name, place_symbol;

	// 同时获取取上海、深圳证券交易所的数据
	while (m.find()) {
	    // strfunds += m.group(2).trim() + ",";
	     System.out.println(m.group(1).trim());
	     System.out.println(m.group(3).trim());
//	    tmp1 = m.group(1).trim();
//	    place = tmp1.substring(0, 2);
//	    symbol = tmp1.substring(2);
//	    tmp2 = m.group(3).trim();
//	    String[] array = tmp2.split("\\(");
//	    name = array[0];
//	    place_symbol = place + symbol;
//	    StockBasic sb = new StockBasic(symbol, name, place, place_symbol);
//	    stockBasicService.add(sb);
	}
    }
}
