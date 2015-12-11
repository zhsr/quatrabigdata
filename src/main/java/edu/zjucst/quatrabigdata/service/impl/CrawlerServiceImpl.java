package edu.zjucst.quatrabigdata.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.zjucst.quatrabigdata.model.BasicInfo;
import edu.zjucst.quatrabigdata.model.CapitalFlow;
import edu.zjucst.quatrabigdata.model.CorpFinancialInfo;
import edu.zjucst.quatrabigdata.model.DailyTradeInfo;
import edu.zjucst.quatrabigdata.model.RealTimePrc;
import edu.zjucst.quatrabigdata.model.StockBasic;
import edu.zjucst.quatrabigdata.service.ICrawlerService;
import edu.zjucst.quatrabigdata.utils.URLUtils;

@Transactional(readOnly = true)
@Service(ICrawlerService.SERVICE_NAME)
public class CrawlerServiceImpl implements ICrawlerService {

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public String genCFIURL(String place_symbol, int panelMark) {
		final String preUrl = "http://f10.eastmoney.com/f10_v2/BackOffice.aspx?command=RptF10MainTarget&code=";
		String place = place_symbol.substring(0, 2);
		String symbol = place_symbol.substring(2);
		Date date = new Date();
		String dateStr = Long.toString(date.getTime());
		String placeCode;
		if (place.equals("sh"))
			placeCode = "01";
		else
			placeCode = "02";
		return preUrl + symbol + placeCode + "&num=10&code1=" + place_symbol + "&spstr=&n=" + panelMark + "&timetip="
				+ dateStr;
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public List<CorpFinancialInfo> genCFIList(String url, int panelMark, String ps,String name) {
		String symbol = ps.substring(2);
		if (panelMark == 2) {
			try {
				return genThiCFIList(url, panelMark, symbol,name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				return genFirSecCFIList(url, panelMark, symbol,name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ArrayList<CorpFinancialInfo>();
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public List<CorpFinancialInfo> genFirSecCFIList(String url, int panelMark, String symbol,String name)
			throws IOException, ParseException {
		List<CorpFinancialInfo> cFIList = new ArrayList<CorpFinancialInfo>();
		System.out.println(url);
		Document doc = Jsoup.connect(url).get();

		Elements trElements = doc.select("tr");
		// System.out.println(trElements.first().toString());
		// int row = trElements.size() - 6; // 数值字段数
		Elements thDate = trElements.first().select("th.tips-fieldname-Right");
		// System.out.println(thDate.first().toString());
		int col = thDate.size(); // 通过日期的个数知道该panel总共有多少条记录
		Elements spanDate = thDate.select("span");
		// System.out.println(spanDate.first().toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		List<Date> dateList = new ArrayList<Date>();
		for (Element sDate : spanDate) {
			dateList.add(sdf.parse(sDate.text()));
		}

		Elements tdElements = doc.select("td.tips-data-Right");
		// System.out.println(tdElments.first().toString());
		List<String> tdList = new ArrayList<String>();
		for (Element td : tdElements) {
			tdList.add(td.select("span").text());
		}

		for (int c = 0; c < col; c++) {

			cFIList.add(new CorpFinancialInfo(symbol, name,dateList.get(c), panelMark, tdList.get(0 * col + c),
					tdList.get(1 * col + c), tdList.get(2 * col + c), tdList.get(3 * col + c), tdList.get(4 * col + c),
					tdList.get(5 * col + c), tdList.get(6 * col + c), tdList.get(7 * col + c), tdList.get(8 * col + c),
					tdList.get(9 * col + c), tdList.get(10 * col + c), tdList.get(11 * col + c),
					tdList.get(12 * col + c), tdList.get(13 * col + c), tdList.get(14 * col + c),
					tdList.get(15 * col + c), tdList.get(16 * col + c), tdList.get(17 * col + c),
					tdList.get(18 * col + c), tdList.get(19 * col + c), tdList.get(20 * col + c),
					tdList.get(21 * col + c), tdList.get(22 * col + c), tdList.get(23 * col + c),
					tdList.get(24 * col + c), tdList.get(25 * col + c), tdList.get(26 * col + c),
					tdList.get(27 * col + c), tdList.get(28 * col + c), tdList.get(29 * col + c),
					tdList.get(30 * col + c), tdList.get(31 * col + c), tdList.get(32 * col + c)));

		}

		return cFIList;

	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public List<CorpFinancialInfo> genThiCFIList(String url, int panelMark, String symbol,String name)
			throws IOException, ParseException {
		List<CorpFinancialInfo> cFIList = new ArrayList<CorpFinancialInfo>();
		Document doc = Jsoup.connect(url).get();
		System.out.println(url);
		Elements trElements = doc.select("tr");

		Elements thDate = trElements.first().select("th.tips-fieldname-Right");
		int col = thDate.size(); // 通过日期的个数知道该panel总共有多少条记录
		Elements spanDate = thDate.select("span");
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		List<Date> dateList = new ArrayList<Date>();
		for (Element sDate : spanDate) {
			dateList.add(sdf.parse(sDate.text()));
		}

		Elements tdElments = doc.select("td.tips-data-Right");
		List<String> tdList = new ArrayList<String>();
		for (Element td : tdElments) {
			tdList.add(td.select("span").text());
		}

		for (int c = 0; c < col; c++) {

			cFIList.add(new CorpFinancialInfo(symbol, name,dateList.get(c), panelMark, tdList.get(0 * col + c), "--", "--",
					tdList.get(1 * col + c), tdList.get(2 * col + c), tdList.get(3 * col + c), tdList.get(4 * col + c),
					tdList.get(5 * col + c), tdList.get(6 * col + c), tdList.get(7 * col + c), tdList.get(8 * col + c),
					tdList.get(9 * col + c), tdList.get(10 * col + c), tdList.get(11 * col + c),
					tdList.get(12 * col + c), tdList.get(13 * col + c), tdList.get(14 * col + c), "--",
					tdList.get(15 * col + c), tdList.get(16 * col + c), tdList.get(17 * col + c),
					tdList.get(18 * col + c), "--", "--", "--", "--", "--", "--", "--", "--", "--", "--", "--"));
		}
		return cFIList;

	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
	public String genBIURL(String place_symbol) {

		return "http://f10.eastmoney.com/f10_v2/CompanySurvey.aspx?code=" + place_symbol;
	}

	public BasicInfo genBI(String url, String ps,int state) throws IOException {
		BasicInfo bi = new BasicInfo();
		System.out.println(url);
		try {
			Document doc = Jsoup.connect(url).get();
			Elements tables = doc.select("div.content");
			List<String> tdList = new ArrayList<String>();
			for (Element table : tables) {
				// System.out.println(e.select("td").toString());
				Elements tds = table.select("td");
				for (Element td : tds)
					tdList.add(td.text());
			}
			bi.setSymbol(ps);
			bi.setName(tdList.get(4));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			bi.setIssueDate(sdf.parse(tdList.get(34)));
			bi.setIssueAmt(tdList.get(39));
			bi.setIssuePrc(BigDecimal.valueOf(Double.parseDouble(tdList.get(40))));
			bi.setIssuer("--");
			bi.setCategory(tdList.get(9));
			bi.setArea(tdList.get(10));
			bi.setState(state);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bi;
	}

	@Override
	public List<StockBasic> genSBList() {
		String content = URLUtils.getContent("http://quote.eastmoney.com/stocklist.html");
		String regxString = " <li><a target=\"_blank\" href=\"http://quote.eastmoney.com/(.*?).html\">(.*?)((.*?))</a></li>";
		Pattern p = Pattern.compile(regxString);
		Matcher m = p.matcher(content);
		String tmp1, tmp2;
		String place, symbol, name, place_symbol;
		List<StockBasic> sBList = new ArrayList<StockBasic>();
		// 同时获取取上海、深圳证券交易所的数据
		while (m.find()) {
			String symbolStr = m.group(1).substring(2); // 得到股票代码字符串
			// 只取a股股票，000、002、60打头，有1000条左右记录不是a股的
			if (symbolStr.startsWith("000") || symbolStr.startsWith("002") || symbolStr.startsWith("60")) {
				tmp1 = m.group(1).trim();
				place = tmp1.substring(0, 2);
				symbol = tmp1.substring(2);
				tmp2 = m.group(3).trim();
				String[] array = tmp2.split("\\(");
				name = array[0];
				place_symbol = place + symbol;
				StockBasic sb = new StockBasic(symbol, name, place, place_symbol);
				sBList.add(sb);
			}
		}
		return sBList;
	}

	@Override
	public CapitalFlow genCapitalFIObject(String place_symbol) {
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
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		String baseURL = "http://qt.gtimg.cn/q=ff_"; // 实时资金流向，设置股票state的URL
		String urlFinalString = baseURL + place_symbol;
		String content = URLUtils.getContent(urlFinalString);
		String[] strArray = content.toString().split("~");
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
		try {
			date = sdf.parse(strArray[13]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		CapitalFlow cf = new CapitalFlow(symbol, mainFlowIn, mainFlowOut, mainNetFlowIn, mainMoneyInOutTotalRate,
				retFlowIn, retFlowOut, retNetFlowIn, retMoneyInOutTotalRate, mainRetMoneyInOutTotal, name, date);
		return cf;
	}

	@Override
	public RealTimePrc genRealTimePrcObject(String place_symbol) {
		String baseURL = "http://qt.gtimg.cn/q="; // 最新行情，实时价格表
		String urlFinalString = baseURL + place_symbol;
		String Symbol;
		String Name;
		BigDecimal BuyOnePrc;
		BigDecimal BuyTwoPrc;
		BigDecimal BuyThreePrc;
		BigDecimal BuyFourPrc;
		BigDecimal BuyFivePrc;
		BigDecimal SelOnePrc;
		BigDecimal SelTwoPrc;
		BigDecimal SelThreePrc;
		BigDecimal SelFourPrc;
		BigDecimal SelFivePrc;
		int BuyOneAmt;
		int BuyTwoAmt;
		int BuyThreeAmt;
		int BuyFourAmt;
		int BuyFiveAmt;
		int SelOneAmt;
		int SelTwoAmt;
		int SelThreeAmt;
		int SelFourAmt;
		int SelFiveAmt;
		BigDecimal CurrentPrc;
		BigDecimal AveragePrc; // 数据源不包含该字段
		BigDecimal IncreasePrc;
		BigDecimal Increase;
		int TraddingVol;
		BigDecimal TraddingPrc;
		double Ratio; // 数据源不包含该字段
		double TurnoverRate;
		int BuyVol;
		int SellVol;
		double Committee; // 数据源不包含该字段
		double CommitteeDiff; // 数据源不包含该字段
		Timestamp Time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");

		String content = URLUtils.getContent(urlFinalString); // 用URL得到String
		String[] strArray = content.split("~");
		Symbol = strArray[2];
		Name = strArray[1];
		BuyOnePrc = BigDecimal.valueOf(Double.parseDouble(strArray[9]));
		BuyOneAmt = Integer.parseInt(strArray[10]);
		BuyTwoPrc = BigDecimal.valueOf(Double.parseDouble(strArray[11]));
		BuyTwoAmt = Integer.parseInt(strArray[12]);
		BuyThreePrc = BigDecimal.valueOf(Double.parseDouble(strArray[13]));
		BuyThreeAmt = Integer.parseInt(strArray[14]);
		BuyFourPrc = BigDecimal.valueOf(Double.parseDouble(strArray[15]));
		BuyFourAmt = Integer.parseInt(strArray[16]);
		BuyFivePrc = BigDecimal.valueOf(Double.parseDouble(strArray[17]));
		BuyFiveAmt = Integer.parseInt(strArray[18]);
		SelOnePrc = BigDecimal.valueOf(Double.parseDouble(strArray[19]));
		SelOneAmt = Integer.parseInt(strArray[20]);
		SelTwoPrc = BigDecimal.valueOf(Double.parseDouble(strArray[21]));
		SelTwoAmt = Integer.parseInt(strArray[22]);
		SelThreePrc = BigDecimal.valueOf(Double.parseDouble(strArray[23]));
		SelThreeAmt = Integer.parseInt(strArray[24]);
		SelFourPrc = BigDecimal.valueOf(Double.parseDouble(strArray[25]));
		SelFourAmt = Integer.parseInt(strArray[26]);
		SelFivePrc = BigDecimal.valueOf(Double.parseDouble(strArray[27]));
		SelFiveAmt = Integer.parseInt(strArray[28]);
		CurrentPrc = BigDecimal.valueOf(Double.parseDouble(strArray[3]));
		// 暂时取不到AveragePrc=BigDecimal.valueOf(Double.parseDouble(strArray[3]));
		IncreasePrc = BigDecimal.valueOf(Double.parseDouble(strArray[31]));
		Increase = BigDecimal.valueOf(Double.parseDouble(strArray[32]));
		TraddingVol = Integer.parseInt(strArray[36]);
		TraddingPrc = BigDecimal.valueOf(Double.parseDouble(strArray[37]));
		// Ratio
		TurnoverRate = Double.parseDouble(strArray[38]);
		BuyVol = Integer.parseInt(strArray[7]);
		SellVol = Integer.parseInt(strArray[8]);
		// Committee
		// CommitteeDiff
		try {
			Time = new Timestamp(sdf.parse(strArray[30]).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		RealTimePrc realTimePrc = new RealTimePrc(Symbol, Name, BuyOnePrc, BuyTwoPrc, BuyThreePrc, BuyFourPrc,
				BuyFivePrc, SelOnePrc, SelTwoPrc, SelThreePrc, SelFourPrc, SelFivePrc, BuyOneAmt, BuyTwoAmt,
				BuyThreeAmt, BuyFourAmt, BuyFiveAmt, SelOneAmt, SelTwoAmt, SelThreeAmt, SelFourAmt, SelFiveAmt,
				CurrentPrc, IncreasePrc, Increase, TraddingVol, TraddingPrc, TurnoverRate, BuyVol, SellVol, Time); // 删除了以上四个变量的构造函数
		return realTimePrc;
	}

	@Override
	public DailyTradeInfo genDailyTradeObject(String place_symbol) {
		String url = "http://qt.gtimg.cn/q=";
		String urlFinalString;
		String symbol;
		String name;
		Date date = null;
		BigDecimal Open;
		BigDecimal High;
		BigDecimal Low;
		BigDecimal Close; // ?
		BigDecimal PreClose;
		BigDecimal Volume;
		BigDecimal Amount;
		BigDecimal EarnRatio;
		BigDecimal PBRatio;
		BigDecimal CirMarketVal;
		BigDecimal TotMarketVal;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");

		urlFinalString = url + place_symbol;
		String content = URLUtils.getContent(urlFinalString);

		String[] strArray = content.split("~");

		symbol = place_symbol.substring(2);
		try {
			date = sdf.parse(strArray[30]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		name = strArray[12];
		Open = BigDecimal.valueOf(Double.parseDouble(strArray[5]));
		High = BigDecimal.valueOf(Double.parseDouble(strArray[33]));
		Low = BigDecimal.valueOf(Double.parseDouble(strArray[34]));
		PreClose = BigDecimal.valueOf(Double.parseDouble(strArray[4]));
		Volume = BigDecimal.valueOf(Double.parseDouble(strArray[36]));
		Amount = BigDecimal.valueOf(Double.parseDouble(strArray[37]));
		if (!strArray[39].equals("")) {
			EarnRatio = BigDecimal.valueOf(Double.parseDouble(strArray[39]));
		} else {
			EarnRatio = null;
		}
		PBRatio = BigDecimal.valueOf(Double.parseDouble(strArray[46]));
		CirMarketVal = BigDecimal.valueOf(Double.parseDouble(strArray[44]));
		TotMarketVal = BigDecimal.valueOf(Double.parseDouble(strArray[45]));
		DailyTradeInfo dti = new DailyTradeInfo(symbol, name,date, Open, High, Low, PreClose, Volume, Amount, EarnRatio,
				PBRatio, CirMarketVal, TotMarketVal);
		return dti;
	}

}
