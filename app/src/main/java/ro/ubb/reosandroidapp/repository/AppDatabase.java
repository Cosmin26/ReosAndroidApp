//package ro.ubb.reosandroidapp.repository;
//
//import android.arch.persistence.db.SupportSQLiteOpenHelper;
//import android.arch.persistence.room.Database;
//import android.arch.persistence.room.DatabaseConfiguration;
//import android.arch.persistence.room.InvalidationTracker;
//import android.arch.persistence.room.Room;
//import android.arch.persistence.room.RoomDatabase;
//import android.content.Context;
//
//import ro.ubb.reosandroidapp.model.Apartment;
//
///**
// * Created by CristianCosmin on 21.12.2017.
// */
//
//@Database(entities = {Apartment.class}, version = 1)
//public abstract class AppDatabase extends RoomDatabase{
//    private static AppDatabase INSTANCE;
//    public abstract ApartmentDao apartmentDao();
//
//    public static AppDatabase getAppDatabase(Context context) {
//        if (INSTANCE == null) {
//            INSTANCE =
//                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "list-database")
//                            .build();
//        }
//        return INSTANCE;
//    }
//
//    public static void destroyInstance() {
//        INSTANCE = null;
//    }
//}
