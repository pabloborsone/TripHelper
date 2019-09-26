package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import p185296_m203380.ft.unicamp.trip_helper.R;
import viagens.Viagem;

public class MyFirstAdapter extends RecyclerView.Adapter {

    private ArrayList<Viagem> viagens;
    private MyOnItemClickListener myOnItemClickListener;
    private MyOnItemLongClickListener myOnItemLongClickListener;

    public MyFirstAdapter(ArrayList<Viagem> viagens) {
        this.viagens = viagens;
    }

    public class MyFirstViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txtCity;
        private TextView txtCountry;
        private TextView txtDescription;

        public MyFirstViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagem_viagem);
            txtCity = itemView.findViewById(R.id.nome_cidade);
            txtCountry = itemView.findViewById(R.id.nome_pais);
            txtDescription = itemView.findViewById(R.id.descricao_viagem);
        }

        public void bind(final Viagem viagem) {
            txtCity.setText(viagem.getCidade());
            txtCountry.setText(viagem.getPais());
            txtDescription.setText(viagem.getDescricao());
            imageView.setImageResource(viagem.getFoto());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myOnItemClickListener != null) {
                    TextView txt = view.findViewById(R.id.nome_cidade);
                    myOnItemClickListener.myOnItemClick(txt.getText().toString());
                }
            }
        });
        return new MyFirstViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((MyFirstViewHolder) holder).bind(viagens.get(position));
        View view = holder.itemView;

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (myOnItemLongClickListener != null)
                    myOnItemLongClickListener.myOnItemLongClick(position);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return viagens.size();
    }

    public interface MyOnItemClickListener {
        void myOnItemClick(String nome);
    }

    public interface MyOnItemLongClickListener {
        void myOnItemLongClick(int position);
    }

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }

    public void setMyOnItemLongClickListener(MyOnItemLongClickListener myOnItemLongClickListener) {
        this.myOnItemLongClickListener = myOnItemLongClickListener;
    }
}