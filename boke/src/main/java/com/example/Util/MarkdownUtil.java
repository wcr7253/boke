package com.example.Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownUtil
{
	/**
	 * markdown格式转换为html
	 * @param markdown
	 * @return
	 * @author wcr
	 * */
	public static String markdownToHtml(String markdown)
	{
		Parser parser = Parser.builder().build();
		Node document = parser.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(document);
	}
	
	/**
	 * markdown格式转换为html(标题生成、表格生成)
	 * @param markdown
	 * @return
	 * @author wcr
	 * */
	public static String markdownToHtmlExtensions(String markdown) 
	{
		//h标题生成id
		Set<Extension> headingAnchorExtensions=Collections.singleton(HeadingAnchorExtension.create());
		//转成table的html
		List<Extension> tableExtension=Arrays.asList(TablesExtension.create());
		Parser parser = Parser.builder()
		        .extensions(tableExtension)
		        .build();
		Node document=parser.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder()
		        .extensions(headingAnchorExtensions)
		        .extensions(tableExtension)
		        .attributeProviderFactory(new AttributeProviderFactory() {
					
					@Override
					public AttributeProvider create(AttributeProviderContext context)
					{
						// TODO Auto-generated method stub
						return new CustomAttributeProvider();
					}
				})
		        .build();
		return renderer.render(document);
	}
	
	public static class CustomAttributeProvider implements AttributeProvider
	{

		@Override
		public void setAttributes(Node node, String tagName, Map<String, String> attributes)
		{
			// TODO Auto-generated method stub
			if(node instanceof Link)
			{
				attributes.put("target","_blank");
			}
			if(node instanceof TableBlock)
			{
				attributes.put("class", "ui celled table");
			}
		}
		
	}
}
