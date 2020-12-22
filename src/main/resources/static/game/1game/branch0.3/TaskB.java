import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.json.*;


public class myTaskB {
	        Canvas c;
	        Formats f;
	        HistogramData d;
	  	  	Colors color; 
	  	  	Fonts font;
	public void readFromJsonFile(String fileName) {
		try (
			   InputStream is = new FileInputStream( new File( fileName ));
			   JsonReader rdr = Json.createReader(is)
		) {
			   JsonObject obj = rdr.readObject().getJsonObject( "histogramaB" );
			   this.c = getCanvasFrom( obj.getJsonObject( "canvas" ));
			   this.f= getFormatsFrom( obj.getJsonObject( "formats" ));
			   this.d = getDataFrom( obj.getJsonObject( "data" ));
			   this.color=getColors(obj.getJsonObject( "colors" ),this);
			   this.font=getFonts(obj.getJsonObject( "fonts" ));
		} catch (IOException e) {
			         System.out.println( e.getMessage());
		};
	}
	  
	private static Canvas getCanvasFrom( JsonObject obj) {
	      Canvas canvas = new Canvas();
	      JsonArray szArray = obj.getJsonArray( "size" );
	      if (szArray != null ) {  // otherwise, use the default size
	         int[] size = toIntArray( szArray);
	         canvas.x = size[0]; 
	         canvas.y = size[1];
	      } 
	      
	      JsonArray xsArray = obj.getJsonArray( "xscale" );
	      if (xsArray != null )  // otherwise, use the default xScale
	         canvas.xScale = toDoubleArray( xsArray );

	      JsonArray ysArray = obj.getJsonArray( "yscale" );
	      if (ysArray != null )  // otherwise, use the default yScale
	         canvas.yScale = toDoubleArray( ysArray );
	      	     
	      return canvas;
	}
	
	 private static HistogramData getDataFrom( JsonObject obj) {
	      HistogramData data = new HistogramData();
	      data.header = obj.getString( "header", "");
	      data.footer = obj.getString( "footer", "");
	      data.minValue = obj.getJsonNumber( "minvalue").doubleValue(); // TODO for default value
	      data.keys = toStringArray( obj.getJsonArray( "keys"));
	      data.dimOfValue = obj.getJsonNumber( "dimOfValue").intValue();
	      data.annotation=new String[data.dimOfValue];
	      
	      String[] arr=new String[data.dimOfValue];
	      data.values=new double[data.dimOfValue][];
	      for(int i=0;i<data.dimOfValue;i++) {
	    	  arr[i]="value"+i;
	    	  data.values[i]=new double[obj.getJsonArray( arr[i]).size()];
	    	  data.values[i]=toDoubleArray(obj.getJsonArray( arr[i]));
	    	  arr[i]="annotation"+i;
	    	  data.annotation[i]=obj.getString(arr[i],"");
	      }
	      return data;
	 }
	 
	 private static Colors getColors(JsonObject obj,TaskB h) {
		 Colors color=new Colors();
		 int num=h.d.dimOfValue;
		 color.ofBars=new Color[num];
		 color.ofHeader=getColorFrom( obj,"ofHeader",color.ofHeader);
		 color.ofFooter=getColorFrom( obj,"ofFooter",color.ofFooter);
		 color.ofKeys=getColorFrom( obj,"ofKeys",color.ofKeys);
		 color.ofRulers=getColorFrom( obj,"ofRulers",color.ofRulers);
		 color.ofCanva=getColorFrom( obj,"ofCanva",color.ofCanva);
		 color.ofBarFrame=getColorFrom( obj,"ofBarFrame",color.ofBarFrame);
		 color.ofBoarder=getColorFrom( obj,"ofBoarder",color.ofBoarder);
		 String[] arr=new String[num];
		 color.ofBars=new Color[num];
		 for(int i=0;i<num;i++) {
			 arr[i]="Bar"+i;
			
			 color.ofBars[i]=getColorFrom( obj,arr[i],Color.blue);
			 
		 }
		 return color;
	 }
	 
	 private static Color getColorFrom(JsonObject obj,String colorName,Color co) {	 
		 JsonArray cArray = obj.getJsonArray( colorName);
	      if (cArray != null ) {	    	  // otherwise, use the default color
	    	  int[] arr=toIntArray (cArray);
	    	  co= new Color(arr[0],arr[1],arr[2]);
	      }
	     return co;
	    	  
	 }
	 
	 private static Fonts getFonts(JsonObject obj) {
		 Fonts fon=new Fonts();
		 fon.ofHeader=getFontFrom(obj,"ofHeader",fon.ofHeader);
		 fon.ofFooter=getFontFrom(obj,"ofFooter",fon.ofFooter);
		 fon.ofKeys=getFontFrom(obj,"ofKeys",fon.ofKeys);
		 fon.ofRulers=getFontFrom(obj,"ofRulers",fon.ofRulers);
		 return fon;
	 }
	 
	 private static Font getFontFrom(JsonObject obj,String fontName,Font fo) {
		 JsonArray fArray = obj.getJsonArray(fontName);
		 if(fArray!=null) {
		 String[] arr=toStringArray(fArray);
		 int style=Integer.parseInt(arr[1]);
		 int size=Integer.parseInt(arr[2]);
		 fo= new Font(arr[0],style,size);
		 }
		 return fo;
	 }
	  
	private static int[] toIntArray (JsonArray jsa) {
	      int[] a = new int[jsa.size()];
	      for (int i = 0; i < jsa.size(); i++)
	         a[i] = jsa.getInt(i); 
	      return a;
	}

	 private static double[] toDoubleArray (JsonArray jsa) {
	      double[] a = new double[jsa.size()]; 
	      for (int i = 0; i < jsa.size(); i++)
	         a[i] = jsa.getJsonNumber(i).doubleValue(); 
	      return a;
	 }
	 
	 private static String[] toStringArray (JsonArray jsa) {
	      String[] s = new String[jsa.size()]; 
	      for (int i = 0; i < jsa.size(); i++)
	         s[i] = jsa.getString(i); 
	      return s;
	 }
	   
	

	 private static Formats getFormatsFrom( JsonObject obj) {  // TODO for default values
		  Formats fmts = new Formats();
		  fmts.margins = toDoubleArray( obj.getJsonArray( "margins"));
	      fmts.isBarFilled = obj.getBoolean( "isbarfilled");
	      
		  fmts.hasBarFrame = obj.getBoolean( "hasbarframe");
          
		  fmts.hasBorder = obj.getBoolean( "hasborder");
		  
		
		  fmts.hasRightRuler = obj.getBoolean( "isbarfilled");
		 
		  fmts.hasHeader = obj.getBoolean( "hasheader");
		 
		  fmts.hasFooter = obj.getBoolean( "hasfooter");
		 
		    return fmts;
	 }
	  
	 
	 public void draw(Boolean drawGroupedBars ) {
	  StdDraw.setCanvasSize(c.x,c.y);
   	  StdDraw.setXscale(c.xScale[0],c.xScale[1]);
   	  StdDraw.setYscale(c.yScale[0],c.yScale[1]);
   	  StdDraw.clear(color.ofCanva);
   	  double yRange=c.yScale[1]-c.yScale[0];
   	  double xRange=c.xScale[1]-c.xScale[0];
   	  double yTopBoard=(1-f.margins[0])*yRange+c.yScale[0];
   	  double yBottomBoard=f.margins[1]*yRange+c.yScale[0];
   	  double xLeftBoard=f.margins[2]*xRange+c.xScale[0];
   	  double xRightBoard=(1-f.margins[3])*xRange+c.xScale[0];
   	  StdDraw.setFont(font.ofHeader);  StdDraw.setPenColor(color.ofHeader);
   	  StdDraw.text((c.xScale[0]+c.xScale[1])/2, (yTopBoard+c.yScale[1])/2,d.header);
   	  StdDraw.setFont(font.ofFooter);  StdDraw.setPenColor(color.ofFooter);
   	  StdDraw.text((c.xScale[0]+c.xScale[1])/2, (yBottomBoard+c.yScale[0])/2,d.footer);
   	  StdDraw.setPenColor(color.ofBoarder);
   	  StdDraw.rectangle((xLeftBoard+xRightBoard)/2, (yTopBoard+yBottomBoard)/2,
   			  (xRightBoard-xLeftBoard)/2, (yTopBoard-yBottomBoard)/2);
   	  
   	  double minOfValue,maxValue;  minOfValue=maxValue=d.values[0][0];
   	  
   	  for(int i=0;i<d.dimOfValue;i++) {
   		  for(int j=0;j<d.values[i].length;j++) {
   			  if(minOfValue>d.values[i][j]) minOfValue=d.values[i][j];
   			  if(maxValue<d.values[i][j]) maxValue=d.values[i][j];
   		  }
   	  }
   	  
   	  double rulerInterval=(yTopBoard-yBottomBoard)/12;
   	  StdDraw.setFont(font.ofRulers);   StdDraw.setPenColor(color.ofRulers);
   	  StdDraw.text(xLeftBoard-0.05,yBottomBoard ,"0");
   	  for(int i=0;i<12;i++) {
   		  String s=""+String.format("%.3f",(minOfValue+i*(maxValue-minOfValue)/10));
   		  StdDraw.text(xLeftBoard-0.05,yBottomBoard+(i+1)*rulerInterval,s);
   		  StdDraw.line(xLeftBoard, yBottomBoard+(i+1)*rulerInterval,
   				  xLeftBoard+0.05,yBottomBoard+(i+1)*rulerInterval );
   		  if(f.hasRightRuler) {
   			  StdDraw.text(xRightBoard+0.05, yBottomBoard+(i+1)*rulerInterval, s);
   			  StdDraw.line(xRightBoard-0.05,yBottomBoard+(i+1)*rulerInterval ,
   					  xRightBoard,yBottomBoard+(i+1)*rulerInterval);
   		  }
   	  }
   	  
   	  double keysInterval=(xRightBoard-xLeftBoard)/(d.keys.length+1);
   	  StdDraw.setFont(font.ofKeys);   StdDraw.setPenColor(color.ofKeys);
   	  for(int i=0;i<d.keys.length;i++) {
   		  StdDraw.text(xLeftBoard+(i+1)*keysInterval, 
   				  yBottomBoard-(yBottomBoard-c.yScale[0])/5,d.keys[i]);
   	  }
   	  
   
   	  if(drawGroupedBars) {
   		  double barWidth=keysInterval*2/3/d.dimOfValue;
   		  for(int i=0;i<d.dimOfValue;i++) {
   		       		       
   		       for(int j=0;j<d.keys.length;j++) {
   		    	double halfHeight=( (d.values[i][j]-minOfValue)/(maxValue-minOfValue)*10*rulerInterval
   		  			 +rulerInterval)/2;  
   		    	StdDraw.setPenColor(color.ofBars[i]);
   		    	   if(f.isBarFilled) {
   		    	   StdDraw.filledRectangle(
   		    			   xLeftBoard+(j+1-1.0/3)*keysInterval+barWidth*(2*i+1)/2,yBottomBoard+halfHeight,
   		    			   barWidth/2,halfHeight);
   		    	       if(f.hasBarFrame) {
   		    	    	   StdDraw.setPenColor(color.ofBarFrame);
   		    	    	 StdDraw.rectangle(xLeftBoard+(j+1-1.0/3)*keysInterval+barWidth*(2*i+1)/2
   		    	    			 ,yBottomBoard+halfHeight
   	   		    			   ,barWidth/2,halfHeight);
   		    	       }
   		    	   }
   		    	   else
   		    	   StdDraw.rectangle(xLeftBoard+(j+1-1.0/3)*keysInterval+barWidth*(2*i+1)/2,yBottomBoard+halfHeight
   		    			   ,barWidth/2,halfHeight);
   		       }
   		    	   
   		  }
   	  }
   	  
   	  else {
   		  
   		  double barWidth=keysInterval*2/3;
   		  for(int j=0;j<d.keys.length;j++) {
   			int[] arr=sortValues(j);
   			   for(int i=0;i<d.dimOfValue;i++) {
   				 
   				double halfHeight=( (d.values[arr[i]][j]-minOfValue)/(maxValue-minOfValue)*10*rulerInterval
   		  			 +rulerInterval)/2; 
   				  StdDraw.setPenColor(color.ofBars[arr[i]]);
   				  if(f.isBarFilled) {
   				  StdDraw.filledRectangle(xLeftBoard+(j+1)*keysInterval, yBottomBoard+halfHeight
   						  ,barWidth/2, halfHeight);
   				     if(f.hasBarFrame) {
	    	    	     StdDraw.setPenColor(color.ofBarFrame);
	    	    	     StdDraw.rectangle(xLeftBoard+(j+1)*keysInterval,yBottomBoard+halfHeight
   		    			   ,barWidth/2,halfHeight);
	    	         }
   				  }
   				  else
   				  StdDraw.rectangle(xLeftBoard+(j+1)*keysInterval,yBottomBoard+halfHeight 
   						  , barWidth/2, halfHeight);
   			  }
   		  }
   	  }
   	  
   	  double annotationInterval=(c.yScale[1]-yTopBoard)/(d.dimOfValue+1);
   	  StdDraw.setFont(font.ofRulers);
   	  for(int i=0;i<d.dimOfValue;i++) {
   		  StdDraw.setPenColor(color.ofBars[i]);
   		  StdDraw.filledRectangle(xRightBoard-0.05, yTopBoard+(i+1)*annotationInterval,
   				  0.03, annotationInterval/3);
   		  StdDraw.text(xRightBoard+0.03,yTopBoard+(i+1)*annotationInterval ,d.annotation[i]);
   	  }
   	  
   	  
	 } 
	 
	 public int[] sortValues(int num) {
		 ArrayList<Double> list=new ArrayList<>();
		 for(int i=0;i<d.dimOfValue;i++) {
			 list.add(d.values[i][num]);
		 }
		 int[] arr=new int[d.dimOfValue];
		  int index=0; double max=list.get(0);
		 for(int j=0;j<d.dimOfValue;j++) {
		  for(int i=0;i<list.size();i++) {
			 if(max<list.get(i)) {
			      max=list.get(i); index=i;
			 }
		  }
		 arr[j]=index; list.set(index,new Double(-10000000));max=list.get(index);
		 }	
		 
		
		 return arr;
	 }
   	  
}	 
	 
	  
     


class Canvas {
	   int x = 512, y = 512;
	   double[] xScale = { 0, 1.0 };  // MIN, MAX
	   double[] yScale = { 0, 1.0 };  // MIN, MAX	   		  
}

class Formats{
	   double[] margins = { 0.15, 0.15, 0.1, 0.05 };  // NORTH, SOUTH, WEST, EAST
	   boolean isBarFilled = true;
	  
	   boolean hasBarFrame = true;
	   
	   boolean hasBorder = true;
	  
	  
	 
	   boolean hasRightRuler = true;
	  
	   boolean hasHeader = true;
	   
	   boolean hasFooter = true;
	 
}

class HistogramData {
	   String header = "";
	   String footer = "";
	   double minValue = 0.0;
	   String[] keys = { };
	   int dimOfValue;
	   double[][] values;
	   String[] annotation;
}

class Colors{
	
	Color ofHeader=Color.white;
	Color ofFooter=Color.white;;
	Color ofKeys=Color.white;
	Color ofRulers=Color.white;
	Color ofCanva=Color.black;
	Color[] ofBars;
	Color ofBarFrame=Color.blue;
	Color ofBoarder=Color.green;
}

class Fonts{
	Font ofHeader=new Font("consolas", Font.PLAIN, 23);
	Font ofFooter=new Font("consolas", Font.PLAIN, 23);
	Font ofKeys=new Font("consolas", Font.PLAIN, 15);
	Font ofRulers=new Font("consolas", Font.PLAIN, 10);	
}