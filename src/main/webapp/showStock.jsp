<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
<meta charset="UTF-8">
<title>StockChoice</title>
<link rel="stylesheet" href="res/css/bootstrap.min.css">
<script src="res/js/jquery.min.js"></script>
<script src="res/js/bootstrap.min.js"></script>

<style>
.navbar .nav>li .dropdown-menu {
	margin: 0;
}

.navbar .nav>li:hover .dropdown-menu {
	display: block;
}
</style>

</head>
<body>

	<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<span class="glyphicon glyphicon-asterisk">brand</span>
		</div>
		<div class="collapse navbar-collapse"></div>
	</div>
	</nav>

	<nav class="navbar navbar-default">
	<h1>选股器</h1>
	</nav>

	<div
		style="width: 1345px; height: 460px; background: blue; margin-top: -10px; margin-left: 10px; margin-right: 10px">
		<div style="width: 100%; height: 20%; background: red">
			<nav class="navbar navbar-default">
			<div class=" container-fluid">
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li class="dropdown"><a href="#">股票范围</a>
							<ul class="dropdown-menu">
								<div class="row">
									<div class="col-sm-6">
										<li style="margin-left: 20px"><a href="showhuA.action"
											style="text-decoration: none">沪A</a></li>
									</div>
									<div class="col-sm-3>">
										<li><a href="#menu8" style="text-decoration: none">深A</a></li>
									</div>
								</div>
							</ul></li>
						<li class="dropdown"><a href="#">价格</a>
							<ul class="dropdown-menu" style="width: 250px">
								<input type="text" size="3"
									style="margin-left: 10px; margin-right: 10px">
								<label>-</label>
								<input type="text" size="3"
									style="margin-left: 10px; margin-right: 20px">
								<button type="button" class="btn btn-default btn-sm">确定</button>
							</ul></li>
						<li class="dropdown"><a href="#menu3">涨幅</a>
							<ul class="dropdown-menu" style="width: 250px">
								<input type="text" size="3"
									style="margin-left: 10px; margin-right: 10px">
								<label>-</label>
								<input type="text" size="3"
									style="margin-left: 10px; margin-right: 20px">
								<button type="button" class="btn btn-default btn-sm">确定</button>
							</ul></li>
						<li class="dropdown"><a href="#">成交量</a>
							<ul class="dropdown-menu" style="width: 230px;">
								<label style="margin-left: 10px">大于等于:</label>
								<input type="text" size="3"
									style="margin-left: 10px; margin-right: 10px">
								<button type="button" class="btn btn-default btn-sm">确定</button>
								<label style="margin-left: 10px; margin-top: 10px">小于等于:</label>
								<input type="text" size="3"
									style="margin-left: 10px; margin-right: 10px">
								<button type="button" class="btn btn-default btn-sm">确定</button>
							</ul></li>
						<li class="dropdown"><a href="#">成交额</a>
							<ul class="dropdown-menu" style="width: 230px;">
								<label style="margin-left: 10px">大于等于:</label>
								<input type="text" size="3"
									style="margin-left: 10px; margin-right: 10px">
								<button type="button" class="btn btn-default btn-sm">确定</button>
								<label style="margin-left: 10px; margin-top: 10px">小于等于:</label>
								<input type="text" size="3"
									style="margin-left: 10px; margin-right: 10px">
								<button type="button" class="btn btn-default btn-sm">确定</button>
							</ul></li>
					</ul>
				</div>
			</div>
			</nav>
		</div>
		<div style="width: 100%; height: 80%; float: left; background: green">
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th>代码</th>
						<th>名称</th>
						<th>价格</th>
						<th>涨幅</th>
						<th>成交量</th>
						<th>成交额</th>
						<th>加入自选股</th>
					</tr>  
					<s:iterator value="stocks" status="st" var="st1">
						<tr>
							<td><s:property value="Symbol" /></td>
							<td><s:property value="Name" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
						</tr>
					</s:iterator>
				</thead>
			</table>
		</div>
	</div>

	<nav class="navbar navbar-inverse navbar-fixed-bottom">
	<div class="container-fluid">
		<div class="navbar-header">
			<span class="glyphicon glyphicon-asterisk">Bottom</span>
		</div>
		<div class="collapse navbar-collapse">
			<form class="navbar-form "></form>
		</div>
	</div>
	</nav>

</body>
</html>