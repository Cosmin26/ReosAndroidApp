package ro.ubb.reosandroidapp;

/**
 * Created by CristianCosmin on 08.11.2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.ubb.reosandroidapp.globals.Globals;
import ro.ubb.reosandroidapp.view.ApartmentAdapter;


public class CardFragment extends Fragment {

//    ArrayList<Apartment> listitems = new ArrayList<>();
    RecyclerView recyclerView;
//    String Apartments[] = {"Apt_1", "Apt_2", "Apt_3"};
//    int Images[] = {R.drawable.one, R.drawable.two, R.drawable.three};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getActivity().getIntent().getExtras() != null) {
//            String titleToChange = getActivity().getIntent().getExtras().getString("TITLE_KEY");
//            Integer posAtWhichToChange = getActivity().getIntent().getExtras().getInt("POSITION_KEY");
//            if (titleToChange != null && posAtWhichToChange != null) {
//                Apartments[posAtWhichToChange] = titleToChange;
//            }
//        }
//        initializeList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_card, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (recyclerView != null) {
            recyclerView.setAdapter(new ApartmentAdapter(Globals.apartmentRepository.getAll(), this.getContext()));
        }
        recyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

//    public void initializeList() {
//        listitems.clear();
//        for (int i = 0; i < 3; i++) {
//            Apartment item = new Apartment();
//            item.setTitle(Apartments[i]);
//            item.setImage(Images[i]);
//            listitems.add(item);
//        }
//    }
}
