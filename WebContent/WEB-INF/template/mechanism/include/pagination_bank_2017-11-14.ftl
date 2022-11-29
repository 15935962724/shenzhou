<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
<input type="hidden" id="searchProperty" name="searchProperty" value="${page.searchProperty}" />
<input type="hidden" id="orderProperty" name="orderProperty" value="${page.orderProperty}" />
<input type="hidden" id="orderDirection" name="orderDirection" value="${page.orderDirection}" />
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
			${message("admin.page.totalPages", totalPages)} ${message("admin.page.pageNumber", '<input id="pageNumber" type="text" style="width: 15px;" name="pageNumber" value="' + pageNumber + '" maxlength="9" onpaste="return false;" />')}<button type="submit">Go</button>
		</span>
	  </div>	
	</div>
[/#if]