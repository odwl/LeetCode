import org.junit.Test;


import static org.junit.Assert.*;


public class BinaryComplementTest {

    public int bitwiseComplement2(int n) {
        String b = Integer.toBinaryString(n);
        String s = b.chars().map(t -> t =='0' ? '1' : '0').collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return Integer.parseInt(s, 2);
    }

    public int bitwiseComplement3(int n) {
        if (n == 0) return 0;
        int p = (int)Math.pow(2, Math.ceil(Math.log(n+1)/Math.log(2))) - 1;
        return n ^ p;
    }

    public int bitwiseComplement(int n) {
        int power = 1;
        while(power < n +1)
            power*=2;

        return n ^ (power-1);
    }





    @Test
    public void testBit() {
        assertEquals(1, bitwiseComplement(2));
        assertEquals(0, bitwiseComplement(1));
        assertEquals(2, bitwiseComplement(5));
        assertEquals(0, bitwiseComplement(3));
    }


}
