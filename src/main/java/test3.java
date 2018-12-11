import com.duyi.management.domain.User;
import com.duyi.management.service.UserLogService;
import com.duyi.management.service.UserService;
import com.duyi.util.RSAEncrypt;
import com.duyi.util.RegExUtil;
import org.apache.commons.collections.bag.SynchronizedSortedBag;

import java.util.List;

public class test3 {
    public static void main(String[] args) throws Exception {

        System.out.println(RegExUtil.match("^[a-zA-Z0-9_]{4,16}$", "ab_A0*cd"));
        UserLogService userLogService = new UserLogService();
        User user = userLogService.findByAccount("demo13");
        System.out.println(user);
    }

}
