package net.shenzhou;
import java.util.Random;


/**
 * 系统常量
 */
public class Colour {
	
	
   static String [] colour = new String [] {"#e53637","#ffb711","#ffe533","#a6d030","#36b9e2","#017cff","#724eb6","#ff8888","#ffde91","#dcff7a","#94e6ff","#7ebcff","#bc99ff","#e3e3e3","#d3d3d3","#85fae8","#3cedd2"};
   
   public static String getColour(){
	   Random random = new Random();
       int s = random.nextInt(colour.length-1);
       
//       jxl.write.Colour.RED;
//       jxl.write.Colour.getInternalColour(s)
	   return colour[s];
	   
   }
   
	
	
	
}






