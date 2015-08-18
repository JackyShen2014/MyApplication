package com.jacky.myapplication.DataBase;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This file describe the scheme of database and all lists information.
 * Created by Jacky on 8/18/2015.
 */
public final class ContentDescriptor {

    public static final String AUTHORITY = "com.v2tech.bizcom";

    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public static final UriMatcher URI_MATCHER = buildUriMatcher();
    public static final String BASE_OWNER_USER_ID = "OwnerUserID";
    public static final String BASE_SAVEDATE = "SaveDate";

    private ContentDescriptor() {
    }


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String augura = AUTHORITY;

        matcher.addURI(augura, HistoriesMessage.PATH, HistoriesMessage.TOKEN);
        matcher.addURI(augura, HistoriesMessage.PATH + "/#",
                HistoriesMessage.TOKEN_WITH_ID);
        matcher.addURI(augura, HistoriesMessage.PATH + "/page",
                HistoriesMessage.TOKEN_BY_PAGE);

        /*matcher.addURI(augura, HistoriesGraphic.PATH, HistoriesGraphic.TOKEN);
        matcher.addURI(augura, HistoriesGraphic.PATH + "/#",
                HistoriesGraphic.TOKEN_WITH_ID);
        matcher.addURI(augura, HistoriesGraphic.PATH + "/page",
                HistoriesGraphic.TOKEN_BY_PAGE);

        matcher.addURI(augura, HistoriesAudios.PATH, HistoriesAudios.TOKEN);
        matcher.addURI(augura, HistoriesAudios.PATH + "/#",
                HistoriesAudios.TOKEN_WITH_ID);

        matcher.addURI(augura, HistoriesFiles.PATH, HistoriesFiles.TOKEN);
        matcher.addURI(augura, HistoriesFiles.PATH + "/#",
                HistoriesFiles.TOKEN_WITH_ID);

        matcher.addURI(augura, RecentHistoriesMessage.PATH,
                RecentHistoriesMessage.TOKEN);
        matcher.addURI(augura, RecentHistoriesMessage.PATH + "/#",
                RecentHistoriesMessage.TOKEN_WITH_ID);

        matcher.addURI(augura, HistoriesMedia.PATH, HistoriesMedia.TOKEN);
        matcher.addURI(augura, HistoriesMedia.PATH + "/#",
                HistoriesMedia.TOKEN_WITH_ID);

        matcher.addURI(augura, HistoriesAddFriends.PATH,
                HistoriesAddFriends.TOKEN);
        matcher.addURI(augura, HistoriesAddFriends.PATH + "/#",
                HistoriesAddFriends.TOKEN_WITH_ID);

        matcher.addURI(augura, HistoriesCrowd.PATH, HistoriesCrowd.TOKEN);
        matcher.addURI(augura, HistoriesCrowd.PATH + "/#",
                HistoriesCrowd.TOKEN_WITH_ID);
        matcher.addURI(augura, HistoriesGraphic.PATH, HistoriesGraphic.TOKEN);*/

        return matcher;
    }



    /**
     * 聊天历史消息记录表
     *
     * @author
     *
     */
    public static class HistoriesMessage {

        public static String PATH = "ChatHistoricalMessage";

        public static String NAME = PATH;

        public static final int TOKEN = 1;

        public static final int TOKEN_WITH_ID = 2;

        public static final int TOKEN_BY_PAGE = 3;

        public static Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH)
                .build();

        public static class Cols {

            public static final String ID = BaseColumns._ID;

            public static String OWNER_USER_NAME = "UserName";
            public static String HISTORY_MESSAGE_SAVEDATE = BASE_SAVEDATE;
            public static final String HISTORY_MESSAGE_GROUP_TYPE = "GroupType";
            public static final String HISTORY_MESSAGE_GROUP_ID = "GroupID";
            public static final String HISTORY_MESSAGE_REMOTE_USER_ID = "RemoteUserID";
            public static final String HISTORY_MESSAGE_FROM_USER_ID = "FromUserID";
            public static final String HISTORY_MESSAGE_TO_USER_ID = "ToUserID";
            public static final String HISTORY_MESSAGE_ID = "MsgID";
            public static final String HISTORY_MESSAGE_CONTENT = "MsgContent";
            public static final String HISTORY_MESSAGE_TRANSTATE = "TransState";

            public static final String[] ALL_CLOS = { ID,
                    HISTORY_MESSAGE_GROUP_TYPE, HISTORY_MESSAGE_GROUP_ID,
                    HISTORY_MESSAGE_REMOTE_USER_ID,
                    HISTORY_MESSAGE_FROM_USER_ID, HISTORY_MESSAGE_TO_USER_ID,
                    HISTORY_MESSAGE_ID, HISTORY_MESSAGE_CONTENT,
                    HISTORY_MESSAGE_SAVEDATE, HISTORY_MESSAGE_TRANSTATE };
        }
    }

}
