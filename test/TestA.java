
import java.lang.reflect.Method;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Wewt
 */
public class TestA {

    public TestA() {
        doMethod("ShowOne", null);
    }

    public void doMethod(String methName, Object[] params) {
        try {
            Method m = TestA.class.getMethod(methName, null);
            m.invoke(this, params);
        } catch (Exception e) {
        }

    }

    public void ShowOne() {
        System.out.println(1);
    }

    public static void main(String[] args) {
        new TestA();
    }
}
