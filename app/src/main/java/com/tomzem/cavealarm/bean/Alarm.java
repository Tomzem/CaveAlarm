package com.tomzem.cavealarm.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Tomze
 * @time 2019年03月02日 19:10
 * @desc 闹钟类
 */
@Entity
public class Alarm {

    @Id(autoincrement = true)
    private Long id;
    private Long ringTime; //下次响铃时间
    private Long createTime; //创建时间
    private Long updateTime; //更新时间

    private String alarmNote; //备注
    private String ringHour; //响铃小时点
    private String ringMin; //响铃分钟点

    private int ringCycle; //响铃周期
    private int currentState = 0; //当前状态 -1关闭 0开启 1响铃 2延迟响铃

    private boolean isDelete = false; //是否删除

    @Generated(hash = 1059461599)
    public Alarm(Long id, Long ringTime, Long createTime, Long updateTime,
            String alarmNote, String ringHour, String ringMin, int ringCycle,
            int currentState, boolean isDelete) {
        this.id = id;
        this.ringTime = ringTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.alarmNote = alarmNote;
        this.ringHour = ringHour;
        this.ringMin = ringMin;
        this.ringCycle = ringCycle;
        this.currentState = currentState;
        this.isDelete = isDelete;
    }

    @Generated(hash = 1972324134)
    public Alarm() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRingTime() {
        return this.ringTime;
    }

    public void setRingTime(Long ringTime) {
        this.ringTime = ringTime;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getAlarmNote() {
        return this.alarmNote;
    }

    public void setAlarmNote(String alarmNote) {
        this.alarmNote = alarmNote;
    }

    public String getRingHour() {
        return this.ringHour;
    }

    public void setRingHour(String ringHour) {
        this.ringHour = ringHour;
    }

    public String getRingMin() {
        return this.ringMin;
    }

    public void setRingMin(String ringMin) {
        this.ringMin = ringMin;
    }

    public int getRingCycle() {
        return this.ringCycle;
    }

    public void setRingCycle(int ringCycle) {
        this.ringCycle = ringCycle;
    }

    public int getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public boolean getIsDelete() {
        return this.isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }
}
