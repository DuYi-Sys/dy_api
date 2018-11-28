import com.duyi.hrb.util.RSAEncrypt;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class test3 {
    public static void main(String[] args) throws Exception {
        
//        String urlEncryptionAccount ="EtlnIykx/4jo56GOAAfikyJopdWGNf8TZFD6PYeBwN48rrhbFYuDwPtkSrsg+i9oOYKnd5MZJ0uB4hxWkIbmhpuBFaLQshizo81Lc+HsFgGDvfVB3Njj/yFWSNIJgURsleA9Yq1AN7iDbAZUpszCiMls44OyMU7tsUlUXtXrgyQ=";
//
//        String encodeAccount = URLDecoder.decode(urlEncryptionAccount);
        String urlEncryptionAccount = RSAEncrypt.encrypt("1001");
        
        System.out.println("urlEncryptionAccount:" + urlEncryptionAccount);
        
//        System.out.println("encodeAccount" + encodeAccount);
    

    }

}
