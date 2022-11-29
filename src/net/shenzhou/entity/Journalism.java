/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import net.shenzhou.CommonAttributes;
import net.shenzhou.util.FreemarkerUtils;

import org.apache.commons.lang.StringUtils;
import org.dom4j.io.SAXReader;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Similarity;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.springframework.core.io.ClassPathResource;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKSimilarity;

import freemarker.template.TemplateException;

/**
 * 新闻
 * @date 2017-10-10 17:23:42
 * @author wsr
 *
 */
@Indexed
@Similarity(impl = IKSimilarity.class)
@Entity
@Table(name = "xx_journalism")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_journalism_sequence")
public class Journalism extends  BaseEntity  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3349620765985888822L;


	/** 点击数缓存名称 */
	public static final String HITS_CACHE_NAME = "journalismHits";

	/** 点击数缓存更新间隔时间 */
	public static final int HITS_CACHE_INTERVAL = 600000;

	/** 内容分页长度 */
	private static final int PAGE_CONTENT_LENGTH = 800;

	/** 内容分页符 */
	private static final String PAGE_BREAK_SEPARATOR = "<hr class=\"pageBreak\" />";

	/** 段落分隔符配比 */
	private static final Pattern PARAGRAPH_SEPARATOR_PATTERN = Pattern.compile("[,;\\.!?，；。！？]");

	/** 静态路径 */
	private static String staticPath;

	/** 标题 */
	private String title;

	/** 作者 */
	private String author;
	
	/** 来源 */
	private String source;
	
	/** 展示图片 */
	private String image;
	
	/** 内容 */
	private String content;

	/** 页面标题 */
	private String seoTitle;

	/** 页面关键词 */
	private String seoKeywords;

	/** 页面描述 */
	private String seoDescription;

	/** 是否发布 */
	private Boolean isPublication;

	/** 是否置顶 */
	private Boolean isTop;

	/** 点击数 */
	private Long hits;

	/** 页码 */
	private Integer pageNumber;

	/** 新闻分类 */
	private JournalismCategory journalismCategory;

	/** 标签 */
	private Set<Tag> tags = new HashSet<Tag>();

	static {
		try {
			File shenzhouXmlFile = new ClassPathResource(CommonAttributes.shenzhou_XML_PATH).getFile();
			org.dom4j.Document document = new SAXReader().read(shenzhouXmlFile);
			org.dom4j.Element element = (org.dom4j.Element) document.selectSingleNode("/shenzhou/template[@id='journalismContent']");
			staticPath = element.attributeValue("staticPath");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取标题
	 * 
	 * @return 标题
	 */
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取作者
	 * 
	 * @return 作者
	 */
	@Field(store = Store.YES, index = Index.NO)
	@Length(max = 200)
	public String getAuthor() {
		return author;
	}

	/**
	 * 设置作者
	 * 
	 * @param author
	 *            作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	
	@NotEmpty
	@Length(max = 60)
	@Column(nullable = false)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	@Lob
	public String getContent() {
		if (pageNumber != null) {
			String[] pageContents = getPageContents();
			if (pageNumber < 1) {
				pageNumber = 1;
			}
			if (pageNumber > pageContents.length) {
				pageNumber = pageContents.length;
			}
			return pageContents[pageNumber - 1];
		} else {
			return content;
		}
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取页面标题
	 * 
	 * @return 页面标题
	 */
	@Length(max = 200)
	public String getSeoTitle() {
		return seoTitle;
	}

	/**
	 * 设置页面标题
	 * 
	 * @param seoTitle
	 *            页面标题
	 */
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	/**
	 * 获取页面关键词
	 * 
	 * @return 页面关键词
	 */
	@Length(max = 200)
	public String getSeoKeywords() {
		return seoKeywords;
	}

	/**
	 * 设置页面关键词
	 * 
	 * @param seoKeywords
	 *            页面关键词
	 */
	public void setSeoKeywords(String seoKeywords) {
		if (seoKeywords != null) {
			seoKeywords = seoKeywords.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
		}
		this.seoKeywords = seoKeywords;
	}

	/**
	 * 获取页面描述
	 * 
	 * @return 页面描述
	 */
	@Length(max = 200)
	public String getSeoDescription() {
		return seoDescription;
	}

	/**
	 * 设置页面描述
	 * 
	 * @param seoDescription
	 *            页面描述
	 */
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	/**
	 * 获取是否发布
	 * 
	 * @return 是否发布
	 */
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	@NotNull
	@Column(nullable = false)
	public Boolean getIsPublication() {
		return isPublication;
	}

	/**
	 * 设置是否发布
	 * 
	 * @param isPublication
	 *            是否发布
	 */
	public void setIsPublication(Boolean isPublication) {
		this.isPublication = isPublication;
	}

	/**
	 * 获取是否置顶
	 * 
	 * @return 是否置顶
	 */
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	@NotNull
	@Column(nullable = false)
	public Boolean getIsTop() {
		return isTop;
	}

	/**
	 * 设置是否置顶
	 * 
	 * @param isTop
	 *            是否置顶
	 */
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	/**
	 * 获取点击数
	 * 
	 * @return 点击数
	 */
	@Column(nullable = false)
	public Long getHits() {
		return hits;
	}

	/**
	 * 设置点击数
	 * 
	 * @param hits
	 *            点击数
	 */
	public void setHits(Long hits) {
		this.hits = hits;
	}

	/**
	 * 获取页码
	 * 
	 * @return 页码
	 */
	@Transient
	public Integer getPageNumber() {
		return pageNumber;
	}

	/**
	 * 设置页码
	 * 
	 * @param pageNumber
	 *            页码
	 */
	@Transient
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}


	/**
	 * 获取新闻分类
	 * 
	 * @return 新闻分类
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public JournalismCategory getJournalismCategory() {
		return journalismCategory;
	}

	public void setJournalismCategory(JournalismCategory journalismCategory) {
		this.journalismCategory = journalismCategory;
	}

	/**
	 * 获取标签
	 * 
	 * @return 标签
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_journalism_tag")
	@OrderBy("order asc")
	public Set<Tag> getTags() {
		return tags;
	}

	/**
	 * 设置标签
	 * 
	 * @param tags
	 *            标签
	 */
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	/**
	 * 获取访问路径
	 * 
	 * @return 访问路径
	 */
	@Transient
	public String getPath() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", getId());
		model.put("createDate", getCreateDate());
		model.put("modifyDate", getModifyDate());
		model.put("title", getTitle());
		model.put("seoTitle", getSeoTitle());
		model.put("seoKeywords", getSeoKeywords());
		model.put("seoDescription", getSeoDescription());
		model.put("pageNumber", getPageNumber());
		model.put("journalismCategory", getJournalismCategory());
		try {
			return FreemarkerUtils.process(staticPath, model);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取文本内容
	 * 
	 * @return 文本内容
	 */
	@Transient
	public String getText() {
		if (getContent() != null) {
			return Jsoup.parse(getContent()).text();
		}
		return null;
	}

	/**
	 * 获取分页内容
	 * 
	 * @return 分页内容
	 */
	@Transient
	public String[] getPageContents() {
		if (StringUtils.isEmpty(content)) {
			return new String[] { "" };
		}
		if (content.contains(PAGE_BREAK_SEPARATOR)) {
			return content.split(PAGE_BREAK_SEPARATOR);
		} else {
			List<String> pageContents = new ArrayList<String>();
			Document document = Jsoup.parse(content);
			List<Node> children = document.body().childNodes();
			if (children != null) {
				int textLength = 0;
				StringBuffer html = new StringBuffer();
				for (Node node : children) {
					if (node instanceof Element) {
						Element element = (Element) node;
						html.append(element.outerHtml());
						textLength += element.text().length();
						if (textLength >= PAGE_CONTENT_LENGTH) {
							pageContents.add(html.toString());
							textLength = 0;
							html.setLength(0);
						}
					} else if (node instanceof TextNode) {
						TextNode textNode = (TextNode) node;
						String text = textNode.text();
						String[] contents = PARAGRAPH_SEPARATOR_PATTERN.split(text);
						Matcher matcher = PARAGRAPH_SEPARATOR_PATTERN.matcher(text);
						for (String content : contents) {
							if (matcher.find()) {
								content += matcher.group();
							}
							html.append(content);
							textLength += content.length();
							if (textLength >= PAGE_CONTENT_LENGTH) {
								pageContents.add(html.toString());
								textLength = 0;
								html.setLength(0);
							}
						}
					}
				}
				String pageContent = html.toString();
				if (StringUtils.isNotEmpty(pageContent)) {
					pageContents.add(pageContent);
				}
			}
			return pageContents.toArray(new String[pageContents.size()]);
		}
	}

	/**
	 * 获取总页数
	 * 
	 * @return 总页数
	 */
	@Transient
	public int getTotalPages() {
		return getPageContents().length;
	}

}