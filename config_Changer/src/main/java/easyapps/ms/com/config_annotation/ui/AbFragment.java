package easyapps.ms.com.config_annotation.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import easyapps.ms.com.config_annotation.R;
import easyapps.ms.com.config_annotation.ui.viewModel.SharedViewModel;


public class AbFragment extends Fragment {

    public static final String BUNDLE_LOB = "LOB";

    private RecyclerView recyclerView;
    private AbListAdapter abListAdapter;


    public static AbFragment getInstance(String lob) {
        AbFragment abFragment = new AbFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_LOB, lob);
        abFragment.setArguments(bundle);
        return abFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ab, container, false);
        recyclerView = view.findViewById(R.id.rv_ab);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        String lob = (String) getArguments().getSerializable(BUNDLE_LOB);
        SharedViewModel viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        abListAdapter = new AbListAdapter(getActivity(), viewModel.getLobList(lob));
        recyclerView.setAdapter(abListAdapter);
    }

    public void updateFragment() {
        if(abListAdapter != null){
            abListAdapter.notifyDataSetChanged();
        }
    }
}
