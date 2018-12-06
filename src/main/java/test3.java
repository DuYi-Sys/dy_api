import com.duyi.util.RSAEncrypt;
import com.duyi.util.RegExUtil;

public class test3 {
    public static void main(String[] args) throws Exception {

        System.out.println(RegExUtil.match("^[a-zA-Z0-9_]{4,16}$", "ab_A0*cd"));

    }

}
