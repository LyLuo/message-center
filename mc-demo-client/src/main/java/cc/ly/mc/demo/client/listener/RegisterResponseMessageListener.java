package cc.ly.mc.demo.client.listener;

import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.attribute.impl.BoolAttribute;
import cc.ly.mc.core.event.EventListener;
import cc.ly.mc.core.event.EventSource;
import cc.ly.mc.core.message.RegisterResponseMessage;

public class RegisterResponseMessageListener implements EventListener {

    public RegisterResponseMessageListener() {
    }

    @Override
    public void update(EventSource event, Object object) {
        RegisterResponseMessage message = (RegisterResponseMessage) object;
        BoolAttribute bool = (BoolAttribute) message.attribute(Attributes.SUCCESS.getCode());
       if(bool.data().get().booleanValue()){
           System.out.println("register successfully");
       }else{
           System.out.println("failed to register");
       }
    }

}
