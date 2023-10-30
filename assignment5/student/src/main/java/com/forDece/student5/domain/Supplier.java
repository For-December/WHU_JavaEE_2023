package com.forDece.student5.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author forDece
 * @since 2023-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String email;

    private String name;

    private String phone;


}
