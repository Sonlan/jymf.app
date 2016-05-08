package org.jymf.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class FileManager {
	/**
	 * 读取
	 * @param file
	 * @return
	 */
	public String readFile(File file){
	    if(!file.exists()){
	    	return "";
	    }
	    BufferedReader reader = null;
	    StringBuilder laststr = new StringBuilder();
	    try {
	        reader = new BufferedReader(new FileReader(file));
	        String tempString = null;
	        //一次读入一行，直到读入null为文件结束
	        
	        while ((tempString = reader.readLine()) != null) {
	        	laststr.append(new String(tempString.getBytes(),"UTF-8") + "\n");
	        }
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e1) {}
	        }
	    }
	    return laststr.toString();
	}
}
