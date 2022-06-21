import org.junit.Test;
import top.fcxie.common.User;
import top.fcxie.service.ServerThreadManager;
import top.fcxie.service.UserService;

public class userServiceTest {
    UserService userService = new UserService();
    @Test
    public void TestInsert(){
        User user = new User("拉文", "123456");
        int i = userService.addUser(user);
        System.out.println(i);
    }

    @Test
    public void TestQuery(){
        User user = userService.getUser("1");
        System.out.println(user);
    }

    @Test
    public void TestDelete(){
        int res = userService.removeUser("1");
        System.out.println(res);
    }

    @Test
    public void TestClientList(){
        System.out.println(ServerThreadManager.getClientList());
    }

}
