package ro.ubb.reosandroidapp.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ro.ubb.reosandroidapp.model.Apartment;

/**
 * Created by CristianCosmin on 21.12.2017.
 */

public class ApartmentRepository {
    private List<Apartment> repo;
    private final AppDatabase appDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public ApartmentRepository(final AppDatabase appDatabase) {
        this.repo = new ArrayList<>();
        this.appDatabase = appDatabase;
        executor.execute(new Runnable() {

            @Override
            public void run() {
                repo.addAll(appDatabase.apartmentDao().getAll());
            }
        });
    }

    public void add(final Apartment e) {
        repo.add(e);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.apartmentDao().insertAll(e);
            }
        });
    }

    public void update(final Apartment e) {
        Apartment initialApartment = null;
        for(Apartment a : repo) {
                if(a.getId() == e.getId()) {
                    initialApartment = a;
                    break;
                }
            }
        initialApartment.setTitle(e.getTitle());
        initialApartment.setImage(e.getImage());
        initialApartment.setCost(e.getCost());

        final Apartment toUpdate = initialApartment;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.apartmentDao().update(toUpdate);
            }
        });
    }

    public void delete(final Apartment apartment) {

        for(Apartment a : repo) {
            if(a.getId() == apartment.getId()) {
                repo.remove(a);
                final Apartment toDelete = a;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.apartmentDao().delete(toDelete);
                    }
                });
                break;
            }
        }
    }

    public List<Apartment> getAll() {
        return repo;
    }

    public Apartment getApartmentById(final Long id) {
        for(Apartment a:repo) {
            if(a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }
}
