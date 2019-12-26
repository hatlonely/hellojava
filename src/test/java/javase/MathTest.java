package javase;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MathTest {
    @Test
    public void testMath() {
        double delta = 0.0000001;
        assertEquals(Math.abs(-6), 6);
        assertEquals(Math.floor(-6.2), -7, delta);  // 向下取整
        assertEquals(Math.floor(6.2), 6, delta);
        assertEquals(Math.floor(6.8), 6, delta);
        assertEquals(Math.ceil(-6.2), -6, delta);   // 向上取整
        assertEquals(Math.ceil(6.2), 7, delta);
        assertEquals(Math.ceil(6.8), 7, delta);
        assertEquals(Math.round(-6.2), -6, delta);  // 四舍五入
        assertEquals(Math.round(6.2), 6, delta);
        assertEquals(Math.round(6.8), 7, delta);
        assertEquals(Math.round(-6.5), -6, delta);
        assertEquals(Math.round(6.5), 7, delta);
        assertEquals(Math.rint(-6.2), -6, delta);  // 最接近整数，如果存在两个，返回偶数
        assertEquals(Math.rint(6.2), 6, delta);
        assertEquals(Math.rint(6.8), 7, delta);
        assertEquals(Math.rint(-6.5), -6, delta);
        assertEquals(Math.rint(6.5), 6, delta);

        assertEquals(Math.sin(Math.PI / 2), 1.0, delta);
        assertEquals(Math.cos(Math.PI), -1, delta);
        assertEquals(Math.tan(Math.PI / 4), 1.0, delta);
        assertEquals(Math.asin(1), Math.PI / 2, delta);
        assertEquals(Math.acos(-1), Math.PI, delta);
        assertEquals(Math.atan(1), Math.PI / 4, delta);

        assertEquals(Math.pow(3, 2), 9, delta);
        assertEquals(Math.pow(2, 3), 8, delta);
        assertEquals(Math.sqrt(4), 2, delta);
        assertEquals(Math.cbrt(27), 3, delta);
        assertEquals(Math.hypot(3, 4), 5, delta);   // √(x² + y²)

        assertEquals(Math.exp(2.5), Math.pow(Math.E, 2.5), delta);  // e ^ x
        assertEquals(Math.expm1(2), Math.exp(2) - 1, delta);    // e ^ x - 1
        assertEquals(Math.log(Math.exp(1.5)), 1.5, delta);  // ln(x)
        assertEquals(Math.log10(1000), 3, delta);           // lg(x)
        assertEquals(Math.log1p(Math.E - 1), 1, delta);     // ln(1 + x)

        assertEquals(Math.sinh(2), (Math.exp(2) - Math.exp(-2)) / 2, delta);    // sinh(x) = (e ^ x - e ^ -x) / 2
        assertEquals(Math.cosh(2), (Math.exp(2) + Math.exp(-2)) / 2, delta);    // cosh(x) = (e ^ x + e ^ -x) / 2
        assertEquals(Math.tanh(2), Math.sinh(2) / Math.cosh(2), delta);         // tanh(x) = sinh(x) / cosh(x)

        assertEquals(Long.MAX_VALUE + 1, Long.MIN_VALUE);                             // 溢出
        assertThrows(ArithmeticException.class, () -> Math.addExact(Long.MAX_VALUE, 1));        // 加法溢出抛异常
        assertThrows(ArithmeticException.class, () -> Math.multiplyExact(Long.MAX_VALUE, 2));   // 乘法
        assertThrows(ArithmeticException.class, () -> Math.decrementExact(Long.MIN_VALUE));     // 递减
        assertThrows(ArithmeticException.class, () -> Math.incrementExact(Long.MAX_VALUE));     // 递增
        assertThrows(ArithmeticException.class, () -> Math.negateExact(Long.MIN_VALUE));        // 相反数
        assertEquals(Math.addExact(1, 2), 3);
        assertEquals(Math.multiplyExact(2, 3), 6);
        assertEquals(Math.incrementExact(6), 7);
        assertEquals(Math.decrementExact(6), 5);
        assertEquals(Math.negateExact(-6), 6);

        assertEquals(Math.multiplyFull(1, 2), 2);   // 接受两个 int 返回一个 long，防止溢出
        assertEquals(Math.multiplyHigh(1, 2), 0);   // 返回两个 long 乘积的高 64 位

        assertEquals(Math.copySign(123.456, -100), -123.456, delta);    // 返回第二个参数符号的第一个参数的值

        assertEquals(Math.floorDiv(7, 3), 2);
        assertEquals(Math.floorDiv(-7, 3), -3);
        assertEquals(Math.floorMod(7, 3), 1);

        System.out.println(Math.nextUp(1.1));   // 下一个浮点数
        System.out.println(Math.nextDown(1.1)); // 上一个浮点数
        System.out.println(Math.nextAfter(1.1, Double.POSITIVE_INFINITY));    // 下一个浮点数
        System.out.println(Math.nextAfter(1.1, Double.NEGATIVE_INFINITY));    // 上一个浮点数

        System.out.println(Math.getExponent(5));    // 无偏指数
        System.out.println(Math.random());          // 0 ~ 1 之间的随机数

        System.out.println(Math.fma(1, 2, 3));
    }
}
