package util;

import java.awt.Color;  
import java.awt.Font;  
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.image.BufferedImage;  
import java.util.HashMap;  
import java.util.Map;  
import java.util.Random;  

public class VerificationCodeTool {  
    //LOG  
//    private static final Logger LOG =Logger.getLogger(VerificationCodeTool.class);  
    //verification code image width  
    private static final int IMG_WIDTH=146;  
    //verification code image height  
    private static final int IMG_HEIGHT=30;  
    //The number of interference lines  
    private static final int DISTURB_LINE_SIZE = 15;  
    //generate a random number  
    private Random random = new Random();  
    //result  
    private int xyresult;  
    //result random string  
    private String randomString;  
    //Chinese Numbers  
//  private static final String [] CNUMBERS = "零,一,二,三,四,五,六,七,八,九,十".split(",");  
    //零一二三四五六七八九十乘除加减  
    //Here, must be java Unicode code  
    private static final String CVCNUMBERS = "\u96F6\u4E00\u4E8C\u4E09\u56DB\u4E94\u516D\u4E03\u516B\u4E5D\u5341\u4E58\u9664\u52A0\u51CF";  
    //Definition of drawings in the captcha characters font, font name, font style, font size  
    //static final font : In Chinese characters garbled  
    private final Font font = new Font("黑体", Font.BOLD, 18);  
    //data operator  
    private static final Map<String, Integer> OPMap = new HashMap<String, Integer>();  
      
    static{  
        OPMap.put("*", 11);  
        OPMap.put("/", 12);  
        OPMap.put("+", 13);  
        OPMap.put("-", 14);  
    }  
    /** 
     * The generation of image  verification code 
     * */  
    public BufferedImage drawVerificationCodeImage(){  
        //image  
        BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);  
        //In memory to create a brush  
        Graphics g = image.getGraphics();  
        //Set the brush color  
//      g.setColor(getRandomColor(200,250));  
        g.setColor(Color.WHITE);  
        //Fill the background color, using the current brush colour changing background color images  
        //The meaning of the four parameters respectively, starting x coordinates, starting y, width, height.  
        //image background  
        g.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);  
        //Set the brush color  
        g.setColor(getRandomColor(200,250));  
        //image border  
        g.drawRect(0, 0, IMG_WIDTH-2, IMG_HEIGHT-2);  
          
        //Set disturb line color  
        g.setColor(getRandomColor(110, 133));  
        //Generate random interference lines  
        for(int i =0;i < DISTURB_LINE_SIZE; i++){  
            drawDisturbLine1(g);  
            drawDisturbLine2(g);  
        }  
        //Generate a random number, set return data  
        getRandomMathString();  
        System.out.println("验证码 : "+randomString);  
        System.out.println("验证码结果 : "+xyresult);  
        //The generated random string used to save the system  
        StringBuffer logsu = new StringBuffer();  
        for(int j=0,k = randomString.length(); j < k; j++){  
          int chid = 0;  
          if(j==1){  
              chid = OPMap.get(String.valueOf(randomString.charAt(j)));  
          }else{  
              chid = Integer.parseInt(String.valueOf(randomString.charAt(j)));  
          }  
          String ch = String.valueOf(CVCNUMBERS.charAt(chid));  
          logsu.append(ch);  
          drawRandomString((Graphics2D)g,ch, j);  
        }  
        //= ?  
        drawRandomString((Graphics2D)g,"\u7B49\u4E8E\uFF1F", 3);  
        logsu.append("\u7B49\u4E8E \uFF1F");  
        System.out.println("汉字验证码 : "+logsu);  
        randomString = logsu.toString();  
        //Release the brush object  
        g.dispose();  
        return image;  
    }  
    /** 
     * Get a random string 
     * */  
    private void getRandomMathString(){  
        //Randomly generated number 0 to 10  
        int xx = random.nextInt(10);  
        int yy = random.nextInt(10);  
        //save getRandomString  
        StringBuilder suChinese =  new StringBuilder();  
            //random 0,1,2  
            int Randomoperands = (int) Math.round(Math.random()*2);  
            //multiplication  
            if(Randomoperands ==0){  
                this.xyresult = yy * xx;  
//              suChinese.append(CNUMBERS[yy]);  
                suChinese.append(yy);  
                suChinese.append("*");  
                suChinese.append(xx);  
            //division, divisor cannot be zero, Be divisible  
            }else if(Randomoperands ==1){  
                if(!(xx==0) && yy%xx ==0){  
                    this.xyresult = yy/xx;  
                    suChinese.append(yy);  
                    suChinese.append("/");  
                    suChinese.append(xx);  
                }else{  
                    this.xyresult = yy + xx;  
                    suChinese.append(yy);  
                    suChinese.append("+");  
                    suChinese.append(xx);  
                }  
            //subtraction  
            }else if(Randomoperands ==2){  
                    this.xyresult = yy - xx;  
                    suChinese.append(yy);  
                    suChinese.append("-");  
                    suChinese.append(xx);  
            //add  
            }else{  
                    this.xyresult = yy + xx;  
                    suChinese.append(yy);  
                    suChinese.append("+");  
                    suChinese.append(xx);  
            }  
        this.randomString = suChinese.toString();  
    }  
    /** 
     * Draw a random string 
     * @param g Graphics 
     * @param randomString random string 
     * @param i the random number of characters 
     * */  
    public void drawRandomString(Graphics2D g,String randomvcch,int i){  
        //Set the string font style  
        g.setFont(font);  
        //Set the color string  
        int rc = random.nextInt(255);  
        int gc = random.nextInt(255);  
        int bc = random.nextInt(255);  
        g.setColor(new Color(rc, gc, bc));  
        //random string  
        //Set picture in the picture of the text on the x, y coordinates, random offset value  
        int x = random.nextInt(3);  
        int y = random.nextInt(2);  
        g.translate(x, y);  
        //Set the font rotation angle  
        int degree = new Random().nextInt() % 15;  
        //Positive point of view  
        g.rotate(degree * Math.PI / 180, 5+i*25, 20);  
        //Character spacing is set to 15 px  
        //Using the graphics context of the current font and color rendering by the specified string for a given text.  
        //The most on the left side of the baseline of the characters in the coordinate system of the graphics context (x, y) location  
        //str- to draw string.x - x coordinate.y - y coordinate.  
        g.drawString(randomvcch, 5+i*25, 20);  
        //Reverse Angle  
        g.rotate(-degree * Math.PI / 180, 5+i*25, 20);  
    }  
    /** 
     *Draw line interference  
     *@param g Graphics 
     * */  
    public void drawDisturbLine1(Graphics g){  
        int x1 = random.nextInt(IMG_WIDTH);  
        int y1 = random.nextInt(IMG_HEIGHT);  
        int x2 = random.nextInt(13);  
        int y2 = random.nextInt(15);  
        //x1 - The first point of the x coordinate.  
        //y1 - The first point of the y coordinate  
        //x2 - The second point of the x coordinate.  
        //y2 - The second point of the y coordinate.  
        //X1 and x2 is the starting point coordinates, x2 and y2 is end coordinates.  
        g.drawLine(x1, y1, x1 + x2, y1 + y2);  
    }  
      
    /** 
     *Draw line interference  
     *@param g Graphics 
     * */  
    public void drawDisturbLine2(Graphics g){  
        int x1 = random.nextInt(IMG_WIDTH);  
        int y1 = random.nextInt(IMG_HEIGHT);  
        int x2 = random.nextInt(13);  
        int y2 = random.nextInt(15);  
        //x1 - The first point of the x coordinate.  
        //y1 - The first point of the y coordinate  
        //x2 - The second point of the x coordinate.  
        //y2 - The second point of the y coordinate.  
        //X1 and x2 is the starting point coordinates, x2 and y2 is end coordinates.  
        g.drawLine(x1, y1, x1 - x2, y1 - y2);  
    }  
    /** 
     * For random color 
     * @param fc fc 
     * @param bc bc 
     * @return color random color 
     * */  
    public Color getRandomColor(int fc,int bc){  
        if(fc > 255){  
            fc = 255;  
        }  
        if(bc > 255){  
            bc = 255;  
        }  
        //Generate random RGB trichromatic  
        int r = fc+random.nextInt(bc -fc - 16);  
        int g = fc+random.nextInt(bc - fc - 14);  
        int b = fc+random.nextInt(bc - fc - 18);  
        return new Color(r, g, b);  
    }  
      
    /** 
     * xyresult.<br/> 
     * 
     * @return  the xyresult <br/> 
     *  
     */  
    public int getXyresult() {  
        return xyresult;  
    }  
      
    /** 
     * randomString.<br/> 
     * 
     * @return  the randomString <br/> 
     *  
     */  
    public String getRandomString() {  
        return randomString;  
    }  
      
}  