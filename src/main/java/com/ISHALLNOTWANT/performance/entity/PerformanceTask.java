package com.ISHALLNOTWANT.performance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class PerformanceTask extends BaseEntity{

    private static final long serialVersionUID = 6540982017109466916L;

    /**
     * 主键（履职任务id）
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 主任务id 0表示主任务
     */
    private Integer taskMainId;

    /**
     * 任务模版ID
     */
    private BigInteger perTempleId;

    /**
     * 模板履职点ID
     */
    private BigInteger perSiteId;

    /**
     * 任务名字
     */
    private String taskName;

    /**
     * 任务所属客户id
     */
    private Integer openAccountId;

    /**
     * 任务所属机构ID
     */
    private Integer branchId;

    /**
     * 任务所属机构名称
     */
    private String branchName;

    /**
     * 任务执行组织机构ID
     */
    private Integer executeOrgId;

    /**
     * 任务执行组织机构level
     */
    private String executeOrgLevel;

    /**
     * 任务状态 0-未开始 1-未完成 2-已完成
     */
    private Integer taskStatus;

    /**
     * 履职点名称
     */
    private String perSiteName;

    /**
     * 履职点状态 0-未完成  1-合格  -1-不合格
     */
    private Integer perStatus;

    /**
     * 前置执行任务ID
     */
    private Integer perTaskId;

    /**
     * 履职执行开始时间
     */
    private BigInteger executeStartTime;

    /**
     * 履职执行结束时间
     */
    private BigInteger executeEndTime;

    /**
     * 是否超时  1:超时 0:未超时
     */
    private Integer isTimeOut;

    /**
     * 任务创建时间
     */
    private BigInteger createTime;

    /**
     * 履职任务完成时间
     */
    private BigInteger finishTime;

    /**
     * 组织级别
     */
    private String orgLevel;

    /**
     * 任务时间类型
     */
    private String taskTimeType;

    /**
     * 执行任务开始时间
     */
    private BigInteger startTime;

    /**
     * 任务得分
     */
    private BigDecimal score;

    /**
     * 记录最后修改时间
     */
    private BigInteger updateTime;

    /**
     * 任务项合格数
     */
    private Integer qualifiedCount;

    /**
     * 任务项不合格数
     */
    private Integer unQualifiedCount;

    /**
     * 完成数
     */
    private Integer finishCount;

    /**
     * 未完成数
     */
    private Integer unFinishCount;

    /**
     * 修复数
     */
    private Integer repairCount;

    /**
     * 执行开始小时
     */
    private String executeStartHour;

    /**
     * 执行结束小时
     */
    private String executeEndHour;

    /**
     * 执行岗位list
     */
    private String postList;

    /**
     * 修复状态，0：未修复，1：已修复
     */
    private Integer repairStatus;

    /**
     * 是否删除，0 未删除，1：已删除
     */
    private Integer isDelete;
}
