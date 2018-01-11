package person.andy.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import person.andy.bean.User;
import person.andy.common.ResponseObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @Author: AndyCui
 * @Date: 2017/10/10 17:33
 * @Description:
 */
@RestController
public class HelloController {
    @Resource
    public RestTemplate restTemplate;

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello() throws InterruptedException {
        return "hello service copy";
    }
    @RequestMapping(value = "/serviceHello",method = RequestMethod.GET)
    public String helloAsService(HttpServletRequest request){
        String remoteIp=getClientIp(request);
        return restTemplate.getForEntity("http://hello-consumer/hello-consumer/serviceHello",String.class).getBody();
    }
    @RequestMapping(value = "/user",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User user(@RequestParam String name,@RequestParam Integer age,@RequestBody User user){
        System.err.println("hello service");
        User user1=new User();
        user1.setName(name);
        user1.setAge(age);
        System.err.println(user.getAge()+" "+user.getName());
        return user;
    }
    @RequestMapping(value = "/pay",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseObject<String> pay(@RequestParam String name){
        Random random=new Random();
        int errorCode=random.nextInt(3);
        System.err.println(errorCode);
        if (errorCode==1){
            return  ResponseObject.errorResponseWithData(201,"微信支付失败",name);
        }else if (errorCode==2){
            return ResponseObject.errorResponseWithData(202,"钱包支付失败",name);
        }else {
            return ResponseObject.successResponse(name,"支付成功");
        }
    }
    private static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");

            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");

            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");

            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");

            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();

            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        System.err.println("最终获得的客户端ip是{}"+ip);
        return ip;
    }
}
