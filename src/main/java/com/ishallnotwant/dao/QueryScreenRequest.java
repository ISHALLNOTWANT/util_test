package com.ishallnotwant.dao;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hanyun
 * @ClassName QueryScreenParam.java
 * @createTime 2022年09月20日 11:10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryScreenRequest {
    private static final long serialVersionUID = 9140412494346270516L;
    /**
     * 机构id
     */
    private Long orgId;
    /**
     * 周期类型
     */
    private Integer dateType;

    /**
     * 开始时间
     */
    private String dateStart;

    /**
     * 结束时间
     */
    private String dateEnd;
}
