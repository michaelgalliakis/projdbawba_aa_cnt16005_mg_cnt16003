package dbawba.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CaptchaGenerator")
public class CaptchaGenerator extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Example based on: http://zetcode.com/tutorials/jeetutorials/captcha/
        int digits = 5;
        
        int width = 120;
        int height = 40;
        
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2d = bufferedImage.createGraphics();
        
        Font font = new Font("Georgia", Font.BOLD, 18);
        g2d.setFont(font);
        
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        g2d.setRenderingHints(rh);
        
        GradientPaint gp = new GradientPaint(0, 0, Color.red, 0, height / 2, Color.black, true);
        
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);
        
        g2d.setColor(new Color(255, 153, 0));
        
        Random r = new Random();
        char[] chars = new char[digits];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = Character.forDigit(r.nextInt(10), 10);
        }
        
        int x = 0;
        int y = 0;
        
        for (int i = 0; i < chars.length; i++) {
            x += 10 + (Math.abs(r.nextInt()) % 15);
            y = 20 + Math.abs(r.nextInt()) % 20;
            g2d.drawChars(chars, i, 1, x, y);
        }
        
        g2d.dispose();
        
        String captcha = new String(chars);
        request.getSession().setAttribute("captcha", captcha);
        
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(bufferedImage, "png", os);
        os.close();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
