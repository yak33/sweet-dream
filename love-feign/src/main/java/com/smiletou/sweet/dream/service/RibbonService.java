package com.smiletou.sweet.dream.service;

import com.smiletou.sweet.dream.entity.SysUser;
import com.smiletou.sweet.dream.result.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Ribbon负载均衡服务调用
 *
 * @author ZhangChao
 * @date 2019/10/31 14:46
 * @since 1.0.0
 */
@Service
public class RibbonService {
    /**
     * 将IOC容器中的负载均衡RestTemplate工具注入进来
     */
    @Autowired
    RestTemplate restTemplate;

    public ResponseMessage getOtherServerData(String dictCode){
        /**
         * GET请求
         * getForEntity方法的返回值是一个ResponseEntity<T>，ResponseEntity<T>是Spring对HTTP请求响应的封装，包括了几个重要的元素，如响应码、contentType、contentLength、响应消息体等。
         * 1.可以用一个数字做占位符，最后是一个可变长度的参数，来一一替换前面的占位符。
         * 2.可以前面使用name={name}这种形式，最后一个参数是一个map，map的key即为前边占位符的名字，map的value为参数值。
         */
        ResponseEntity<ResponseMessage> responseEntity = restTemplate.getForEntity("http://ENTERPRISE-SPRINGCLOUD-SYSTEM/sys/dict/list?dictCode={1}",ResponseMessage.class,dictCode);
        /**
         * getForObject函数实际上是对getForEntity函数的进一步封装，如果你只关注返回的消息体的内容，对其他信息都不关注，此时可以使用getForObject.
         * 如：ResponseMessage responseMessage = restTemplate.getForObject("http://customs-server-enterprise-springcloud/getAllByUuidOrNo?searchText={1}",ResponseMessage.class,searchText);
         */
        ResponseMessage rm = responseEntity.getBody();

        /**
         * POST请求
         * postForEntity 方法的第一参数表示要调用的服务的地址 第二个参数表示上传的参数 第三个参数表示返回的消息体的数据类型。
         * postForObject 如果你只关注，返回的消息体，可以直接使用postForObject。用法和getForObject一致。
         * 如：ResponseMessage rm2 = restTemplate.postForObject("http://customs-server-enterprise-springcloud/save",contract,ResponseMessage.class);
         */
//        ResponseEntity<ResponseMessage> responseEntity1 = restTemplate.postForEntity("http://customs-server-enterprise-springcloud/save",contract,ResponseMessage.class);
//        ResponseMessage rm1 = responseEntity1.getBody();

        return rm;
    }

    public ResponseMessage saveUser(SysUser user){
        return restTemplate.postForObject("http://enterprise-springcloud-system/sys/user/saveUser",user,ResponseMessage.class);
    }

}
