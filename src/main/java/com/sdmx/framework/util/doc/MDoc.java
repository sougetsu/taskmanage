package com.sdmx.framework.util.doc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class MDoc {

	private Configuration configuration = null;

	@SuppressWarnings("deprecation")
	public MDoc() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
	}

	public void createDoc(Map<String,Object> dataMap,String fileName,String templeName) throws UnsupportedEncodingException {
		//dataMap 要填入模本的数据文件
		configuration.setClassForTemplateLoading(this.getClass(), "/com/sdmx/framework/util/doc/");
		Template t=null;
		try {
			//test.ftl为要装载的模版
			t = configuration.getTemplate(templeName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//输出文档路径及名称
		File outFile = new File(fileName);
		Writer out = null;
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(outFile);
			OutputStreamWriter oWriter = new OutputStreamWriter(fos,"UTF-8");
			//这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不�?��并且包XML文件错误。主要是编码格式不正确，无法解析�?
			//out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
			 out = new BufferedWriter(oWriter); 
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        try {
			t.process(dataMap, out);
			out.close();
			fos.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
