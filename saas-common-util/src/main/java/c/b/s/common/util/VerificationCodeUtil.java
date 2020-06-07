package c.b.s.common.util;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 验证码生成器
 * Created by guiqingqing on 2018/8/10.
 */
public class VerificationCodeUtil {
    private static final int PICTURE_WIDTH = 90;
    private static final int PICTURE_HEIGHT = 30;

    private static final int CODE_COUNT = 4;
    private static final int HORIZONTAL_GAP = 15;
    private static final int CODE_VERTICAL = 22;

    private static final int FONT_HEIGHT = 20;
    private static final String FONT_NAME = "Fixedsys";
    private static final int FONT_STYLE = Font.BOLD + Font.ITALIC;

    private static final int INTERFERE_LINE_COUNT = 20;
    private static final int INTERFERE_LINE_LENGTH = 10;

    private static final char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final Random RANDOM = new Random();

    /**
     * 产生随机数
     * @param bound
     * @return
     */
    private static int random(int bound) {
        return RANDOM.nextInt(bound);
    }

    /**
     * 获取字体
     * @return
     */
    private static Font getFont() {
        return new Font(FONT_NAME, FONT_STYLE, FONT_HEIGHT);
    }

    /**
     * 获取随机颜色
     * @return
     */
    private static Color randomColor(int fc, int bc) {
        int r = fc + random(bc - fc - 16);
        int g = fc + random(bc - fc - 14);
        int b = fc + random(bc - fc - 18);
        return new Color(r, g, b);
    }

    /**
     * 绘制干扰线
     * @param graphics
     * @param color
     */
    private static void randomLine(Graphics graphics, Color color) {
        int x1 = random(PICTURE_WIDTH);
        int y1 = random(PICTURE_HEIGHT);
        int x2 = random(INTERFERE_LINE_LENGTH);
        int y2 = random(INTERFERE_LINE_LENGTH);
        graphics.setColor(color);
        graphics.drawLine(x1, y1, x1 + x2, y1 + y2);
    }

    /**
     * 获取随机字符
     * @return
     */
    private static String randomCode() {
        return String.valueOf(codeSequence[random(codeSequence.length)]);
    }

    /**
     * 填充背景
     * @param graphics
     * @param color
     */
    private static void drawBackground(Graphics graphics, Color color) {
        graphics.setColor(color);
        graphics.fillRect(0, 0, PICTURE_WIDTH, PICTURE_HEIGHT);
    }

    /**
     * 绘制边框
     * @param graphics
     * @param color
     */
    private static void drawBorder(Graphics graphics, Color color) {
        graphics.setColor(color);
        graphics.drawRect(0, 0, PICTURE_WIDTH - 1, PICTURE_HEIGHT - 1);
    }

    /**
     * 绘制验证码并返回
     * @param graphics
     * @return
     */
    private static String drawCode(Graphics graphics) {
        StringBuilder randomCode = new StringBuilder();
        for (int i = 0; i < CODE_COUNT; i++) {
            String code = randomCode();
            graphics.setColor(randomColor(110, 133));
            graphics.drawString(code, (i + 1) * HORIZONTAL_GAP, CODE_VERTICAL);
            randomCode.append(code);
        }
        return randomCode.toString();
    }

    /**
     * 将BufferedImage转成base64字符串
     * @param bufferedImage
     * @return
     * @throws IOException
     */
    private static String generatePictureString(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Image = encoder.encode(outputStream.toByteArray()).replaceAll("[\r\n]", "");
        outputStream.close();
        return "data:image/jpg;base64," + base64Image;
    }

    /**
     * 生成验证码
     *      code: 为生成的验证码
     *      picture: 为生成的验证码图片
     * @return
     */
    public static Map<String, String> generateCodeAndPicture() throws IOException {
        BufferedImage bufferedImage = new BufferedImage(PICTURE_WIDTH, PICTURE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();

        drawBackground(graphics, Color.WHITE);
        drawBorder(graphics, Color.BLACK);

        Font font = getFont();
        graphics.setFont(font);

        for (int i = 0; i < INTERFERE_LINE_COUNT; i++) {
            randomLine(graphics, Color.BLACK);
        }

        String verificationCode = drawCode(graphics);
        String picture = generatePictureString(bufferedImage);

        Map<String, String> map = new HashMap();
        map.put("code", verificationCode);
        map.put("picture", picture);
        return map;
    }

    public static void main(String[] args) throws IOException {
        Map<String, String> map = generateCodeAndPicture();
        System.out.println("验证码的值为: " + map.get("code"));
        System.out.println(map.get("picture"));
    }
}