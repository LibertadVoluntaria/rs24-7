
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Wewt
 */
public class TestParse {

    public static void getRSParams(int world) {
        HashMap<String, String> map = new HashMap<String, String>();

        try {
            String html = readPage(new URL("http://world"+world+".runescape.com/a2,m0,j0,o0"), "http://google.com/");
            Pattern regex = Pattern.compile("<param name=([^\\s]+)\\s+value=([^>]*)>", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            Matcher regexMatcher = regex.matcher(html);
            while (regexMatcher.find()) {
                if (!map.containsKey(regexMatcher.group(1))) {
                    String s1 = regexMatcher.group(1);
                    String s2 = (regexMatcher.group(1).equals("cachesubdirid") ? "0" : regexMatcher.group(2));
                    System.out.println(s1 + "::" + s2);
                    map.put(s1, s2);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static String readPage(URL url, String referer) throws IOException,
            InterruptedException {
        URLConnection uc = url.openConnection();
        uc.addRequestProperty(
                "Accept",
                "text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
        uc.addRequestProperty("Accept-Charset",
                "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
        uc.addRequestProperty("Accept-Encoding", "gzip,deflate");
        uc.addRequestProperty("Accept-Language", "en-gb,en;q=0.5");
        uc.addRequestProperty("Connection", "keep-alive");
        uc.addRequestProperty("Host", "www.runescape.com");
        uc.addRequestProperty("Keep-Alive", "300");
        if (referer != null) {
            uc.addRequestProperty("Referer", referer);
        }
        uc.addRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-GB; rv:1.8.0.6) Gecko/20060728 Firefox       /1.5.0.6      ");
        DataInputStream di = new DataInputStream(uc.getInputStream());
        byte[] buffer = new byte[uc.getContentLength()];
        di.readFully(buffer);
        di.close();
        Thread.sleep(250 + (int) Math.random() * 500);
        return new String(buffer);
    }

    public static void main(String[] args) {
        TestParse.getRSParams(99);
    }
}
