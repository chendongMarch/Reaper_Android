package com.march.reaper.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.march.reaper.imodel.bean.BeautyAlbum;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BEAUTY_ALBUM".
*/
public class BeautyAlbumDao extends AbstractDao<BeautyAlbum, Void> {

    public static final String TABLENAME = "BEAUTY_ALBUM";

    /**
     * Properties of entity BeautyAlbum.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Album_type = new Property(0, String.class, "album_type", false, "ALBUM_TYPE");
        public final static Property Key_words = new Property(1, String.class, "key_words", false, "KEY_WORDS");
        public final static Property Album_link = new Property(2, String.class, "album_link", false, "ALBUM_LINK");
        public final static Property Album_cover = new Property(3, String.class, "album_cover", false, "ALBUM_COVER");
        public final static Property Album_desc = new Property(4, String.class, "album_desc", false, "ALBUM_DESC");
        public final static Property Time_stamp = new Property(5, String.class, "time_stamp", false, "TIME_STAMP");
        public final static Property IsFavorite = new Property(6, Boolean.class, "isFavorite", false, "IS_FAVORITE");
    };


    public BeautyAlbumDao(DaoConfig config) {
        super(config);
    }
    
    public BeautyAlbumDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BeautyAlbum entity) {
        stmt.clearBindings();
 
        String album_type = entity.getAlbum_type();
        if (album_type != null) {
            stmt.bindString(1, album_type);
        }
 
        String key_words = entity.getKey_words();
        if (key_words != null) {
            stmt.bindString(2, key_words);
        }
 
        String album_link = entity.getAlbum_link();
        if (album_link != null) {
            stmt.bindString(3, album_link);
        }
 
        String album_cover = entity.getAlbum_cover();
        if (album_cover != null) {
            stmt.bindString(4, album_cover);
        }
 
        String album_desc = entity.getAlbum_desc();
        if (album_desc != null) {
            stmt.bindString(5, album_desc);
        }
 
        String time_stamp = entity.getTime_stamp();
        if (time_stamp != null) {
            stmt.bindString(6, time_stamp);
        }
 
        Boolean isFavorite = entity.getIsFavorite();
        if (isFavorite != null) {
            stmt.bindLong(7, isFavorite ? 1L: 0L);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BeautyAlbum entity) {
        stmt.clearBindings();
 
        String album_type = entity.getAlbum_type();
        if (album_type != null) {
            stmt.bindString(1, album_type);
        }
 
        String key_words = entity.getKey_words();
        if (key_words != null) {
            stmt.bindString(2, key_words);
        }
 
        String album_link = entity.getAlbum_link();
        if (album_link != null) {
            stmt.bindString(3, album_link);
        }
 
        String album_cover = entity.getAlbum_cover();
        if (album_cover != null) {
            stmt.bindString(4, album_cover);
        }
 
        String album_desc = entity.getAlbum_desc();
        if (album_desc != null) {
            stmt.bindString(5, album_desc);
        }
 
        String time_stamp = entity.getTime_stamp();
        if (time_stamp != null) {
            stmt.bindString(6, time_stamp);
        }
 
        Boolean isFavorite = entity.getIsFavorite();
        if (isFavorite != null) {
            stmt.bindLong(7, isFavorite ? 1L: 0L);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public BeautyAlbum readEntity(Cursor cursor, int offset) {
        BeautyAlbum entity = new BeautyAlbum( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // album_type
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // key_words
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // album_link
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // album_cover
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // album_desc
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // time_stamp
            cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0 // isFavorite
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BeautyAlbum entity, int offset) {
        entity.setAlbum_type(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setKey_words(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAlbum_link(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAlbum_cover(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAlbum_desc(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTime_stamp(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIsFavorite(cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0);
     }
    
    @Override
    protected final Void updateKeyAfterInsert(BeautyAlbum entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(BeautyAlbum entity) {
        return null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}