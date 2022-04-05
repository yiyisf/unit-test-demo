package com.paic.unittest.demo.service;

import com.paic.unittest.demo.service.dto.XserviceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("http://xservice/api")
public interface XServiceClient {

    @GetMapping("/get")
    ResponseEntity<XserviceDto> getDto();


    @GetMapping("/get-with-param")
    ResponseEntity<XserviceDto> getDtoWithParam(@RequestParam("filed") String filed);

}
