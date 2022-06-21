import org.junit.Test;
import top.fcxie.common.Message;
import top.fcxie.common.MessageType;
import top.fcxie.service.MessageService;

import java.util.Date;
import java.util.List;

public class MessageServiceTest {
    private MessageService service = new MessageService();
    @Test
    public void insertTest(){
        Message message = new Message();
        message.setSender("詹姆斯");
        message.setReceiver("东契奇");
        message.setContent("未来是你的");
        message.setSendTime(new Date().toString());
        message.setType(MessageType.MESSAGE_COMM_MES);
        System.out.println(service.addMessage(message));
    }

    @Test
    public void queryTest(){
        List<Message> list = service.getMessageByGetterId("东契奇");
        for(Message message : list){
            System.out.println(message);
        }
    }

    @Test
    public void updateTest(){
        int res = service.updateMessageStatusByGetterId("东契奇");
        System.out.println(res);
    }
}
