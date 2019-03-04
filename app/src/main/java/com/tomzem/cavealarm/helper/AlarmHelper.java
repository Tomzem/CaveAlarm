package com.tomzem.cavealarm.helper;

import com.tomzem.cavealarm.bean.Alarm;
import com.tomzem.cavealarm.greendao.AlarmDao;
import com.tomzem.cavealarm.greendao.DaoManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomze
 * @time 2019年03月02日 19:42
 * @desc
 */
public class AlarmHelper extends DaoManager {

    /**
     * 添加闹钟
     *
     * @param alarm
     * @return
     */
    public long addAlarm(Alarm alarm) {
        if (alarm == null) {
            return -1;
        }
        return AlarmHelper.getInstance().getDaoSession().insert(alarm);
    }

    public long updateAlarm(Alarm alarm) {
        if (alarm == null) {
            return -1;
        }
        return AlarmHelper.getInstance().getDaoSession().insertOrReplace(alarm);
    }

    /**
     * 根据Id查找闹钟
     *
     * @param alarmId
     * @return
     */
    public Alarm getAlarmById(String alarmId) {
        Alarm alarm = null;
        QueryBuilder<Alarm> query = getDaoSession().getAlarmDao().queryBuilder()
                .where(AlarmDao.Properties.Id.eq(alarmId), AlarmDao.Properties.IsDelete.eq(0));
        if (query != null) {
            alarm = query.unique();
        }
        return alarm;
    }

    public List<Alarm> getAllAlarm() {
        List<Alarm> alarms = new ArrayList<>();
        QueryBuilder query = getDaoSession().getAlarmDao().queryBuilder()
                .where(AlarmDao.Properties.IsDelete.eq(0));
        if (query != null) {
            alarms.addAll(query.list());
        }
        return alarms;
    }

    private static AlarmHelper alarmHelper;

    private AlarmHelper() {
    }

    public static AlarmHelper getAlarmHelper() {
        return alarmHelper = new AlarmHelper();
    }

}
