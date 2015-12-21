package edu.zjucst.quatrabigdata.web.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import edu.zjucst.quatrabigdata.container.ServiceProvider;
import edu.zjucst.quatrabigdata.model.StockBasic;
import edu.zjucst.quatrabigdata.service.ICrawlerService;
import edu.zjucst.quatrabigdata.service.IStockBasicService;

@SuppressWarnings("serial")
// @Namespace("/stock")
@Results({ @Result(name = "show", location = "/showStock.jsp", type = "dispatcher") })
public class ShowStockAction extends BaseAction { // 只显示basicInfo表，在市的股票，沪A

	private IStockBasicService stockBasicService = (IStockBasicService) ServiceProvider
			.getService(IStockBasicService.SERVICE_NAME);
	private List<StockBasic> stocks;

	@Action("showhuA") // 发送的url请求为http://localhost:8080/showhuA.action，执行该action，根据return的字符串跳转到jsp中
	public String showStock() {
		stocks = stockBasicService.getHuASBList();
		System.out.println("before return in showStockAction............");
		for (StockBasic s : stocks)
			System.out.println(s.getSymbol() + "  " + s.getName());
		return "show";
	}

	public List<StockBasic> getStocks() {
		return stocks;
	}

	public void setStocks(List<StockBasic> stocks) {
		this.stocks = stocks;
	}
}
