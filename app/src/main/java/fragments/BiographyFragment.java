package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import p185296_m203380.ft.unicamp.trip_helper.R;
import viagens.Viagem;
import viagens.Viagens;

public class BiographyFragment extends Fragment {

    private View view;
    private int position = 0;

    public BiographyFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.biography_fragment, container, false);
        }


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }

        final ArrayList<Viagem> viagem = new ArrayList<>(Arrays.asList(Viagens.viagens));

        setElements(viagem.get(position));
    }

    private void setElements(Viagem viagem) {
        TextView detailsTitle = view.findViewById(R.id.details_title);
        ImageView detailsImageView = view.findViewById(R.id.details_image_view);
        TextView detailsDescription = view.findViewById(R.id.details_description);
        detailsTitle.setText(viagem.getCidade());
        detailsImageView.setImageResource(viagem.getFoto());
        detailsDescription.setText(viagem.getDescricao());
    }
}
