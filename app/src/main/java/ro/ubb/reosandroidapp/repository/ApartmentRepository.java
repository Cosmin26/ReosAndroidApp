package ro.ubb.reosandroidapp.repository;


import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ro.ubb.reosandroidapp.model.Apartment;
import ro.ubb.reosandroidapp.service.Observer;
import ro.ubb.reosandroidapp.service.OnGetDataListener;

/**
 * Created by CristianCosmin on 21.12.2017.
 */

public class ApartmentRepository {
    private List<Apartment> repo = new ArrayList<>();
    private DatabaseReference myDatabaseReference;

    //    private final AppDatabase appDatabase;
//    private Executor executor = Executors.newSingleThreadExecutor();
//
    public ApartmentRepository(boolean isAdmin, final OnGetDataListener listener) {
        listener.onStart();
        if(!isAdmin) {
            myDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("apartments");
            myDatabaseReference.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Get map of users in datasnapshot
                            listener.onSuccess(dataSnapshot);
                            collectApartmentsFromUser((Map<String, Object>) dataSnapshot.getValue());
//                        Object mumu = dataSnapshot.getValue();
//                            collectApartmentsFromAllUsers(repo, (Map<String, Object>) dataSnapshot.getValue());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            listener.onFailed(databaseError);
                            //handle databaseError
                        }
                    });
        }else{
            myDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");
            myDatabaseReference.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            listener.onSuccess(dataSnapshot);
                            //Get map of users in datasnapshot
//                            collectApartmentsFromUser(repo,(Map<String, Object>) dataSnapshot.getValue());
//                        Object mumu = dataSnapshot.getValue();
                            collectApartmentsFromAllUsers((Map<String, Object>) dataSnapshot.getValue());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            listener.onFailed(databaseError);
                            //handle databaseError
                        }
                    });
        }
    }

    public void mReadDataOnce(String child, final OnGetDataListener listener) {
        listener.onStart();
        FirebaseDatabase.getInstance().getReference().child(child).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    private void collectApartmentsFromUser(Map<String, Object> apartmentsMap){
        ArrayList<Apartment> apartments = new ArrayList<>();

        if(apartmentsMap != null) {
            for (Map.Entry<String, Object> apartmentEntry : apartmentsMap.entrySet()) {
                //Get phone field and append to list
                Map singleApartment = (Map) apartmentEntry.getValue();
                int cost = Integer.parseInt(singleApartment.get("cost").toString());
                Apartment apartment = new Apartment(FirebaseAuth.getInstance().getCurrentUser().getUid(), (String) singleApartment.get("title"), (String) singleApartment.get("imageUrl"), cost);
                apartment.setId(apartmentEntry.getKey());
                apartments.add(apartment);
//                apartments.add(new Apartment(FirebaseAuth.getInstance().getCurrentUser().getUid(), (String) singleApartment.get("title"), (String) singleApartment.get("imageUrl"), cost));
            }
        }
        this.repo = apartments;
    }

    private void collectApartmentsFromAllUsers(Map<String, Object> users) {

        ArrayList<Apartment> apartments = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map<String, Object> singleUser = (Map<String, Object>) entry.getValue();
            Map<String, Object> apartmentsOfUser = (Map<String, Object>) singleUser.get("apartments");
            if(apartmentsOfUser!=null){
                for (Map.Entry<String, Object> apartmentEntry : apartmentsOfUser.entrySet()) {
                    //Get phone field and append to list
                    Map singleApartment = (Map) apartmentEntry.getValue();
                    int cost = Integer.parseInt(singleApartment.get("cost").toString());
                    Apartment apartment = new Apartment(entry.getKey(), (String) singleApartment.get("title"), (String) singleApartment.get("imageUrl"), cost);
                    apartment.setId(apartmentEntry.getKey());
                    apartments.add(apartment);
                }
            }
        }
        this.repo = apartments;
    }

    public void addApartment(String personId, String title, String imageUrl, int cost){
        Apartment apartment = new Apartment(personId, title, imageUrl, cost);
        String apartmentId = myDatabaseReference.push().getKey();
        apartment.setId(apartmentId);
        this.repo.add(apartment);
//        FirebaseDatabase.getInstance().getReference().child("users").child(personId).child("apartments");
        myDatabaseReference.child(apartmentId).child("title").setValue(apartment.getTitle());
        myDatabaseReference.child(apartmentId).child("imageUrl").setValue(apartment.getImage());
        myDatabaseReference.child(apartmentId).child("cost").setValue(apartment.getCost());
    }
    public void updateApartment(String personId, Apartment apartment){
        this.repo.remove(this.repo.indexOf(this.getApartmentById(apartment.getId())));
        this.repo.add(apartment);
        myDatabaseReference.child(apartment.getId()).child("title").setValue(apartment.getTitle());
        myDatabaseReference.child(apartment.getId()).child("imageUrl").setValue(apartment.getImage());
//        myDatabaseReference.child(personId).child(apartment.getId()).child("cost").setValue(apartment.getCost());
    }

    public void removeApartment(String personId, String apartmentId){
        this.repo.remove(this.repo.indexOf(this.getApartmentById(apartmentId)));
        myDatabaseReference.child(apartmentId).removeValue();
    }
//        this.repo = new ArrayList<>();
//        this.appDatabase = appDatabase;
//        executor.execute(new Runnable() {
//
//            @Override
//            public void run() {
//                repo.addAll(appDatabase.apartmentDao().getAll());
//            }
//        });

    //
//    public void add(final Apartment e) {
//        repo.add(e);
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                appDatabase.apartmentDao().insertAll(e);
//            }
//        });
//    }
//
//    public void update(final Apartment e) {
//        Apartment initialApartment = null;
//        for(Apartment a : repo) {
//                if(a.getId() == e.getId()) {
//                    initialApartment = a;
//                    break;
//                }
//            }
//        initialApartment.setTitle(e.getTitle());
//        initialApartment.setImage(e.getImage());
//        initialApartment.setCost(e.getCost());
//
//        final Apartment toUpdate = initialApartment;
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                appDatabase.apartmentDao().update(toUpdate);
//            }
//        });
//    }
//
//    public void delete(final Apartment apartment) {
//
//        for(Apartment a : repo) {
//            if(a.getId() == apartment.getId()) {
//                repo.remove(a);
//                final Apartment toDelete = a;
//                executor.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        appDatabase.apartmentDao().delete(toDelete);
//                    }
//                });
//                break;
//            }
//        }
//    }
//
    public List<Apartment> getAll() {
        return repo;
    }

    public Apartment getApartmentById(final String id) {
        for (Apartment a : repo) {
            if (a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }
}
