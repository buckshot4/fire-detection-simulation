/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddedsystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author eleonora
 */
public class FileInport {
    
    static ArrayList<String> outputs=new ArrayList<String>();
    static int[][] values= new int[8][121];
    static String[] fileNames= new String[]{
        "output.txt","output2.txt","output3.txt", "output4.txt","output5.txt","output6.txt","outputMinR.txt","outPutMinR2.txt"
    };
    
   public static void InportFile(){
       
   
     for(int f=0;f<7;f++){
                int x=0;
		BufferedReader br = null;
		FileReader fr = null;

		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader("/Users/eleonora/Desktop/DTU/Embedded/gitHub/fire/fire-detection-simulation/data/"+ fileNames[f]);
			br = new BufferedReader(fr);

			String sCurrentLine;
                     //   while(x<10){
                           if((sCurrentLine = br.readLine())!=null){
                                outputs.add(sCurrentLine);
                                
				System.out.println(sCurrentLine);
                                x++;
                          //  }
                        }
                        
                System.out.println("FInished  "+ fileNames[f]);
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
     }
                
                
            for(int i=0;i<outputs.size();i++){
         
                        String str =outputs.get(i);
                        String [] arrStr=str.split(";");
                        
                                   for (int j=0;j<arrStr.length-1;j++){
                                       if((arrStr[j].equalsIgnoreCase("end"))){
                                           
                                       }else{
                                           values[i][j]=Integer.parseInt(arrStr[j]);
                                       }
                                       
                    }
                }
                
                for(int i=0;i<8;i++){
                    for(int j=0;j<121;j++){
                        System.out.print(values[i][j]+" ");
                    }
                    System.out.println(" ");
                }
	}
  

    }

 
    

