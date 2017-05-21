package com.eollse.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class HtmlConvertTextUtil extends HTMLEditorKit.ParserCallback{
	
	private StringBuffer s;  
	  
    public HtmlConvertTextUtil() {}  
 
    public void parse(Reader in) throws IOException {  
    	s = new StringBuffer();  
        ParserDelegator delegator = new ParserDelegator();  
        delegator.parse(in, this, Boolean.TRUE);  
    }  
 
    public void handleText(char[] text, int pos) {  
    	s.append(text);  
    }  
 
    public String getText() {  
      return s.toString();  
    }  
 
    public static void main (String[] args) {  
      try {  
        // the HTML to convert  
        Reader in=new StringReader("gfsdasds");      
//        FileReader in = new FileReader("java-new.html");  
    	  HtmlConvertTextUtil parser = new HtmlConvertTextUtil();  
        parser.parse(in);  
        in.close();  
        System.out.println(parser.getText());  
      }  
      catch (Exception e) {  
        e.printStackTrace();  
      }  
    }  

    
}
