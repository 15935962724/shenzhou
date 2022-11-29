<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
<input type="hidden" id="searchProperty" name="searchProperty" value="${page.searchProperty}" />
<input type="hidden" id="orderProperty" name="orderProperty" value="${page.orderProperty}" />
<input type="hidden" id="orderDirection" name="orderDirection" value="${page.orderDirection}" />
<table cellpadding="0" cellspacing="0" border="0" width="100%" style="margin-left: 20px;">
	<tr>
		<td width="350" height="80" valign = "middle">共<font color="#ff0000>">${page.total}条</font>记录 每页显示
		<select id = "pageSizeSelectd">
			<option [#if page.pageSize == 10] selected="selected" [/#if] value="10">10</option>	
			<option [#if page.pageSize == 20] selected="selected" [/#if] value="20">20</option>
			<option [#if page.pageSize == 50] selected="selected" [/#if] value="50">50</option>
			<option [#if page.pageSize == 100] selected="selected" [/#if] value="100">100</option>
		</select>条 共<font color="#ff0000>">${page.totalPages}</font>页
		</td>
		<td style="position:relative;top:-11px;">
			[#if totalPages > 1]
				<div class="page">
				  <div class="page_r_1">
					[#if isFirst]
						<span class="firstPage">&nbsp;</span>
					[#else]
						<a class="firstPage" href="javascript: $.pageSkip(${firstPageNumber});">首页</a>
					[/#if]
					[#if hasPrevious]
						<a class="previousPage" href="javascript: $.pageSkip(${previousPageNumber});">上一页</a>
					[#else]
						<span class="previousPage">&nbsp;</span>
					[/#if]
					[#list segment as segmentPageNumber]
						[#if segmentPageNumber_index == 0 && segmentPageNumber > firstPageNumber + 1]
							<span class="pageBreak">...</span>
						[/#if]
						[#if segmentPageNumber != pageNumber]
							<a href="javascript: $.pageSkip(${segmentPageNumber});">${segmentPageNumber}</a>
						[#else]
							<span class="currentPage">${segmentPageNumber}</span>
						[/#if]
						[#if !segmentPageNumber_has_next && segmentPageNumber < lastPageNumber - 1]
							<span class="pageBreak">...</span>
						[/#if]
					[/#list]
					[#if hasNext]
						<a class="nextPage" href="javascript: $.pageSkip(${nextPageNumber});">下一页</a>
					[#else]
						<span class="nextPage">&nbsp;</span>
					[/#if]
					[#if isLast]
						<span class="lastPage">&nbsp;</span>
					[#else]
						<a class="lastPage" href="javascript: $.pageSkip(${lastPageNumber});">未页</a>
					[/#if]
					<span class="pageSkip">
						${message("admin.page.pageNumber", '<input id="pageNumber" type="text" style="width: 15px;" name="pageNumber" value="' + pageNumber + '" maxlength="9" onpaste="return false;" />')}<button type="submit">Go</button>
					</span>
				  </div>	
				</div>
		[/#if]
		</td>
	</tr>
</table>



