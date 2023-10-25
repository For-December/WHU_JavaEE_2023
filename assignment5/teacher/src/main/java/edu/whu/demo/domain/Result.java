package edu.whu.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiaxy
 * API返回结果的封装类，目的是在Controller部分能够统一返回正确和错误时的ResponseEntity
 */

@JsonInclude(JsonInclude.Include.NON_NULL) //不序列化为null的字段
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T>{
    T data;
    String error;
}
