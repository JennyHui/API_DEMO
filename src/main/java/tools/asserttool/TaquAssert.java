package tools.asserttool;

import tools.configuration.LoggerControler;
import org.testng.Assert;

import java.lang.reflect.Array;
import java.util.regex.Pattern;

import static org.testng.internal.EclipseInterface.*;

/**
 * 继承testng 的Assert类，同时做了扩展，添加了
 * 1.两个字符串包含关系校验
 * 2.字符串是否与正则匹配
 * 3.字符串是以XX开头或者以XX结尾
 */
public class TaquAssert extends Assert {
    private static LoggerControler logger = LoggerControler.getLogger(TaquAssert.class);

    private TaquAssert() {
    }

    /**
     * 断言是否为true
     *
     * @param message   消息
     * @param condition 断言情况
     */
    public static void assertTrue(String message, Boolean condition) {
        StringBuilder msg = new StringBuilder("assertTrue(String message, Boolean condition):\n");
        if (condition) {
            msg.append(message + "条件断言正确！实际值为true！");
            logger.info(msg);
        } else {
            msg.append(message + "条件断言错误！实际值为false!");
            logger.error(msg);
            failNotEquals(Boolean.valueOf(condition), Boolean.TRUE, message);
        }
    }

    /**
     * 断言是否为true
     *
     * @param condition 断言情况
     */
    static public void assertTrue(boolean condition) {
        assertTrue(condition, null);
    }

    /**
     * 断言是否为false
     *
     * @param message   消息
     * @param condition 断言情况
     */
    public static void assertFalse(String message, boolean condition) {
        StringBuilder msg = new StringBuilder("assertFalse(String message, boolean condition):");
        if (!condition) {
            msg.append(message + ":条件断言正确！实际值为false！");
            logger.info(msg);
        } else {
            msg.append(message + ":条件断言错误！实际值为true!");
            logger.error(msg);
            failNotEquals(Boolean.valueOf(condition), Boolean.FALSE, message);
        }
    }

    /**
     * 断言是否为false
     *
     * @param condition 断言情况
     */
    public static void assertFalse(boolean condition) {
        assertFalse(condition, null);
    }

    /**
     * 断言两个对象是否相等
     *
     * @param actual
     * @param expected
     * @param message
     */
    public static void assertEquals(String message, Object actual, Object expected) {
        StringBuilder msg = new StringBuilder("assertEquals(String message, Object actual, Object expected):");
        if ((expected == null) && (actual == null)) {
            return;
        }
        if (expected != null) {
            if (expected.getClass().isArray()) {
                assertArrayEquals(message, actual, expected);
                msg.append(message + ":断言成功\n" + "实际值：" + actual + "\n预期值：" + expected);
                logger.info(msg);
                return;
            } else if (expected.equals(actual)) {
                msg.append(message + ":断言成功\n" + "实际值：" + actual + "\n预期值：" + expected);
                logger.info(msg);
                return;
            }
        }
        failNotEquals(actual, expected, message);
    }

    /**
     * 断言两个对象是否相等
     *
     * @param actual   实际值
     * @param expected 预期值
     * @param message  断言错误的消息
     */
    private static void assertArrayEquals(String message, Object expected, Object actual) {
        StringBuilder msg = new StringBuilder("assertArrayEquals(String message, Object actual, Object expected):");
        //is called only when expected is an array
        if (actual.getClass().isArray()) {
            int expectedLength = Array.getLength(expected);
            if (expectedLength == Array.getLength(actual)) {
                for (int i = 0; i < expectedLength; i++) {
                    Object _actual = Array.get(actual, i);
                    Object _expected = Array.get(expected, i);
                    try {
                        assertEquals(_actual, _expected);
                    } catch (AssertionError ae) {
                        msg.append(message + ":断言失败\n" + "实际值：" + actual + "\n预期值：" + expected);
                        logger.error(msg);
                        failNotEquals(actual, expected, message == null ? "" : message
                                + " (第 " + i + " 个值不相同)");
                    }
                }
                //array values matched
                return;
            } else {
                msg.append(message + ":断言失败\n" + "实际值：" + actual + "\n预期值：" + expected);
                logger.error(msg);
                failNotEquals(Array.getLength(actual), expectedLength, message == null ? "" : message
                        + " (数组长度不相同)");
            }
        }
        failNotEquals(actual, expected, message);
    }

    /**
     * 断言两个对象是否相等
     *
     * @param actual   预期值
     * @param expected 实际值
     */
    public static void assertEquals(Object actual, Object expected) {
        assertEquals(null, actual, expected);
    }


    /**
     * 断言两个字符串是否一致
     *
     * @param message  断言错误消息
     * @param actual   真实值
     * @param expected 预期值
     */
    public static void assertEquals(String message, String expected, String actual) {
        assertEquals(message, (Object) actual, (Object) expected);
    }

    /**
     * 断言两个字符串是否一致
     *
     * @param actual   真实值
     * @param expected 预期值
     */
    public static void assertEquals(String actual, String expected) {
        assertEquals(null, actual, expected);
    }


    /**
     * 断言预期值和真实值的差的绝对值是否在规定范围内
     *
     * @param message  消息
     * @param actual   真实值
     * @param expected 预期值
     * @param delta    预期值和真实值的绝对值差
     */
    public static void assertEquals(String message, double actual, double expected, double delta) {
        StringBuilder msg = new StringBuilder("String message, double actual, double expected, double delta:");
        // handle infinity specially since subtracting to infinite values gives NaN and the
        // the following test fails
        if (Double.isInfinite(expected)) {
            if (!(expected == actual)) {
                msg.append(message + ":断言失败\n" + "实际值：" + actual + "\n预期值：" + expected + "\n绝对值差" + delta);
                logger.error(msg);
                failNotEquals(new Double(actual), new Double(expected), message);
            } else {
                msg.append(message + ":断言成功\n" + "实际值：" + actual + "\n预期值：" + expected + "\n绝对值差" + delta);
                logger.info(msg);
            }
        } else if (!(Math.abs(expected - actual) <= delta)) { // Because comparison with NaN always returns false
            msg.append(message + ":断言失败\n" + "实际值：" + actual + "\n预期值：" + expected + "\n绝对值差" + delta);
            logger.error(msg);
            failNotEquals(new Double(actual), new Double(expected), message);
        }
    }

    /**
     * 断言预期值和真实值的差的绝对值是否在规定范围内
     *
     * @param actual   真实值
     * @param expected 预期值
     * @param delta    预期值和真实值的绝对值差
     */
    static public void assertEquals(double actual, double expected, double delta) {
        assertEquals(null, actual, expected, delta);
    }

    /**
     * 断言预期值和真实值的差的绝对值是否在规定范围内
     *
     * @param message  消息
     * @param actual   真实值
     * @param expected 预期值
     * @param delta    预期值和真实值的绝对值差
     */
    static public void assertEquals(String message, float actual, float expected, float delta) {
        StringBuilder msg = new StringBuilder("String message, float actual, float expected, float delta:");

        // handle infinity specially since subtracting to infinite values gives NaN and the
        // the following test fails
        if (Float.isInfinite(expected)) {
            if (!(expected == actual)) {
                msg.append(message + ":断言失败\n" + "实际值：" + actual + "\n预期值：" + expected + "\n绝对值差" + delta);
                logger.error(msg);
                failNotEquals(new Float(actual), new Float(expected), message);
            } else {
                msg.append(message + ":断言成功\n" + "实际值：" + actual + "\n预期值：" + expected + "\n绝对值差" + delta);
                logger.info(msg);
            }
        } else if (!(Math.abs(expected - actual) <= delta)) {
            msg.append(message + ":断言失败\n" + "实际值：" + actual + "\n预期值：" + expected + "\n绝对值差" + delta);
            logger.error(msg);
            failNotEquals(new Float(actual), new Float(expected), message);
        }
    }

    /**
     * 断言预期值和真实值的差的绝对值是否在规定范围内
     *
     * @param actual   真实值
     * @param expected 预期值
     * @param delta    预期值和真实值的绝对值差
     */
    static public void assertEquals(float actual, float expected, float delta) {
        assertEquals(null, actual, expected, delta);
    }

    /**
     * 断言两个long类型值是否相等
     *
     * @param message  断言错误消息
     * @param actual   真实值
     * @param expected 预期值
     */
    static public void assertEquals(String message, long actual, long expected) {
        assertEquals(message, Long.valueOf(actual), Long.valueOf(expected));
    }

    /**
     * 断言两个long类型值是否相等
     *
     * @param actual   真实值
     * @param expected 预期值
     */
    static public void assertEquals(long actual, long expected) {
        assertEquals(null, actual, expected);
    }

    /**
     * 断言两个boolen 类型是否一样
     *
     * @param message  消息
     * @param actual   实际值
     * @param expected 预期值
     */
    public static void assertEquals(String message, boolean actual, boolean expected) {
/*        StringBuilder msg = new StringBuilder("assertEqual(boolean actual,boolean expect,String message):");
        if (actual == expected) {
            msg.append("断言正确！预期值和实际值均为:" + actual);
            logger.info(msg);
        } else {
            msg.append(message + ":");
            msg.append("断言失败！预期值为：" + expected + "实际值为：" + actual);
            logger.error(msg);
            failNotEquals(actual, expected, message);
        }*/
        assertEquals(message, Boolean.valueOf(actual), Boolean.valueOf(expected));
    }

    /**
     * 断言两个boolen 类型是否一样
     *
     * @param actual   实际值
     * @param expected 预期值
     */
    public static void assertEquals(boolean actual, boolean expected) {
        assertEquals(actual, expected, null);
    }

    /**
     * 断言两个byte 类型是否一样
     *
     * @param message  消息
     * @param actual   实际值
     * @param expected 预期值
     */
    static public void assertEquals(String message, byte actual, byte expected) {
        assertEquals(message, Byte.valueOf(actual), Byte.valueOf(expected));
    }

    /**
     * 断言两个byte 类型是否一样
     *
     * @param actual   实际值
     * @param expected 预期值
     */
    static public void assertEquals(byte actual, byte expected) {
        assertEquals(null, Byte.valueOf(actual), Byte.valueOf(expected));
    }

    /**
     * 断言两个char 类型是否一样
     *
     * @param message  消息
     * @param actual   实际值
     * @param expected 预期值
     */
    static public void assertEquals(String message, char actual, char expected) {
        assertEquals(message, Character.valueOf(actual), Character.valueOf(expected));
    }

    /**
     * 断言两个char 类型是否一样
     *
     * @param actual   实际值
     * @param expected 预期值
     */
    static public void assertEquals(char actual, char expected) {
        assertEquals(null, actual, expected);
    }

    /**
     * 断言两个 short 类型值是否一样
     *
     * @param message  消息
     * @param actual   实际值
     * @param expected 预期值
     */
    static public void assertEquals(String message, short actual, short expected) {
        assertEquals(message, Short.valueOf(actual), Short.valueOf(expected));
    }

    /**
     * 断言两个 short 类型值是否一样
     *
     * @param actual   实际值
     * @param expected 预期值
     */
    static public void assertEquals(short actual, short expected) {
        assertEquals(null, actual, expected);
    }

    /**
     * 断言两个 int 类型值是否一样
     *
     * @param message  消息
     * @param actual   实际值
     * @param expected 预期值
     */
    static public void assertEquals(String message, int actual, int expected) {
        assertEquals(message, Integer.valueOf(actual), Integer.valueOf(expected));
    }

    /**
     * 断言两个 int 类型值是否一样
     *
     * @param actual   实际值
     * @param expected 预期值
     */
    static public void assertEquals(int actual, int expected) {
        assertEquals(null, actual, expected);
    }

    //=============================assertNotEquals=====================================================
    public static void assertNotEquals(String message, Object actual1, Object actual2) {
        StringBuilder msg = new StringBuilder("assertNotEquals(String message, Object actual1, Object actual2):");
        boolean fail = false;
        try {
            TaquAssert.assertEquals(actual1, actual2);
            fail = true;
        } catch (AssertionError e) {
        }

        if (fail) {
            msg.append(message + "断言失败:" + "\n预期值为：" + actual2 + "\n真实值为：" + actual1);
            logger.error(msg);
            TaquAssert.fail(message);
        }
    }


    public static void assertNotEquals(String message, String actual1, String actual2) {
        assertNotEquals(message, (Object) actual1, (Object) actual2);
    }

    static void assertNotEquals(String actual1, String actual2) {
        assertNotEquals(null, actual1, actual2);
    }

    static void assertNotEquals(long actual1, long actual2, String message) {
        assertNotEquals(message, Long.valueOf(actual1), Long.valueOf(actual2));
    }

    static void assertNotEquals(long actual1, long actual2) {
        assertNotEquals(null, actual1, actual2);
    }

    static void assertNotEquals(boolean actual1, boolean actual2, String message) {
        assertNotEquals(message, Boolean.valueOf(actual1), Boolean.valueOf(actual2));
    }

    static void assertNotEquals(boolean actual1, boolean actual2) {
        assertNotEquals(null, actual1, actual2);
    }

    static void assertNotEquals(byte actual1, byte actual2, String message) {
        assertNotEquals(message, Byte.valueOf(actual1), Byte.valueOf(actual2));
    }

    static void assertNotEquals(byte actual1, byte actual2) {
        assertNotEquals(null, actual1, actual2);
    }

    static void assertNotEquals(char actual1, char actual2, String message) {
        assertNotEquals(message, Character.valueOf(actual1), Character.valueOf(actual2));
    }

    static void assertNotEquals(char actual1, char actual2) {
        assertNotEquals(null, actual1, actual2);
    }

    static void assertNotEquals(short actual1, short actual2, String message) {
        assertNotEquals(message, Short.valueOf(actual1), Short.valueOf(actual2));
    }

    static void assertNotEquals(short actual1, short actual2) {
        assertNotEquals(null, actual1, actual2);
    }

    static void assertNotEquals(int actual1, int actual2, String message) {
        assertNotEquals(message, Integer.valueOf(actual1), Integer.valueOf(actual2));
    }

    static void assertNotEquals(int actual1, int actual2) {
        assertNotEquals(null, actual1, actual2);
    }

    static public void assertNotEquals(String message, float actual1, float actual2, float delta) {
        StringBuilder msg = new StringBuilder("assertNotEquals:");

        boolean fail = false;
        try {
            TaquAssert.assertEquals(message, actual1, actual2, delta);
            fail = true;
        } catch (AssertionError e) {

        }

        if (fail) {
            msg.append(message + "断言失败" + "\n预期值为：" + actual2 + "\n真实值为：" + actual1 + "\n绝对值范围" + delta);
            logger.error(msg);
            TaquAssert.fail(message);
        }
    }

    static public void assertNotEquals(float actual1, float actual2, float delta) {
        assertNotEquals(null, actual1, actual2, delta);
    }

    static public void assertNotEquals(String message, double actual1, double actual2, double delta) {
        StringBuilder msg = new StringBuilder("assertNotEquals:");
        boolean fail = false;
        try {
            TaquAssert.assertEquals(message, actual1, actual2, delta);
            fail = true;
        } catch (AssertionError e) {

        }

        if (fail) {
            msg.append(message + "断言失败" + "\n预期值为：" + actual2 + "\n真实值为：" + actual1 + "\n绝对值范围" + delta);
            logger.error(msg);

            TaquAssert.fail(message);
        }
    }

    static public void assertNotEquals(double actual1, double actual2, double delta) {
        assertNotEquals(null, actual1, actual2, delta);
    }


    //=============================assertNull=====================================================

    /**
     * 断言不为 null
     *
     * @param message 断言错误消息
     * @param object  断言对象
     */
    static public void assertNotNull(String message, Object object) {
        StringBuilder msg = new StringBuilder("assertNotNull(String message,Object object):");

        if (object == null) {
            String formatted = "";
            if (message != null) {
                formatted = message + " ";
            }
            msg.append(message + ":");
            logger.error(msg + "断言失败");
            fail(formatted + "预期的对象不能为空");
        }
        assertTrue(object != null, message);
    }

    /**
     * 断言不为 null
     *
     * @param object 断言对象
     */
    static public void assertNotNull(Object object) {
        assertNotNull(null, object);
    }

    /**
     * 断言为 null
     *
     * @param message 断言错误消息
     * @param object  断言对象
     */
    static public void assertNull(String message, Object object) {
        StringBuilder msg = new StringBuilder("assertNull(String message,Object object):");

        if (object != null) {
            msg.append(message + ":");
            logger.error(msg + "断言失败");
            failNotSame(message, object, null);
        }
    }

    /**
     * 断言为 null
     *
     * @param object 断言对象
     */
    static public void assertNull(Object object) {
        assertNull(null, object);
    }


    //======================================assertInclude=================================

    /**
     * 断言字符串中是否包含某些字符
     *
     * @param message  断言错误消息
     * @param content  断言的字符串为
     * @param included 包含的字符串
     */
    public static void assertInclude(String message, String content, String included) {
        StringBuilder msg = new StringBuilder("assertInclude(String content,String included):\n");
        if (content.contains(included)) {
            msg.append(message + ":包含校验正确\n校验字符串为：" + content + "\n包含字符串为：" + included);
            logger.info(msg);
        } else {
            msg.append(message + ":包含校验失败\n校验字符串为：" + content + "\n包含字符串为：" + included);
            logger.error(msg);
            TaquAssert.fail(message);
        }
    }

    /**
     * 断言字符串中是否包含某些字符
     *
     * @param content  断言的字符串为
     * @param included 包含的字符串
     */
    public static void assertInclude(String content, String included) {
        TaquAssert.assertInclude(null, content, included);
    }

    //======================================assertNotInclude=================================

    /**
     * 断言字符串中不包含某些字符
     *
     * @param message  断言错误消息
     * @param content  断言的字符串为
     * @param included 不包含的字符串
     */
    public static void assertNotInclude(String message, String content, String included) {
        StringBuilder msg = new StringBuilder("assertNotInclude(String content,String included):\n");
        if (!content.contains(included)) {
            msg.append(message + ":不包含校验正确\n校验字符串为：" + content + "\n包含字符串为：" + included);
            logger.info(msg);
        } else {
            msg.append(message + ":不包含校验失败\n校验字符串为：" + content + "\n包含字符串为：" + included);
            logger.error(msg);
            TaquAssert.fail(message);
        }
    }

    /**
     * 断言字符串中是否不包含某些字符
     *
     * @param content  断言的字符串为
     * @param included 包含的字符串
     */
    public static void assertNotInclude(String content, String included) {
        TaquAssert.assertNotInclude(null, content, included);
    }

    //======================================assertMatch=================================

    /**
     * 根据正则表达式断言是否匹配
     *
     * @param message 断言错误信息
     * @param matcher 待校验的字符串
     * @param regex   校验的正则表达式
     */
    public static void assertMatch(String message, String matcher, String regex) {
        StringBuilder msg = new StringBuilder("assertMatch(String actual,String expect,String message):\n");
        if (Pattern.matches(regex, matcher)) {
            msg.append(message + ":匹配校验成功！\n待校验的字符串为:" + matcher + "\n校验的正则表达式为:" + regex);
            logger.info(msg);
        } else {
            msg.append(message + ":匹配校验失败！\n待校验的字符串为:" + matcher + "\n校验的正则表达式为:" + regex);
            logger.error(msg);
            TaquAssert.fail(message);
        }
    }

    /**
     * 根据正则表达式断言是否匹配
     *
     * @param matcher 待校验的字符串
     * @param regex   校验的正则表达式
     */
    public static void assertMatch(String matcher, String regex) {
        TaquAssert.assertMatch(null, matcher, regex);
    }

    //======================================assertNotMatch=================================

    /**
     * 根据正则表达式断言不匹配待校验的字符串
     *
     * @param message 断言错误信息
     * @param matcher 待校验的字符串
     * @param regex   校验的正则表达式
     */
    public static void assertNotMatch(String message, String matcher, String regex) {
        StringBuilder msg = new StringBuilder("assertNotMatch(String actual,String expect,String message):\n");
        if (!Pattern.matches(regex, matcher)) {
            msg.append(message + ":匹配校验成功！\n待校验的字符窜为:" + matcher + "\n校验的正则表达式为:" + regex);
            logger.info(msg);
        } else {
            msg.append(message + ":匹配校验失败！\n待校验的字符窜为:" + matcher + "\n校验的正则表达式为:" + regex);
            logger.error(msg);
            TaquAssert.fail(message);
        }
    }

    /**
     * 根据正则表达式断言不匹配待校验的字符串
     *
     * @param matcher 待校验的字符串
     * @param regex   校验的正则表达式
     */
    public static void assertNotMatch(String matcher, String regex) {
        TaquAssert.assertNotMatch(null, matcher, regex);
    }

    //=============================assertStartWith  assertEndWith=====================================================

    /**
     * 断言字符是否已某个字符串开头
     *
     * @param message 断言错误消息
     * @param content 待断言字符串
     * @param prefix  前缀表达式
     */
    public static void assertStartWith(String message, String content, String prefix) {
        StringBuilder msg = new StringBuilder("assertStartWith(String message, String content, String prefix)");
        if (content.startsWith(prefix)) {
            msg.append(message + ":前缀匹配校验成功！\n待校验的字符窜为:" + content + "\n校验的前缀表达式为:" + prefix);
            logger.info(msg);
        } else {
            msg.append(message + ":前缀匹配校验失败！\n待校验的字符窜为:" + content + "\n校验的前缀表达式为:" + prefix);
            logger.error(msg);
            TaquAssert.fail(message);
        }
    }

    /**
     * 断言字符是否已某个字符串开头
     *
     * @param content 待断言字符串
     * @param prefix  前缀表达式
     */
    public static void assertStartWith(String content, String prefix) {
        TaquAssert.assertStartWith(null, content, prefix);
    }

    /**
     * 断言字符是否已某个字符串结尾
     *
     * @param message 断言错误消息
     * @param content 待断言字符串
     * @param endfix  前缀表达式
     */
    public static void assertEndWith(String message, String content, String endfix) {
        StringBuilder msg = new StringBuilder("assertEndWith(String message, String content, String endfix)");
        if (content.endsWith(endfix)) {
            msg.append(message + ":");
            msg.append("后缀匹配校验成功！\n待校验的字符窜为:" + content + "\n校验的后缀表达式为:" + endfix);
            logger.info(msg);
        } else {
            msg.append(message + ":");
            msg.append("后缀匹配校验失败！\n待校验的字符窜为:" + content + "\n校验的后缀表达式为:" + endfix);
            logger.error(msg);
            TaquAssert.fail(message);
        }
    }

    /**
     * 断言字符是否已某个字符串结尾
     *
     * @param content 待断言字符串
     * @param endfix  前缀表达式
     */
    public static void assertEndWith(String content, String endfix) {
        TaquAssert.assertEndWith(null, content, endfix);
    }

    //======================================assertSame=================================

    /**
     * 断言两个对象是否引用同一个对象
     *
     * @param message  断言错误消息
     * @param actual   真实值
     * @param expected 预期值
     */
    static public void assertSame(String message, Object actual, Object expected) {
        StringBuilder msg = new StringBuilder("assertSame(String message,Object actual, Object expected):");

        if (expected == actual) {
            return;
        }
        msg.append(message + ":");
        logger.error(msg + "断言失败");
        failNotSame(message, actual, expected);
    }


    //==================================================================================


    static private void failNotEquals(Object actual, Object expected, String message) {
        logger.error(message + "断言失败：\n预期值：" + expected + "\n真实值：" + actual);
        fail(format(actual, expected, message));
    }

    static String format(Object actual, Object expected, String message) {
        String formatted = "";
        if (null != message) {
            formatted = message + " ";
        }

        return formatted + ASSERT_LEFT + expected + ASSERT_MIDDLE + actual + ASSERT_RIGHT;
    }

    /**
     * 失败给个消息提示
     *
     * @param message 断言错误消息
     */
    static public void fail(String message) {
        throw new AssertionError(message);
    }

    /**
     * 失败给个消息提示和原始提示消息
     *
     * @param message   断言错误消息
     * @param realCause 原来的异常
     */
    static public void fail(String message, Throwable realCause) {
        AssertionError ae = new AssertionError(message);
        ae.initCause(realCause);

        throw ae;
    }

    static private void failNotSame(String message, Object actual, Object expected) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        fail(formatted + ASSERT_LEFT + expected + ASSERT_MIDDLE + actual + ASSERT_RIGHT);
    }
}
