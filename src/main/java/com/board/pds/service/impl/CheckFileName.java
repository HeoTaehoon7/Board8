package com.board.pds.service.impl;

import java.io.File;

public class CheckFileName {
    //  filePath    : d:\\upload\\
	//  orgFileName : 손흥민
	//  fileExt     : .jpg 
		
	public String getCheckFileName(
			String filePath, String orgFileName, String fileExt) {
		
		boolean   isCheck         =  true;
		String    returnFileName  =  null;
		
		String    fullFilePath    =  null;
		File      file            =  null;
		
		String    fileName        =  orgFileName;
		
		int i = 0;
		while( isCheck ) {
			fullFilePath  =  filePath  + fileName + fileExt;
			//  d:\\upload\\ + 손흥민 + _ + i + .jpg
			file          =  new File(  fullFilePath ); 
			if( file.exists() ) {
				i++;
				fileName = String.format("%s_%d", orgFileName, i);  // 손흥민_1
			} else {
				isCheck = false;
			}
		}
		returnFileName  =  fileName + fileExt;
		
		return   returnFileName;
	}

}






