package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import p185296_m203380.ft.unicamp.trip_helper.MainActivity;
import p185296_m203380.ft.unicamp.trip_helper.R;
import adapter.MyFirstAdapter;
import viagens.Viagens;

public class AlunosFragment extends Fragment implements FragmentController {
    private View view;

    public AlunosFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_alunos, container, false);
        }

        RecyclerView mRecyclerView = view.findViewById(R.id.alunos_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        MyFirstAdapter mAdapter = new MyFirstAdapter(new ArrayList<>(Arrays.asList(Viagens.viagens)));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setMyOnItemClickListener(new MyFirstAdapter.MyOnItemClickListener() {
            @Override
            public void myOnItemClick(String nome) {
                Toast.makeText(view.getContext(), nome, Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setMyOnItemLongClickListener(new MyFirstAdapter.MyOnItemLongClickListener() {
            @Override
            public void myOnItemLongClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                BiographyFragment biography = new BiographyFragment();
                biography.setArguments(bundle);
                replaceFragment(biography, MainActivity.BIOGRAPHY_KEY);
            }
        });

        return view;
    }

    public void replaceFragment(Fragment fragment, String key) {
        if (getFragmentManager() == null) {
            setTargetFragment(fragment, 0);
        }
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.authors_frame, fragment, key);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
