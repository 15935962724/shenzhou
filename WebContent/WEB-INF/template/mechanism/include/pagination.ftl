<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
<input type="hidden" id="searchProperty" name="searchProperty" value="${page.searchProperty}" />
<input type="hidden" id="orderProperty" name="orderProperty" value="${page.orderProperty}" />
<input type="hidden" id="orderDirection" name="orderDirection" value="${page.orderDirection}" />
[#if totalPages > 1]
	<td height="50" valign="middle">
		共<span class="z16279fff">${page.total}</span>条记录 每页显示
		<select class="bae1e1e1 w50" id = "pageSizeSelectd">
			<option [#if page.pageSize == 10] selected="selected" [/#if] value="10">10</option>	
			<option [#if page.pageSize == 20] selected="selected" [/#if] value="20">20</option>
			<option [#if page.pageSize == 50] selected="selected" [/#if] value="50">50</option>
			<option [#if page.pageSize == 100] selected="selected" [/#if] value="100">100</option>
			<option [#if page.pageSize == 200] selected="selected" [/#if] value="200">200</option>
			<option [#if page.pageSize == 300] selected="selected" [/#if] value="300">300</option>
			<option [#if page.pageSize == 500] selected="selected" [/#if] value="500">500</option>
			<option [#if page.pageSize == 1000] selected="selected" [/#if] value="100">1000</option>
		</select>
		条 共<span class="z16279fff">${page.totalPages}</span>页</td>
	<td valign="middle" align="right" id="fy">
		[#if isFirst]
			<span>&nbsp;</span> 
		[#else]
			<a href="javascript: $.pageSkip(${firstPageNumber});">首页</a>
		[/#if]
		[#if hasPrevious]
			<a  href="javascript: $.pageSkip(${previousPageNumber});">上一页</a>
		[#else]
			<span>&nbsp;</span> 
		[/#if]
		[#list segment as segmentPageNumber]
			[#if segmentPageNumber_index == 0 && segmentPageNumber > firstPageNumber + 1]
				<a href="javascript:;">...</a> 
			[/#if]
			[#if segmentPageNumber != pageNumber]
				<a href="javascript: $.pageSkip(${segmentPageNumber});">${segmentPageNumber}</a>
			[#else]
				<a href="javascript:;" class="page">${segmentPageNumber}</a>
			[/#if]
			[#if !segmentPageNumber_has_next && segmentPageNumber < lastPageNumber - 1]
				<a href="javascript:;">...</a> 
			[/#if]
		[/#list]
		[#if hasNext]
			<a href="javascript: $.pageSkip(${nextPageNumber});">下一页</a>
		[#else]
			<span>&nbsp;</span> 
		[/#if]
		[#if isLast]
			<span>&nbsp;</span> 
		[#else]
			<a href="javascript: $.pageSkip(${lastPageNumber});">未页</a>
		[/#if]
		跳转至
		<input type="text" id="pageNumber" name="pageNumber" value="${pageNumber}"  maxlength="9" onpaste="return false;" class="bae1e1e1 w20 ztc">
		页
		<input type="submit" value="GO" class="bae1e1e1 w40 z12ffffff bg279fff">
	</td>
[/#if]


