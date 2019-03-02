package com.tomzem.cavealarm.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.tomzem.cavealarm.bean.Alarm;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ALARM".
*/
public class AlarmDao extends AbstractDao<Alarm, Long> {

    public static final String TABLENAME = "ALARM";

    /**
     * Properties of entity Alarm.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property RingTime = new Property(1, Long.class, "ringTime", false, "RING_TIME");
        public final static Property CreateTime = new Property(2, Long.class, "createTime", false, "CREATE_TIME");
        public final static Property UpdateTime = new Property(3, Long.class, "updateTime", false, "UPDATE_TIME");
        public final static Property AlarmNote = new Property(4, String.class, "alarmNote", false, "ALARM_NOTE");
        public final static Property RingHour = new Property(5, String.class, "ringHour", false, "RING_HOUR");
        public final static Property RingMin = new Property(6, String.class, "ringMin", false, "RING_MIN");
        public final static Property RingCycle = new Property(7, int.class, "ringCycle", false, "RING_CYCLE");
        public final static Property CurrentState = new Property(8, int.class, "currentState", false, "CURRENT_STATE");
        public final static Property IsDelete = new Property(9, boolean.class, "isDelete", false, "IS_DELETE");
    }


    public AlarmDao(DaoConfig config) {
        super(config);
    }
    
    public AlarmDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ALARM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"RING_TIME\" INTEGER," + // 1: ringTime
                "\"CREATE_TIME\" INTEGER," + // 2: createTime
                "\"UPDATE_TIME\" INTEGER," + // 3: updateTime
                "\"ALARM_NOTE\" TEXT," + // 4: alarmNote
                "\"RING_HOUR\" TEXT," + // 5: ringHour
                "\"RING_MIN\" TEXT," + // 6: ringMin
                "\"RING_CYCLE\" INTEGER NOT NULL ," + // 7: ringCycle
                "\"CURRENT_STATE\" INTEGER NOT NULL ," + // 8: currentState
                "\"IS_DELETE\" INTEGER NOT NULL );"); // 9: isDelete
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ALARM\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Alarm entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long ringTime = entity.getRingTime();
        if (ringTime != null) {
            stmt.bindLong(2, ringTime);
        }
 
        Long createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(3, createTime);
        }
 
        Long updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindLong(4, updateTime);
        }
 
        String alarmNote = entity.getAlarmNote();
        if (alarmNote != null) {
            stmt.bindString(5, alarmNote);
        }
 
        String ringHour = entity.getRingHour();
        if (ringHour != null) {
            stmt.bindString(6, ringHour);
        }
 
        String ringMin = entity.getRingMin();
        if (ringMin != null) {
            stmt.bindString(7, ringMin);
        }
        stmt.bindLong(8, entity.getRingCycle());
        stmt.bindLong(9, entity.getCurrentState());
        stmt.bindLong(10, entity.getIsDelete() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Alarm entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long ringTime = entity.getRingTime();
        if (ringTime != null) {
            stmt.bindLong(2, ringTime);
        }
 
        Long createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(3, createTime);
        }
 
        Long updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindLong(4, updateTime);
        }
 
        String alarmNote = entity.getAlarmNote();
        if (alarmNote != null) {
            stmt.bindString(5, alarmNote);
        }
 
        String ringHour = entity.getRingHour();
        if (ringHour != null) {
            stmt.bindString(6, ringHour);
        }
 
        String ringMin = entity.getRingMin();
        if (ringMin != null) {
            stmt.bindString(7, ringMin);
        }
        stmt.bindLong(8, entity.getRingCycle());
        stmt.bindLong(9, entity.getCurrentState());
        stmt.bindLong(10, entity.getIsDelete() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Alarm readEntity(Cursor cursor, int offset) {
        Alarm entity = new Alarm( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // ringTime
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // createTime
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // updateTime
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // alarmNote
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // ringHour
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // ringMin
            cursor.getInt(offset + 7), // ringCycle
            cursor.getInt(offset + 8), // currentState
            cursor.getShort(offset + 9) != 0 // isDelete
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Alarm entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRingTime(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setCreateTime(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setUpdateTime(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setAlarmNote(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setRingHour(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setRingMin(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setRingCycle(cursor.getInt(offset + 7));
        entity.setCurrentState(cursor.getInt(offset + 8));
        entity.setIsDelete(cursor.getShort(offset + 9) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Alarm entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Alarm entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Alarm entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}