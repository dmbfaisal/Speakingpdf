package com.example.speakingpdf;

import java.io.IOException;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;




public class TextExtracter {
	private int pageNo;
	
	public int getPageNo(){
		return pageNo;
	}
	public int getTotalPageInDoc(String path){
		try {
			PdfReader reader = new PdfReader(path);
			return reader.getNumberOfPages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public String getText(String path, int pageNo) {
		int length;
		this.pageNo=pageNo;
		String content;
		try {
			PdfReader reader = new PdfReader(path);
			length= reader.getNumberOfPages();
			
			if(pageNo<=length){
				content= PdfTextExtractor.getTextFromPage(reader, pageNo);
				return content;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return null;
	}

}
